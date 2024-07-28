package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class ShrubBlock extends BushBlock implements BonemealableBlock {

	private static final IntegerProperty STAGE = BlockStateProperties.STAGE;
	private final Supplier<AbstractTreeGrower> treeGrower;

	public ShrubBlock(Supplier<AbstractTreeGrower> treeGrower) {
		super(BlockBehaviour.Properties.of().noCollission().noOcclusion().sound(SoundType.SWEET_BERRY_BUSH).mapColor(MapColor.COLOR_GREEN));
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
		this.treeGrower = treeGrower;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.SAND);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		EntityType<?> entityType = entity.getType();
		if (entity instanceof LivingEntity && entityType != EntityType.FOX && entityType != EntityType.BEE)
			entity.makeStuckInBlock(state, new Vec3(0.8D, 0.75D, 0.8D));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(STAGE);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return random.nextBoolean();
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		if (treeGrower != null) treeGrower.get().growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
		else level.setBlockAndUpdate(pos, state.cycle(STAGE));
	}
}
