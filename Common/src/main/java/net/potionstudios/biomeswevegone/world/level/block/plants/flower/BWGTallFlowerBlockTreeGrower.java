package net.potionstudios.biomeswevegone.world.level.block.plants.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BWGTallFlowerBlockTreeGrower extends BWGTallFlowerBlock {

    private final Supplier<AbstractTreeGrower> treeGrower;

    public BWGTallFlowerBlockTreeGrower(Properties properties, TagKey<Block> validGround, Supplier<AbstractTreeGrower> treeGrower) {
        super(properties, validGround);
        this.treeGrower = treeGrower;
    }

    public BWGTallFlowerBlockTreeGrower(Properties properties, Supplier<AbstractTreeGrower> treeGrower) {
        super(properties);
        this.treeGrower = treeGrower;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return random.nextBoolean();
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        level.setBlockAndUpdate(state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below(), Blocks.AIR.defaultBlockState());
        treeGrower.get().growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
    }
}
