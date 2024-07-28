package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BoneMealGrassBlock extends TallGrassBlock {

    private final Supplier<DoublePlantBlock> doublePlant;
    private final TagKey<Block> mayPlaceOn;
    public BoneMealGrassBlock(Properties properties, Supplier<DoublePlantBlock> doublePlant, TagKey<Block> mayPlaceOn) {
        super(properties);
        this.doublePlant = doublePlant;
        this.mayPlaceOn = mayPlaceOn;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(mayPlaceOn);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        DoublePlantBlock doublePlantBlock = doublePlant.get();
        if (doublePlantBlock.defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above()))
            DoublePlantBlock.placeAt(level, doublePlantBlock.defaultBlockState(), pos, 2);
    }
}
