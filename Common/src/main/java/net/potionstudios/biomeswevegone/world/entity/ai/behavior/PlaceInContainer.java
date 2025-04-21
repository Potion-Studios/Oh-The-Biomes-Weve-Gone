package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.item.ItemStack;
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
	protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
		if (targetPos == null) return;
		if (!targetPos.closerToCenterThan(pumpkinWarden.position(), 1.6)) {
			tryTicks++;
			if (tryTicks > 200)
				stop(level, pumpkinWarden, gameTime);
		} else if (!pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
			//TODO: place the block in the container
			pumpkinWarden.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
			pumpkinWarden.getBrain().setMemory(BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get(), targetPos);
			stop(level, pumpkinWarden, gameTime);
		} else stop(level, pumpkinWarden, gameTime);
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
		if (pumpkinWarden.getBrain().hasMemoryValue(BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get()) && pumpkinWarden.getBrain().getMemory(BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get()).isPresent()) {
			targetPos = pumpkinWarden.getBrain().getMemory(BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get()).get();
			Block block = level.getBlockState(targetPos).getBlock();
			if ((block instanceof HopperBlock) || (block instanceof BarrelBlock)) {
				pumpkinWarden.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetPos));
				pumpkinWarden.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 1.0F, 0));
				return;
			}
		}

		Optional<BlockPos> optionalBlockPos = findHopperOrBarrel(level, pumpkinWarden.blockPosition());
		if (optionalBlockPos.isPresent()) {
			targetPos = optionalBlockPos.get();
			pumpkinWarden.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetPos));
			pumpkinWarden.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 1.0F, 0));
		} else stop(level, pumpkinWarden, gameTime);

	}

	private Optional<BlockPos> findHopperOrBarrel(@NotNull ServerLevel level, BlockPos entityPosition) {
		Optional<BlockPos> nearestBarrel = Optional.empty();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (int y = 2; -2 <= y; y--)
			for (int x = -25; x < 25; x++)
				for (int z = -25; z < 25; z++) {
					mutableBlockPos.setWithOffset(entityPosition, x, y, z);
					if (level.getBlockState(mutableBlockPos).getBlock() instanceof HopperBlock)
						return Optional.of(mutableBlockPos.immutable());
					else if (nearestBarrel.isEmpty() && level.getBlockState(mutableBlockPos).getBlock() instanceof BarrelBlock)
						nearestBarrel = Optional.of(mutableBlockPos.immutable());
				}
		return nearestBarrel;
	}

	@Override
	protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden) {
		return pumpkinWarden.canMove() && !pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
		return checkExtraStartConditions(level, pumpkinWarden);
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
		pumpkinWarden.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
		pumpkinWarden.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
	}
}
