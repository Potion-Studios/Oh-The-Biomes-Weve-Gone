package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EnterPumpkinBurrow extends Behavior<PumpkinWarden> {
    public EnterPumpkinBurrow() {
        super(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden) {
        if (pumpkinWarden.isPassenger()) return false;

        Brain<PumpkinWarden> brain = pumpkinWarden.getBrain();
        GlobalPos globalPos = brain.getMemory(MemoryModuleType.HOME).get();
        if (level.dimension() != globalPos.dimension()) return false;

        BlockState blockState = level.getBlockState(globalPos.pos());
        if (!blockState.is(BWGBlocks.PUMPKIN_BURROW.get())) return false;
        BlockPos entrancePos = globalPos.pos().relative(blockState.getValue(PumpkinBurrowBlock.FACING));

        return level.getBlockState(entrancePos).getCollisionShape(level, entrancePos).isEmpty() && entrancePos.equals(pumpkinWarden.blockPosition()) && blockState.getBlock() instanceof PumpkinBurrowBlock && !blockState.getValue(PumpkinBurrowBlock.OCCUPIED);
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
        Brain<?> brain = pumpkinWarden.getBrain();
        if (brain.hasMemoryValue(MemoryModuleType.DOORS_TO_CLOSE)) {
            Set<GlobalPos> set = brain.getMemory(MemoryModuleType.DOORS_TO_CLOSE).get();
            Optional<List<LivingEntity>> optional;
            if (brain.hasMemoryValue(MemoryModuleType.NEAREST_LIVING_ENTITIES)) {
                optional = brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES);
            } else {
                optional = Optional.empty();
            }

            InteractWithDoor.closeDoorsThatIHaveOpenedOrPassedThrough(level, pumpkinWarden, null, null, set, optional);
        }

        pumpkinWarden.startSleeping(pumpkinWarden.getBrain().getMemory(MemoryModuleType.HOME).get().pos());
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return false;
    }
}
