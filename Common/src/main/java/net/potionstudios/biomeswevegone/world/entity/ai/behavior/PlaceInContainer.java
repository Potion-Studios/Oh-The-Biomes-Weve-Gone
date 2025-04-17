package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlaceInContainer extends Behavior<PumpkinWarden> {
	private BlockPos targetPos;
	protected int tryTicks;

	public PlaceInContainer() {
		super(ImmutableMap.of(
				BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get(), MemoryStatus.REGISTERED,
				MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT));
	}

	@Override
	protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
		if (targetPos == null) return;
		if (!targetPos.closerToCenterThan(entity.position(), 2.0)) {
			tryTicks++;
			if (tryTicks > 200)
				stop(level, entity, gameTime);
		} else if (entity.getCarriedBlock() != null) {
			//TODO: place the block in the container
			entity.setCarriedBlock(null);
			entity.getBrain().setMemory(BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get(), targetPos);
			stop(level, entity, gameTime);
		} else stop(level, entity, gameTime);
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
		if (entity.getBrain().hasMemoryValue(BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get())) {
			targetPos = entity.getBrain().getMemory(BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get()).get();
			Block block = level.getBlockState(targetPos).getBlock();
			if ((block instanceof HopperBlock) || (block instanceof BarrelBlock)) {
				entity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetPos));
				entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 1.0F, 0));
				return;
			}
		}

		Optional<BlockPos> optionalBlockPos = findHopperOrBarrel(level, entity.blockPosition());
		if (optionalBlockPos.isPresent())
			targetPos = optionalBlockPos.get();
		else stop(level, entity, gameTime);

		entity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetPos));
		entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 1.0F, 0));
	}

	private Optional<BlockPos> findHopperOrBarrel(@NotNull ServerLevel level, BlockPos entityPosition) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (int y = 2; -2 <= y; y--)
			for (int x = -25; x < 25; x++)
				for (int z = -25; z < 25; z++) {
					mutableBlockPos.setWithOffset(entityPosition, x, y, z);
					if (level.getBlockState(mutableBlockPos).getBlock() instanceof HopperBlock)
						return Optional.of(mutableBlockPos.immutable());
				}
		return Optional.empty();
	}

	@Override
	protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
		return entity.canMove() && entity.getCarriedBlock() != null;
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
		return checkExtraStartConditions(level, entity);
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
		entity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
		entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
	}
}
