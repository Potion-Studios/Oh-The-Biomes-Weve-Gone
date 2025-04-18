package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EnterPumpkinBurrow extends Behavior<PumpkinWarden> {
    private long nextOkStartTime;

    public EnterPumpkinBurrow() {
        super(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        if (entity.isPassenger()) return false;

        Brain<PumpkinWarden> brain = entity.getBrain();
        GlobalPos globalPos = brain.getMemory(MemoryModuleType.HOME).get();
        if (level.dimension() != globalPos.dimension()) return false;

        BlockState blockState = level.getBlockState(globalPos.pos());
        return globalPos.pos().closerToCenterThan(entity.position(), 1) && blockState.getBlock() instanceof PumpkinBurrowBlock && !blockState.getValue(PumpkinBurrowBlock.OCCUPIED);
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (gameTime > this.nextOkStartTime) {
            Brain<?> brain = entity.getBrain();
            if (brain.hasMemoryValue(MemoryModuleType.DOORS_TO_CLOSE)) {
                Set<GlobalPos> set = brain.getMemory(MemoryModuleType.DOORS_TO_CLOSE).get();
                Optional<List<LivingEntity>> optional;
                if (brain.hasMemoryValue(MemoryModuleType.NEAREST_LIVING_ENTITIES)) {
                    optional = brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES);
                } else {
                    optional = Optional.empty();
                }

                InteractWithDoor.closeDoorsThatIHaveOpenedOrPassedThrough(level, entity, null, null, set, optional);
            }

            entity.startSleeping(entity.getBrain().getMemory(MemoryModuleType.HOME).get().pos());
        }
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return false;
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (entity.isSleeping()); {
            entity.stopSleeping();
            this.nextOkStartTime = gameTime + 40L;
        }
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
	    return entity.getBrain().getMemory(MemoryModuleType.HOME).filter(globalPos -> entity.getBrain().isActive(Activity.REST) && globalPos.pos().closerToCenterThan(entity.position(), 2)).isPresent();
    }
}
