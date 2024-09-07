package net.potionstudios.biomeswevegone.world.level.block.plants.tree.sapling;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BWGSaplingBlock extends SaplingBlock {

    private final @NotNull TagKey<Block> groundTag;

    public BWGSaplingBlock(@NotNull TagKey<Block> groundTag, TreeGrower treeGrower) {
        super(treeGrower, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING));
	    this.groundTag = groundTag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(groundTag);
    }
}
