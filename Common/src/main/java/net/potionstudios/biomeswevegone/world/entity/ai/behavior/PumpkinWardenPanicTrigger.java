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
    public PumpkinWardenPanicTrigger() {
        super(ImmutableMap.of());
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
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (ticks >= 200) {
            entity.setHiding(true);
            entity.getBrain().setActiveActivityIfPossible(Activity.HIDE);
            stop(level, entity, gameTime);
        }
        ticks++;
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        ticks = 0;
    }

    public static boolean hasHostile(LivingEntity entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean isHurt(LivingEntity entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
    }
}
