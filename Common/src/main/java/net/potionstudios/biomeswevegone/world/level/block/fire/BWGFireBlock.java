package net.potionstudios.biomeswevegone.world.level.block.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BWGFireBlock extends BaseFireBlock {

    private final Supplier<Block> survivorBlock;

    public BWGFireBlock(Properties properties, Supplier<Block> survivorBlock) {
        super(properties, 3.5F);
        this.survivorBlock = survivorBlock;
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return this.survivorBlock.get() == level.getBlockState(pos.below()).getBlock();
    }
}
