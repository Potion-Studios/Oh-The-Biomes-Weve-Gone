package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.level.block.entities.PumpkinBurrowBlockEntity;
import org.jetbrains.annotations.NotNull;

public class EnterPumpkinBurrow extends Behavior<PumpkinWarden> {
    public EnterPumpkinBurrow() {
        super(Util.make(() -> ImmutableMap.of(
                MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT
        )));
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (entity.getBrain().getMemory(MemoryModuleType.HOME).isPresent()) {
            BlockPos pos = entity.getBrain().getMemory(MemoryModuleType.HOME).get().pos();
            if (pos.closerToCenterThan(entity.position(), 1.5))
                if (level.getBlockEntity(pos) instanceof PumpkinBurrowBlockEntity pumpkinBurrow) {
                    pumpkinBurrow.addOccupant(entity);
                    stop(level, entity, gameTime);
                }
        }
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        return !entity.isSleeping();
    }
}
