package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

public class PlaceInContainer extends Behavior<PumpkinWarden> {
	public PlaceInContainer() {
		super(ImmutableMap.of(
				BWGMemoryModuleType.HOPPER_BARREL_LOCATION.get(), MemoryStatus.REGISTERED,
				MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT));
	}

	@Override
	protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {

	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {

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
