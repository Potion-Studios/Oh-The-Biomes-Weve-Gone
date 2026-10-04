package net.potionstudios.biomeswevegone.client.particle.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Falling Leaf Particle for Oh The Biomes We've Gone.
 * @see TextureSheetParticle
 * @see SimpleParticleType
 * @author Joseph T. McQuigg
 */

public class FallingLeafParticle extends TextureSheetParticle {
    private static final float ACCELERATION_SCALE = 0.0025F;
    private static final int INITIAL_LIFETIME = 300;
    private static final int CURVE_ENDPOINT_TIME = 300;
    private float rotSpeed;
    private final float spinAcceleration;
    private final float windBig;
    private final boolean swirl;
    private final boolean flowAway;
    private final double xaFlowScale;
    private final double zaFlowScale;
    private final double swirlPeriod;

    FallingLeafParticle(final ClientLevel level, final double x, final double y, final double z, final TextureAtlasSprite sprite, final float fallAcceleration, final float sideAcceleration, final boolean swirl, final boolean flowAway, final float scale, final float startVelocity) {
        super(level, x, y, z);
        this.setSprite(sprite);

        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? (double)-30.0F : (double)30.0F);
        this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? (double)-5.0F : (double)5.0F);
        this.windBig = sideAcceleration;
        this.swirl = swirl;
        this.flowAway = flowAway;
        this.lifetime = 300;
        this.gravity = fallAcceleration * 1.2F * 0.0025F;
        float size = scale * (this.random.nextBoolean() ? 0.05F : 0.075F);
        this.quadSize = size;
        this.setSize(size, size);
        this.friction = 1.0F;
        this.yd = (double)(-startVelocity);
        float particleRandom = this.random.nextFloat();
        this.xaFlowScale = Math.cos(Math.toRadians((double)(particleRandom * 60.0F))) * (double)this.windBig;
        this.zaFlowScale = Math.sin(Math.toRadians((double)(particleRandom * 60.0F))) * (double)this.windBig;
        this.swirlPeriod = Math.toRadians((double)(1000.0F + particleRandom * 3000.0F));
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float aliveTicks = (float)(300 - this.lifetime);
            float relativeAge = Math.min(aliveTicks / 300.0F, 1.0F);
            double xa = (double)0.0F;
            double za = (double)0.0F;
            if (this.flowAway) {
                xa += this.xaFlowScale * Math.pow((double)relativeAge, (double)1.25F);
                za += this.zaFlowScale * Math.pow((double)relativeAge, (double)1.25F);
            }

            if (this.swirl) {
                xa += (double)relativeAge * Math.cos((double)relativeAge * this.swirlPeriod) * (double)this.windBig;
                za += (double)relativeAge * Math.sin((double)relativeAge * this.swirlPeriod) * (double)this.windBig;
            }

            this.xd += xa * (double)0.0025F;
            this.zd += za * (double)0.0025F;
            this.yd -= (double)this.gravity;
            this.rotSpeed += this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == (double)0.0F || this.zd == (double)0.0F)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd *= (double)this.friction;
                this.yd *= (double)this.friction;
                this.zd *= (double)this.friction;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class CherryProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public CherryProvider(final SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(final SimpleParticleType options, final ClientLevel level, final double x, final double y, final double z, final double xAux, final double yAux, final double zAux) {
            return new FallingLeafParticle(level, x, y, z, this.sprites.get(level.random), 0.25F, 2.0F, false, true, 1.5F, 0.0F);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class PaleOakProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public PaleOakProvider(final SpriteSet sprites) {
            this.sprites = sprites;
        }

        public @NotNull Particle createParticle(@NotNull final SimpleParticleType var1, @NotNull ClientLevel level, final double x, final double y, final double z, final double xAux, final double yAux, final double zAux) {
            return new FallingLeafParticle(level, x, y, z, this.sprites.get(level.random), 0.07F, 10.0F, true, false, 2.0F, 0.021F);
        }
    }

//    @Environment(EnvType.CLIENT)
//    public static class TintedLeavesProvider implements ParticleProvider<ColorParticleOption> {
//        private final SpriteSet sprites;
//
//        public TintedLeavesProvider(final SpriteSet sprites) {
//            this.sprites = sprites;
//        }
//
//        public @NotNull Particle createParticle(@NotNull final ColorParticleOption options, @NotNull ClientLevel level, final double x, final double y, final double z, final double xAux, final double yAux, final double zAux) {
//            FallingLeafParticle particle = new FallingLeafParticle(level, x, y, z, this.sprites.get(level.random), 0.07F, 10.0F, true, false, 2.0F, 0.021F);
//            particle.setColor(options.getRed(), options.getGreen(), options.getBlue());
//            return particle;
//        }
//    }
}