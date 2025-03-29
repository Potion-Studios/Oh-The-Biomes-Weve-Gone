package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DestroyPumpkin extends Behavior<PumpkinWarden> {

    public DestroyPumpkin(Map<MemoryModuleType<?>, MemoryStatus> entryCondition) {
        super(entryCondition);
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden owner, long gameTime) {
        super.tick(level, owner, gameTime);
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        return super.canStillUse(level, entity, gameTime);
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        super.start(level, entity, gameTime);
    }

    @Override
    protected boolean hasRequiredMemories(@NotNull PumpkinWarden owner) {
        return super.hasRequiredMemories(owner);
    }
}
