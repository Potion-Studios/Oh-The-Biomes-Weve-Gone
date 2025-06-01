package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

public class Unhide extends Behavior<PumpkinWarden> {
    public Unhide() {
        super(ImmutableMap.of(
                MemoryModuleType.NEAREST_HOSTILE, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.HURT_BY_ENTITY, MemoryStatus.REGISTERED
        ));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        return entity.isHiding();
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        stop(level, entity, gameTime);
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        entity.getBrain().eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
        entity.unhide();
        entity.getBrain().updateActivityFromSchedule(level.getDayTime(), level.getGameTime());
    }
}
