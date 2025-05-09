package com.farcr.nomansland.common.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.Graph;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Shadow public Supplier<List<FeatureSorter.StepFeatureData>> featuresPerStep;

    private static <T> List<FeatureSorter.StepFeatureData> buildFeaturesPerStep(
            List<T> biomes, Function<T, List<HolderSet<PlacedFeature>>> biomeToFeatureSetFunction, boolean notRecursiveFlag
    ) {
        // Map of each placed feature to a unique index (provided by mutableint)
        Object2IntMap<PlacedFeature> indexMap = new Object2IntOpenHashMap<>();
        MutableInt mutableint = new MutableInt(0);

        record FeatureData(int featureIndex, int step, PlacedFeature feature) {
        }

        Comparator<FeatureData> comparator = Comparator.comparingInt(FeatureData::step).thenComparingInt(FeatureData::featureIndex);
        // Map of each FeatureData to a set of all FeatureDatas that come directly after it in any biome
        Map<FeatureData, Set<FeatureData>> dependencyMap = new TreeMap<>(comparator);

        // Largest biome feature count found so far, used later
        int maxBiomeFeatureCount = 0;

        // Populate the indexMap and dependencyMap, and figure out maxBiomeFeatureCount
        for (T biome : biomes) {
            List<FeatureData> featureDataList = Lists.newArrayList();
            List<HolderSet<PlacedFeature>> featureSet = biomeToFeatureSetFunction.apply(biome);
            maxBiomeFeatureCount = Math.max(maxBiomeFeatureCount, featureSet.size());

            for (int j = 0; j < featureSet.size(); j++) {
                for (Holder<PlacedFeature> holder : featureSet.get(j)) {
                    PlacedFeature placedfeature = holder.value();
                    featureDataList.add(new FeatureData(indexMap.computeIfAbsent(placedfeature, p_220609_ -> mutableint.getAndIncrement()), j, placedfeature));
                }
            }

            for (int k = 0; k < featureDataList.size(); k++) {
                Set<FeatureData> featureDataSet = dependencyMap.computeIfAbsent(featureDataList.get(k), p_220602_ -> new TreeSet<>(comparator));
                if (k < featureDataList.size() - 1) {
                    featureDataSet.add(featureDataList.get(k + 1));
                }
            }
        }

        // Unique nodes visited during graph traversal so far
        Set<FeatureData> visitedNodesUnique = new TreeSet<>(comparator);
        // Internally used by the DFS, should be empty outside of DFS scope
        Set<FeatureData> currentPathNodes = new TreeSet<>(comparator);
        // ALL nodes visited during graph traversal (may have repeats)
        List<FeatureData> visitedNodes = Lists.newArrayList();

        for (FeatureData featureData : dependencyMap.keySet()) {
            if (!currentPathNodes.isEmpty()) {
                throw new IllegalStateException("You somehow broke the universe; DFS bork (iteration finished with non-empty in-progress vertex set");
            }

            // DFS returns false if we do a regular path to a dead end, or if at any point we hit somewhere we've already visited
            // If it returns true it means we've found a loop in the process of the current path traversal
            if (!visitedNodesUnique.contains(featureData) && Graph.depthFirstSearch(dependencyMap, visitedNodesUnique, currentPathNodes, visitedNodes::add, featureData)) {
                /*if (!notRecursiveFlag) {
                    throw new IllegalStateException("Feature order cycle found");
                }

                List<T> list3 = new ArrayList<>(biomes);

                int j1;
                do {
                    j1 = list3.size();
                    ListIterator<T> listiterator = list3.listIterator();

                    while (listiterator.hasNext()) {
                        T t1 = listiterator.next();
                        listiterator.remove();

                        try {
                            buildFeaturesPerStep(list3, biomeToFeatureSetFunction, false);
                        } catch (IllegalStateException illegalstateexception) {
                            continue;
                        }

                        listiterator.add(t1);
                    }
                } while (j1 != list3.size());

                throw new IllegalStateException("Feature order cycle found, involved sources: " + list3);*/
                // instead of fussing about feature order cycles, just collect the path from where we died and add it to the list
                for (FeatureData pathNode : currentPathNodes) {
                    if (visitedNodesUnique.add(pathNode))
                        visitedNodes.add(pathNode);
                }
                currentPathNodes = new TreeSet<>(comparator);
            }
        }

        Collections.reverse(visitedNodes);
        ImmutableList.Builder<FeatureSorter.StepFeatureData> builder = ImmutableList.builder();

        for (int l = 0; l < maxBiomeFeatureCount; l++) {
            int i1 = l;
            List<PlacedFeature> list4 = visitedNodes.stream().filter(p_220599_ -> p_220599_.step() == i1).map(FeatureData::feature).collect(Collectors.toList());
            // Using this constructor because access transformers are evil and don't work
            builder.add(new FeatureSorter.StepFeatureData(list4, Util.createIndexIdentityLookup(list4)));
        }

        return builder.build();
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/biome/BiomeSource;Ljava/util/function/Function;)V", at = @At("TAIL"))
    private void replaceFeaturesPerStep(BiomeSource biomeSource, Function<Holder<Biome>, BiomeGenerationSettings> generationSettingsGetter, CallbackInfo ci)
    {
        this.featuresPerStep = net.neoforged.neoforge.common.util.Lazy.of(
                () -> buildFeaturesPerStep(List.copyOf(biomeSource.possibleBiomes()), holder -> generationSettingsGetter.apply(holder).features(), true)
        );
    }
}
