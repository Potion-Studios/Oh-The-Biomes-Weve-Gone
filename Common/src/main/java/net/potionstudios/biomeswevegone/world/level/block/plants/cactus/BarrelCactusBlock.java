package net.potionstudios.biomeswevegone.world.level.block.plants.cactus;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.potionstudios.biomeswevegone.tags.BWGItemTags;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import org.jetbrains.annotations.NotNull;

public class BarrelCactusBlock extends BWGCactusBlock implements BonemealableBlock {
	public BarrelCactusBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.CACTUS).noOcclusion());
	}

	@Override
	public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(BWGItemTags.SHEARS)) {
			if (!level.isClientSide()) {
				level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.setBlockAndUpdate(pos, BWGBlocks.CARVED_BARREL_CACTUS.get().defaultBlockState());
				itemStack.hurtAndBreak(1, player, (player1 -> player1.broadcastBreakEvent(hand)));
				level.gameEvent(player, GameEvent.SHEAR, pos);
				player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);
	}

	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(BlockTags.SAND) && state.getFluidState().isEmpty();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return mayPlaceOn(level.getBlockState(pos.above()), level, pos);
	}

	@Override
	public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (!state.canSurvive(level, pos))
			return Blocks.AIR.defaultBlockState();
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		if (!state.is(BWGBlocks.FLOWERING_BARREL_CACTUS.get()))
			level.setBlockAndUpdate(pos, BWGBlocks.FLOWERING_BARREL_CACTUS.get().defaultBlockState());
	}
}
