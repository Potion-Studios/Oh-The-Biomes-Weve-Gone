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
import org.jetbrains.annotations.NotNull;

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
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        BlockPos fruitPos = pos.below();
        if (this.decaying(state)) {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
            if (level.getBlockState(fruitPos).is(fruitBlock.get().getBlock()))
                level.destroyBlock(fruitPos, true);
        } else if (level.getBlockState(fruitPos).isAir() && random.nextFloat() < this.tickSpawnChance)
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
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
        BlockState below = level.getBlockState(pos.below());
            if (below.isAir() || below.getBlock() instanceof BWGFruitBlock && below.getValue(BWGFruitBlock.AGE) < 3)
                return random.nextBoolean();
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
        if (level.getBlockState(pos.below()).isAir())
            placeFruit(level, pos.below());
        else if (level.getBlockState(pos.below()).getBlock() instanceof BWGFruitBlock)
            level.setBlock(pos.below(), level.getBlockState(pos.below()).cycle(BWGFruitBlock.AGE), 2);
    }
}
