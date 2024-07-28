package net.potionstudios.biomeswevegone.world.level.block.plants.tree.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.plants.tree.fruit.BWGFruitBlock;

import java.util.function.Supplier;

public class BWGFruitLeavesBlock extends LeavesBlock implements BonemealableBlock {
    private final Supplier<BlockState> fruitBlock;
    private final float tickSpawnChance;

    public BWGFruitLeavesBlock(Properties properties, Supplier<BWGFruitBlock> fruitBlock, float tickSpawnChance) {
        super(properties);
        this.fruitBlock =  () -> fruitBlock.get().defaultBlockState().setValue(BWGFruitBlock.AGE, 0);
        this.tickSpawnChance = tickSpawnChance;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        BlockPos fruitPos = pos.below();

        if (level.getBlockState(fruitPos).isAir() && random.nextFloat() < this.tickSpawnChance)
            placeFruit(level, fruitPos);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    private void placeFruit(Level level, BlockPos pos) {
        level.setBlock(pos, fruitBlock.get(), 2);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState below = level.getBlockState(pos.below());
            if (below.isAir() || below.getBlock() instanceof BWGFruitBlock && below.getValue(BWGFruitBlock.AGE) < 3)
                return random.nextBoolean();
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos.below()).isAir())
            placeFruit(level, pos.below());
        else if (level.getBlockState(pos.below()).getBlock() instanceof BWGFruitBlock)
            level.setBlock(pos.below(), level.getBlockState(pos.below()).cycle(BWGFruitBlock.AGE), 2);
    }
}
