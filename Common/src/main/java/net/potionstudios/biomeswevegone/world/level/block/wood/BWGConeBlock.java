package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BWGConeBlock extends SaplingBlock {
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	private static final VoxelShape HANGING_SHAPE = Block.box(2.0F, 4.0F, 2.0F, 14.0F, 16.0F, 14.0F);

	public BWGConeBlock(TreeGrower treeGrower, Properties properties) {
		super(treeGrower, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		for(Direction direction : context.getNearestLookingDirections())
			if (direction.getAxis() == Direction.Axis.Y)
				return this.defaultBlockState().setValue(HANGING, direction == Direction.UP);

		return null;
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : super.getShape(state, level, pos, context);
	}

	@Override
	protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		if (state.getValue(HANGING))
			return Block.canSupportCenter(level, pos.above(), Direction.DOWN) || level.getBlockState(pos.above()).is(BlockTags.LEAVES);
		return super.canSurvive(state, level, pos);
	}

	protected static Direction getConnectedDirection(BlockState state) {
		return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return super.mayPlaceOn(state, level, pos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(HANGING));
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !state.getValue(HANGING);
	}
}
