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
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
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
		if (!targetPos.closerToCenterThan(pumpkinWarden.position(), 2)) {
			tryTicks++;
			if (tryTicks > 200)
				stop(level, pumpkinWarden, gameTime);
		} else if (!pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
			BlockEntity blockEntity = level.getBlockEntity(targetPos);
			if (blockEntity instanceof HopperBlockEntity hopperBlockEntity)
				HopperBlockEntity.addItem(null, hopperBlockEntity, pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND), pumpkinWarden.getDirection());
			else if (blockEntity instanceof BarrelBlockEntity barrelBlockEntity) {
				ItemStack itemStack = pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND);

				for (int i = 0; i < barrelBlockEntity.getContainerSize(); i++) {
					ItemStack slotStack = barrelBlockEntity.getItem(i);

					if (slotStack.isEmpty()) {
						barrelBlockEntity.setItem(i, itemStack);
						barrelBlockEntity.setChanged();
						break;
					} else if (ItemStack.isSameItemSameComponents(slotStack, itemStack) && slotStack.getCount() < slotStack.getMaxStackSize()) {
						slotStack.grow(1);
						barrelBlockEntity.setChanged();
						break;
					}
				}
			} else return;
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

		Optional<BlockPos> optionalBlockPos = findHopperOrBarrel(level, pumpkinWarden.blockPosition(), 20);
		if (optionalBlockPos.isPresent()) {
			targetPos = optionalBlockPos.get();
			pumpkinWarden.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetPos));
			pumpkinWarden.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 1.0F, 0));
		} else {
			if (level.getRandom().nextBoolean())
				level.broadcastEntityEvent(pumpkinWarden, (byte) 13);
			stop(level, pumpkinWarden, gameTime);
		}

	}

	private Optional<BlockPos> findHopperOrBarrel(@NotNull ServerLevel level, BlockPos blockPos, double distance) {
		Optional<BlockPos> nearestBarrel = Optional.empty();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		for (int i = 0; i <= distance; i = i > 0 ? -i : 1 - i)
			for (int j = 0; j < distance; j++)
				for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k)
					for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
						mutableBlockPos.setWithOffset(blockPos, k, i - 1, l);
						if (blockPos.closerThan(mutableBlockPos, distance) && level.getBlockState(mutableBlockPos).getBlock() instanceof HopperBlock)
							return Optional.of(mutableBlockPos.immutable());
						else if (nearestBarrel.isEmpty() && level.getBlockState(mutableBlockPos).getBlock() instanceof BarrelBlock)
							nearestBarrel = Optional.of(mutableBlockPos.immutable());
					}

		return nearestBarrel;
	}

	@Override
	protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden) {
		return pumpkinWarden.canMove() && !pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && level.isDay();
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
