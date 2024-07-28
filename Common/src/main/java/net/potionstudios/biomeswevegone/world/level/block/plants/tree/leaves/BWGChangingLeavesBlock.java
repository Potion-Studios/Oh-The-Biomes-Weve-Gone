package net.potionstudios.biomeswevegone.world.level.block.plants.tree.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BWGChangingLeavesBlock extends BWGLeavesBlock implements BonemealableBlock {

    private final Supplier<LeavesBlock> next;
    private final float chance;

    public BWGChangingLeavesBlock(Properties properties, Supplier<LeavesBlock> next, float chance, Supplier<SimpleParticleType> particleTypes) {
        super(properties, particleTypes);
        this.next = next;
        this.chance = chance;
    }

    public BWGChangingLeavesBlock(Properties properties, Supplier<LeavesBlock> next, float chance) {
        super(properties);
        this.next = next;
        this.chance = chance;
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return !blockState.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        super.randomTick(state, serverLevel, pos, randomSource);

        if (randomSource.nextFloat() < chance)
            placeNext(state, serverLevel, pos);
    }

    private void placeNext(BlockState state, Level level, BlockPos pos) {
        level.setBlock(pos, next.get().defaultBlockState().setValue(DISTANCE, state.getValue(DISTANCE)).setValue(PERSISTENT, state.getValue(PERSISTENT)), 2);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader blockGetter, BlockPos blockPos, BlockState blockState, boolean b) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return randomSource.nextFloat() < 0.5F;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        placeNext(blockState, level, blockPos);
    }
}
