package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.potionstudios.biomeswevegone.tags.BWGItemTags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BWGPumpkinBlock extends PumpkinBlock {

	private final Supplier<CarvedPumpkinBlock> carvedPumpkin;
	private final Supplier<Item> seeds;

	public BWGPumpkinBlock(Properties properties, Supplier<CarvedPumpkinBlock> carvedPumpkin, Supplier<Item> seeds) {
		super(properties);
		this.carvedPumpkin = carvedPumpkin;
		this.seeds = seeds;
	}

	@Override
	protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
		if (!stack.is(BWGItemTags.SHEARS)) {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		} else if (level.isClientSide) {
			return ItemInteractionResult.sidedSuccess(level.isClientSide());
		} else {
			Direction direction = hitResult.getDirection();
			Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : direction;
			level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.setBlock(pos, carvedPumpkin.get().defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction2), 11);
			ItemEntity itemEntity = new ItemEntity(
					level,
					(double)pos.getX() + 0.5 + (double)direction2.getStepX() * 0.65,
					(double)pos.getY() + 0.1,
					(double)pos.getZ() + 0.5 + (double)direction2.getStepZ() * 0.65,
					new ItemStack(seeds.get(), 4)
			);
			itemEntity.setDeltaMovement(
					0.05 * (double)direction2.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * (double)direction2.getStepZ() + level.random.nextDouble() * 0.02
			);
			level.addFreshEntity(itemEntity);
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			level.gameEvent(player, GameEvent.SHEAR, pos);
			player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
			return ItemInteractionResult.sidedSuccess(level.isClientSide());
		}
	}
}
