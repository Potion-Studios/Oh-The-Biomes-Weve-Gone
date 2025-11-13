package net.potionstudios.biomeswevegone.client.particle.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class FireFlyParticle extends SingleQuadParticle {
    FireFlyParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite sprite) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
        this.gravity = 0.0F;
        this.lifetime = this.random.nextInt(250, 500);
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
			this.xd += (this.random.nextDouble() - 0.5) * 0.02;
			this.yd += (this.random.nextDouble() - 0.5) * 0.02;
			this.zd += (this.random.nextDouble() - 0.5) * 0.02;
			this.xd *= 0.9;
			this.yd *= 0.9;
			this.zd *= 0.9;
		}
	}

    @Override
    protected @NotNull Layer getLayer() {
        return Layer.OPAQUE;
    }

    public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, @NotNull RandomSource random) {
            FireFlyParticle fireFlyParticle = new FireFlyParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprite.get(random));
            fireFlyParticle.lifetime = random.nextInt(250, 500);
            fireFlyParticle.setColor(1.0f, 1.0f, 1.0f);
            fireFlyParticle.setSprite(this.sprite.get(random.nextInt(16), 16));
            return fireFlyParticle;
        }
    }
}
