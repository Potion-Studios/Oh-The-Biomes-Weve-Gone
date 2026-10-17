package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.biomeswevegone.client.particle.BWGParticles;
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
	protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		return canSurvive(state, level, pos) ? state : Blocks.AIR.defaultBlockState();
	}

	@Override
	protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		return Block.canSupportCenter(level, pos.above(), Direction.DOWN) || level.getBlockState(pos.above()).is(BlockTags.LEAVES);
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, level, pos, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		level.addParticle(BWGParticles.BOREALIS_GLINT.get(), x + random.nextDouble(), y + 0.7D, z + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		BlockPos.MutableBlockPos level0 = new BlockPos.MutableBlockPos();

		for(int i = 0; i < 14; ++i) {
			level0.set(x + Mth.nextInt(random, -5, 5), y - random.nextInt(5), z + Mth.nextInt(random, -5, 5));
			BlockState state2 = level.getBlockState(level0);
			if (!state2.isCollisionShapeFullBlock(level, level0))
				level.addParticle(BWGParticles.BOREALIS_GLINT.get(), (double)level0.getX() + random.nextDouble(), (double)level0.getY() + random.nextDouble(), (double)level0.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}
