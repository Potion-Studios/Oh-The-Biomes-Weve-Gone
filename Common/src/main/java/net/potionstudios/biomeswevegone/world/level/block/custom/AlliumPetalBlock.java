package net.potionstudios.biomeswevegone.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AlliumPetalBlock extends Block {
	private final Supplier<SimpleParticleType> particle;

	public AlliumPetalBlock(Properties properties, Supplier<SimpleParticleType> particle) {
		super(properties);
		this.particle = particle;
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (random.nextDouble() < 0.02F) {
			BlockPos below = pos.below();
			BlockState blockState = level.getBlockState(below);
			if (!blockState.canOcclude() || !blockState.isFaceSturdy(level, below, Direction.UP)) {
				double x = (double) pos.getX() + random.nextDouble();
				double y = (double) pos.getY() - 0.05;
				double z = (double) pos.getZ() + random.nextDouble();
				level.addParticle(particle.get(), x, y, z, 0.0, 0, 0.0);
			}
		}
	}
}
