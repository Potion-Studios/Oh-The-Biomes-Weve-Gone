package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BWGPlacementBushBlock extends BushBlock {
    private final VoxelShape SHAPE;
    private final TagKey<Block> validGround;
    public BWGPlacementBushBlock(Properties properties, VoxelShape shape, TagKey<Block> validGround) {
        super(properties);
        this.SHAPE = shape;
        this.validGround = validGround;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 Vector3d = state.getOffset(level, pos);
        return SHAPE.move(Vector3d.x(), Vector3d.y(), Vector3d.z());
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(validGround);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return this.mayPlaceOn(level.getBlockState(pos.below()), level, pos);
    }
}
