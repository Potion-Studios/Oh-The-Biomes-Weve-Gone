package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

public class PumpkinWardenPanicTrigger extends Behavior<PumpkinWarden> {
    private int ticks = 0;
    private long endTimestamp;

    public PumpkinWardenPanicTrigger() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        return !entity.isHiding();
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        return isHurt(entity) || hasHostile(entity);
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (isHurt(entity) || hasHostile(entity)) {
            Brain<PumpkinWarden> brain = entity.getBrain();
            if (!brain.isActive(Activity.PANIC)) {
                brain.eraseMemory(MemoryModuleType.PATH);
                brain.eraseMemory(MemoryModuleType.WALK_TARGET);
                brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
                brain.eraseMemory(MemoryModuleType.INTERACTION_TARGET);
            }
            brain.setActiveActivityIfPossible(Activity.PANIC);
        }
        endTimestamp = gameTime + 200;
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (ticks >= 200) {
            entity.getBrain().setActiveActivityIfPossible(Activity.HIDE);
            entity.hide();
            stop(level, entity, gameTime);
        }
        ticks++;
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return gameTime > endTimestamp;
    }

    private static boolean hasHostile(LivingEntity entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
    }

    private static boolean isHurt(LivingEntity entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
    }
}
