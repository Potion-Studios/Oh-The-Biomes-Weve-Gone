package net.potionstudios.biomeswevegone.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StemBlockSensor extends Sensor<PumpkinWarden> {
    @Override
    protected void doTick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        List<BlockPos> stemPositions = new ArrayList<>();
        BlockPos blockPos = entity.blockPosition();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int y = 2; -2 <= y; y--)
            for (int x = -25; x < 25; x++)
                for (int z = -25; z < 25; z++) {
                    mutableBlockPos.setWithOffset(blockPos, x, y, z);
                    BlockState state = level.getBlockState(mutableBlockPos);
                    if (state.getBlock() instanceof AttachedStemBlock)
                        stemPositions.add(mutableBlockPos.relative(state.getValue(AttachedStemBlock.FACING)));
                    else if (state.getBlock() instanceof StemBlock && state.getValue(StemBlock.AGE) == StemBlock.MAX_AGE)
                        stemPositions.add(mutableBlockPos);
                }
        if (stemPositions.isEmpty()) entity.getBrain().eraseMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get());
        else entity.getBrain().setMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get(), stemPositions);
    }

    @Override
    public @NotNull Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get());
    }
}
