package net.potionstudios.biomeswevegone.client.particle.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

/**
 * Falling Leaf Particle for Oh The Biomes We've Gone.
 * @see SimpleParticleType
 * @see SimpleParticleType
 * @author Joseph T. McQuigg
 */
public class FallingLeafParticle extends SingleQuadParticle {
    FallingLeafParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite sprite) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = true;
        this.friction = 1.0F;
        this.gravity = 1F;
        this.yd = -Math.abs(this.yd);
        this.setSize(0.01F, 0.01F);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0 || this.onGround)
            this.remove();
        else {
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.99D;
            this.zd *= 0.99D;
        }
    }

    @Override
    protected @NotNull Layer getLayer() {
        return Layer.OPAQUE;
    }

    public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, @NotNull RandomSource random) {
            FallingLeafParticle leaf = new FallingLeafParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprite.get(random));
            leaf.lifetime = Mth.randomBetweenInclusive(random, 500, 1000);
            leaf.setColor(1.0f, 1.0f, 1.0f);
            leaf.setSprite(this.sprite.get(random.nextInt(16), 16));
            return leaf;
        }
    }
}
