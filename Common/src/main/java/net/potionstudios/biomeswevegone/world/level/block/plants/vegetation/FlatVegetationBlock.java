package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlatVegetationBlock extends BushBlock {

    private static final VoxelShape VOXEL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1D, 16.0D);

    private final @Nullable TagKey<Block> validGround;

    public FlatVegetationBlock(Properties properties, @Nullable TagKey<Block> validGround) {
        super(properties);
        this.validGround = validGround;
    }

    public FlatVegetationBlock(Properties properties) {
        this(properties, null);
    }

    public FlatVegetationBlock() {
        this(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return validGround == null ? super.mayPlaceOn(state, level, pos) : state.is(validGround);
    }
}
