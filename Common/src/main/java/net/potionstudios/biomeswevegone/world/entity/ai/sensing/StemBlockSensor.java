package net.potionstudios.biomeswevegone.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class StemBlockSensor extends Sensor<PumpkinWarden> {
    @Override
    protected void doTick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        entity.getBrain().setMemory(BWGMemoryModuleType.PUMPKIN_STEMS.get(), getStemPositions(entity));
    }

    private List<BlockPos> getStemPositions(PumpkinWarden entity) {
        return entity.getBrain().getMemory(BWGMemoryModuleType.PUMPKIN_STEMS.get()).orElse(List.of());
    }

    @Override
    public @NotNull Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(BWGMemoryModuleType.PUMPKIN_STEMS.get());
    }
}
