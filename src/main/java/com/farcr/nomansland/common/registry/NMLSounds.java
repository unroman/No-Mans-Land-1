package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMLSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, NoMansLand.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKES_EXTEND = registerSound("block.spike_trap.spikes_extend");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKES_RETRACT = registerSound("block.spike_trap.spikes_retract");

    public static final DeferredHolder<SoundEvent, SoundEvent> MONSTER_RESURRECTION = registerSound("block.monster_anchor.monster_resurrection");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCHOR_ACTIVATES = registerSound("block.monster_anchor.activate");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCHOR_DEACTIVATES = registerSound("block.monster_anchor.deactivate");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_DRINK_MILK = registerSound("entity.player.drink_milk");
    public static final DeferredHolder<SoundEvent, SoundEvent> STICKY_CAULDRON_SLIDE = registerSound("entity.generic.sticky_cauldron_slide");
    public static final DeferredHolder<SoundEvent, SoundEvent> HONEYCOMB_CONSUMED = registerSound("item.honeycomb.consumed");
    public static final DeferredHolder<SoundEvent, SoundEvent> BASS_AMBIENT = registerSound("entity.billhook_bass.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> BASS_FLOP = registerSound("entity.billhook_bass.flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> BASS_HURT = registerSound("entity.billhook_bass.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> BASS_DEATH = registerSound("entity.billhook_bass.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> DEER_AMBIENT = registerSound("entity.deer.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEER_HURT = registerSound("entity.deer.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEER_DEATH = registerSound("entity.deer.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEER_STEP = registerSound("entity.deer.step");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(NoMansLand.MODID, name)));
    }
}
