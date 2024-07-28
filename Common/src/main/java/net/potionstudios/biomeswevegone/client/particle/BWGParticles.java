package net.potionstudios.biomeswevegone.client.particle;

import net.minecraft.core.particles.SimpleParticleType;
import net.potionstudios.biomeswevegone.RegistrationHandler;

import java.util.function.Supplier;

public class BWGParticles {

    public static final Supplier<SimpleParticleType> THERIUM_GLINT = register("therium_glint");
    public static final Supplier<SimpleParticleType> WITCH_HAZEL_LEAVES = register("witch_hazel_leaves");
    public static final Supplier<SimpleParticleType> WHITE_SAKURA_LEAVES = register("white_sakura_leaves");
    public static final Supplier<SimpleParticleType> YELLOW_SAKURA_LEAVES = register("yellow_sakura_leaves");
    public static final Supplier<SimpleParticleType> RED_MAPLE_LEAVES = register("red_maple_leaves");
    public static final Supplier<SimpleParticleType> SILVER_MAPLE_LEAVES = register("silver_maple_leaves");
    public static final Supplier<SimpleParticleType> IRONWOOD_LEAVES = register("ironwood_leaves");
    public static final Supplier<SimpleParticleType> BOREALIS_GLINT = register("borealis_glint");
    public static final Supplier<SimpleParticleType> FIREFLY = register("firefly");


    private static Supplier<SimpleParticleType> register(String id) {
        return RegistrationHandler.registerParticle(id);
    }
}
