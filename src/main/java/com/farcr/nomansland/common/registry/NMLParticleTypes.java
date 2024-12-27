package com.farcr.nomansland.common.registry;

import com.farcr.nomansland.NoMansLand;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NMLParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, NoMansLand.MODID);
    public static final Supplier<SimpleParticleType> PALE_CHERRY_LEAVES = register("pale_cherry_leaves");
    
    public static final Supplier<SimpleParticleType> CAVE_DUST = register("cave_dust");
    
    public static final Supplier<SimpleParticleType> RESIN_DROPLET = register("resin_droplet");
    
    public static final Supplier<SimpleParticleType> RESIN_DROPLET_FLAT = register("resin_droplet_flat");
    
    public static final Supplier<SimpleParticleType> MAPLE_SYRUP_DROPLET = register("maple_syrup_droplet");
    
    public static final Supplier<SimpleParticleType> MAPLE_SYRUP_DROPLET_FLAT = register("maple_syrup_droplet_flat");

    public static final Supplier<SimpleParticleType> OIL = register("oil");

    public static final Supplier<SimpleParticleType> OIL_FLAT = register("oil_flat");
    
    public static final Supplier<SimpleParticleType> RESIN_OIL_BUBBLE = register("resin_oil_bubble");
    
    public static final Supplier<SimpleParticleType> RESIN_OIL_BUBBLE_POP = register("resin_oil_bubble_pop");
    
    public static final Supplier<SimpleParticleType> MALEVOLENT_FLAME = register("malevolent_flame");
            
    public static final Supplier<SimpleParticleType> MALEVOLENT_EMBERS = register("malevolent_embers");

    public static final Supplier<SimpleParticleType> SCULK_AMBIENCE = register("sculk_ambience");

    public static Supplier<SimpleParticleType> register(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
    }
}
