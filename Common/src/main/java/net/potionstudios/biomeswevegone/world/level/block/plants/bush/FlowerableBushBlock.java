package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FlowerableBushBlock extends BushBlock implements BonemealableBlock {

    private final Supplier<FloweringBushBlock> floweringBlock;

    public FlowerableBushBlock(Properties properties, Supplier<FloweringBushBlock> floweringBlock) {
        super(properties);
        this.floweringBlock = floweringBlock;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (floweringBlock.get().defaultBlockState().canSurvive(level, pos))
            level.setBlock(pos, floweringBlock.get().defaultBlockState(), 1);
    }
}
