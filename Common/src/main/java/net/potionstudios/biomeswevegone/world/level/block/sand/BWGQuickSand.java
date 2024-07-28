package net.potionstudios.biomeswevegone.world.level.block.sand;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.biomeswevegone.world.damagesource.BWGDamageTypes;
import org.jetbrains.annotations.NotNull;

public class BWGQuickSand extends SandBlock {
	public BWGQuickSand(int dustColor) {
		super(dustColor, BlockBehaviour.Properties.copy(Blocks.SAND).noCollission().isValidSpawn(Blocks::never));
	}

	@Override
	public @NotNull VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity) {
			entity.makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9F));
			if (level.isClientSide) {
				RandomSource randomSource = level.getRandom();
				boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
				if (bl && randomSource.nextBoolean()) {
					level.addParticle(
							new BlockParticleOption(ParticleTypes.FALLING_DUST, state),
							entity.getX() + Mth.randomBetween(randomSource, -1.0F, 1.0F),
							entity.getEyeY() + Mth.randomBetween(randomSource,0,  1.0F),
							entity.getZ() + Mth.randomBetween(randomSource, -1.0F, 1.0F),
							Mth.randomBetween(randomSource, -1.0F, 1.0F) * 0.083333336F,
							0.05F,
							Mth.randomBetween(randomSource, -1.0F, 1.0F) * 0.083333336F
					);
				}
			}
			BlockPos headPos = new BlockPos(entity.getBlockX(), (int) entity.getEyeY(), entity.getBlockZ());
			if (level.getBlockState(headPos).getBlock() instanceof BWGQuickSand)
				entity.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(BWGDamageTypes.IN_QUICKSAND)), 0.5F);
		}

		if (!level.isClientSide) {
			if (entity.isOnFire() && (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || entity instanceof Player) && entity.mayInteract(level, pos)) {
				level.destroyBlock(pos, false);
			}

			entity.setSharedFlagOnFire(false);
		}
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!((double)fallDistance < 4.0) && entity instanceof LivingEntity livingEntity) {
			LivingEntity.Fallsounds fallsounds = livingEntity.getFallSounds();
			SoundEvent soundEvent = (double)fallDistance < 7.0 ? fallsounds.small() : fallsounds.big();
			entity.playSound(soundEvent, 1.0F, 1.0F);
		}
	}

	@Override
	public @NotNull VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}
}
