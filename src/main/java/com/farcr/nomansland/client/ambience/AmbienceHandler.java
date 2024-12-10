package com.farcr.nomansland.client.ambience;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Vector3f;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME, modid = NoMansLand.MODID)
public class AmbienceHandler {
    public static final SurfaceAmbience SURFACE_AMBIENCE_HANDLER = new SurfaceAmbience(Minecraft.getInstance());
    public static final FogModifierHandler FOG_MODIFIER_HANDLER = new FogModifierHandler();

    @SubscribeEvent
    public static void onTick(ClientTickEvent.Pre event) {
        Minecraft.getInstance().getProfiler().push("nomansland.ambienceTick");

        boolean shouldUpdate = shouldUpdate();
        SURFACE_AMBIENCE_HANDLER.tick(shouldUpdate);
        if (shouldUpdate)
            FOG_MODIFIER_HANDLER.tick(Minecraft.getInstance().level, Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), SURFACE_AMBIENCE_HANDLER.getAboveGroundFactor(1.0F));

        Minecraft.getInstance().getProfiler().pop();
    }

    private static boolean shouldUpdate() {
        return Minecraft.getInstance().level != null &&
               Minecraft.getInstance().level.isLoaded(Minecraft.getInstance().gameRenderer.getMainCamera().getBlockPosition()) &&
               !Minecraft.getInstance().isPaused();
    }
}
