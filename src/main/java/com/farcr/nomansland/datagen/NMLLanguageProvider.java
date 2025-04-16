package com.farcr.nomansland.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.ItemDefinition;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.items.NMLItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class NMLLanguageProvider extends LanguageProvider {
    public NMLLanguageProvider(PackOutput output) {
        super(output, NoMansLand.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (BlockDefinition<?> definition : NMLBlocks.BLOCK_DEFINITIONS) {

            if (!definition.hasCustomLang()) {
                add(definition.langKey(), definition.langName());
            }
        }

        for (ItemDefinition<?> definition : NMLItems.ITEM_DEFINITIONS) {

            if (!definition.hasCustomLang()) {
                add(definition.langKey(), definition.langName());
            }
        }

        add("itemGroup.nomansland", "No Man's Land");
        add("biome.nomansland.old_growth_forest", "Old Growth Forest");
        add("biome.nomansland.old_growth_forest_clearing", "Old Growth Forest Clearing");
        add("biome.nomansland.old_growth_forest_edge", "Old Growth Forest Edge");
        add("biome.nomansland.boreal_forest", "Boreal Forest");
        add("biome.nomansland.autumnal_forest", "Autumnal Forest");
        add("biome.nomansland.autumnal_fields", "Autumnal Fields");
        add("biome.nomansland.maple_forest", "Maple Forest");
        add("biome.nomansland.maple_grove", "Maple Grove");
        add("biome.nomansland.dark_swamp", "Dark Swamp");
        add("biome.nomansland.bog", "Bog");
        add("biome.nomansland.bayou", "Bayou");
        add("biome.nomansland.caves", "Caves");
        add("biome.nomansland.cave_depths", "Cave Depths");
        add("biome.nomansland.gutter", "Gutter");
        add("biome.nomansland.jejunum", "Jejunum");
        add("entity.nomansland.buried", "Buried");
        add("entity.nomansland.cave_carp", "Cave Carp");
        add("entity.nomansland.billhook_bass", "Billhook Bass");
        add("entity.nomansland.deer", "Deer");
        add("fluid_type.nomansland.resin_oil", "Resin Oil");
        add("entity.nomansland.boat", "Boat");
        add("entity.nomansland.chest_boat", "Boat with Chest");
        add("nomansland.subtitles.block.spike_trap.extend", "Spikes extend");
        add("nomansland.subtitles.block.spike_trap.retract", "Spikes retract");
        add("nomansland.subtitles.block.monster_anchor.monster_resurrection", "Monster begins resurrection");
        add("nomansland.subtitles.block.monster_anchor.activate", "Monster anchor activates");
        add("nomansland.subtitles.block.monster_anchor.deactivate", "Monster anchor deactivates");
        add("nomansland.subtitles.entity.player.drink_milk", "Player drinks milk");
        add("nomansland.subtitles.entity.generic.sticky_cauldron_slide", "Sliding in a sticky cauldron");
        add("nomansland.subtitles.item.generic.consumed", "Item consumed");
        add("nomansland.subtitles.item.bomb.primed", "Bomb primed");
        add("nomansland.subtitles.entity.billhook_bass.death", "Billhook Bass dies");
        add("nomansland.subtitles.entity.billhook_bass.flop", "Billhook Bass flops");
        add("nomansland.subtitles.entity.billhook_bass.hurt", "Billhook Bass hurts");
        add("nomansland.subtitles.entity.deer.death", "Deer dies");
        add("nomansland.subtitles.entity.deer.hurt", "Deer hurts");
        add("nomansland.subtitles.entity.deer.ambient", "Deer bleats");
        add("nomansland.subtitles.particle.droplet.fall", "Droplet falls");
        add("death.attack.nomansland.spike_fall", "%1$s fell for a spike trap");
        add("death.attack.nomansland.spike_fall.player", "%1$s fell for a spike trap while fighting %2$s");
        add("death.attack.nomansland.spike_impale", "%1$s was impaled in a spike trap");
        add("death.attack.nomansland.spike_impale.player", "%1$s was impaled in a spike trap while fighting %2$s");
        add("death.attack.nomansland.spike_poke", "%1$s got stuck in a spike trap");
        add("death.attack.nomansland.spike_poke.player", "%1$s got stuck in a spike trap while fighting %2$s");
        add("death.attack.nomansland.spike_skewer", "%1$s was skewered by a spike trap");
        add("death.attack.nomansland.spike_skewer.player", "%1$s was skewered by a spike trap while fighting %2$s");
        add("death.attack.nomansland.combust", "%1$s faced immolation");
        add("death.attack.nomansland.combust.player", "%1$s was immolated by %2$s");
        add("entity.nomansland.explosive", "Explosive");
        add("entity.nomansland.firebomb", "Firebomb");
        add("effect.nomansland.flammable", "Flammable");
        add("nomansland.advancements.use_tap.title", "Tree Juice");
        add("nomansland.advancements.use_tap.description", "Collect Resin or Maple Syrup with a Tap and a Cauldron");
        add("nomansland.advancements.kill_anchored_mob.title", "Can't Even Die Right");
        add("nomansland.advancements.kill_anchored_mob.description", "Defeat an enemy under the influence of a Monster Anchor");
        add("nomansland.advancements.when_pigs_fly.title", "When Pigs Fly");
        add("nomansland.advancements.when_pigs_fly.description", "Ride a pig down a 40 block fall without it taking damage");
        add("nomansland.advancements.ignite_flammable_enemy.title", "Grossly Incandescent");
        add("nomansland.advancements.ignite_flammable_enemy.description", "Set an enemy on fire after dousing it in Resin Oil");
        add("nomansland.advancements.explode_ore.title", "Gets The Job Done");
        add("nomansland.advancements.explode_ore.description", "Mine Ores with an Explosive instead of a Pickaxe");
        add("nomansland.advancements.find_ancient_city.title", "Blue Hum");
        add("nomansland.advancements.find_ancient_city.description", "Find an Ancient City");
        add("nomansland.advancements.find_mineshaft.title", "To Blisters and Bedrock");
        add("nomansland.advancements.find_mineshaft.description", "Find a Mineshaft");
        add("block.nomansland.resin_cauldron", "Cauldron Filled With Resin");
        add("block.nomansland.honey_cauldron", "Cauldron Filled With Honey");
        add("block.nomansland.maple_syrup_cauldron", "Cauldron Filled With Maple Syrup");
        add("block.nomansland.resin_oil_cauldron", "Cauldron Filled With Resin Oil");
        add("block.nomansland.milk_cauldron", "Cauldron Filled With Milk");
    }
}
