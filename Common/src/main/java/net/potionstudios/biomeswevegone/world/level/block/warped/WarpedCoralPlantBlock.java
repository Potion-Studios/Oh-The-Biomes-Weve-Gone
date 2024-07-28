package net.potionstudios.biomeswevegone.world.level.block.warped;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.biomeswevegone.tags.BWGBlockTags;
import org.jetbrains.annotations.NotNull;

public class WarpedCoralPlantBlock extends BaseCoralPlantBlock {

    private final VoxelShape SHAPE;

    public WarpedCoralPlantBlock(Properties properties, VoxelShape shape) {
        super(properties);
        SHAPE = shape;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    public WarpedCoralPlantBlock(VoxelShape shape) {
        this(Properties.of().sound(SoundType.WET_GRASS).strength(0.0F).noCollission().noOcclusion().lightLevel((state) -> 8), shape);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(BWGBlockTags.WARPED_CORAL_PLACEABLE);
    }
}
