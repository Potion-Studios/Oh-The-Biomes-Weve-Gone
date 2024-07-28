package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BWGDoublePlantBlock extends DoublePlantBlock {

    private final TagKey<Block> mayPlaceOn;

    public BWGDoublePlantBlock(BlockBehaviour.Properties properties, TagKey<Block> mayPlaceOn) {
        super(properties);
        this.mayPlaceOn = mayPlaceOn;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(this.mayPlaceOn);
    }
}
