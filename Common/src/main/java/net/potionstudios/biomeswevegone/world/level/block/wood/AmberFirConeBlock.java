package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AmberFirConeBlock extends Block {
	private static final VoxelShape SHAPE = Block.box(2.0F, 4.0F, 2.0F, 14.0F, 16.0F, 14.0F);

	public AmberFirConeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		return context.getClickedFace() == Direction.DOWN ? super.getStateForPlacement(context) : null;
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}
}
