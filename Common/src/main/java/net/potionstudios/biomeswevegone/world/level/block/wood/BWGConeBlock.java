package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class BWGConeBlock extends SaplingBlock {
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public BWGConeBlock(TreeGrower treeGrower, Properties properties) {
		super(treeGrower, properties);
		this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false));
	}

	@Override
	protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		return super.canSurvive(state, level, pos);
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		if (state.getValue(HANGING)) {
			return level.getBlockState(pos.above()).isCollisionShapeFullBlock(level, pos.above());
		} else return super.mayPlaceOn(state, level, pos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(HANGING));
	}
}
