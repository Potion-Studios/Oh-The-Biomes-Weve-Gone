package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

public class Unhide extends Behavior<PumpkinWarden> {

    private int ticks;

    public Unhide() {
        super(ImmutableMap.of(
                MemoryModuleType.NEAREST_HOSTILE, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.HURT_BY_ENTITY, MemoryStatus.VALUE_PRESENT
        ));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        return entity.isHiding();
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        ticks++;
        if (ticks >= 200) {
            entity.setHiding(false);
            stop(level, entity, gameTime);
        }
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        ticks = 0;
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        entity.getBrain().updateActivityFromSchedule(level.getDayTime(), level.getGameTime());
        entity.getBrain().eraseMemory(MemoryModuleType.NEAREST_HOSTILE);
        entity.getBrain().eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
        ticks = 0;
    }
}
