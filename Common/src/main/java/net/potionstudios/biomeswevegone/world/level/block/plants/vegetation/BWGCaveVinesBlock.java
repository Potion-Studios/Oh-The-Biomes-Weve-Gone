package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BWGCaveVinesBlock extends CaveVinesBlock {
	private final ResourceKey<Item> item;
	private final Supplier<? extends Block> bodyBlock;
	public BWGCaveVinesBlock(Properties properties, ResourceKey<Item> item, Supplier<? extends Block> bodyBlock) {
		super(properties);
		this.item = item;
		this.bodyBlock = bodyBlock;
	}

	@Override
	protected @NotNull Block getBodyBlock() {
		return bodyBlock.get();
	}

	@Override
	public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return new ItemStack(DataFixUtils.orElse(level.registryAccess().registryOrThrow(Registries.ITEM).getOptional(item), this));
	}

	@Override
	protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
		if (state.getValue(BERRIES)) {
			Block.popResource(level, pos, getCloneItemStack(level, pos, state));
			float f = Mth.randomBetween(level.random, 0.8F, 1.2F);
			level.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
			BlockState blockState = state.setValue(BERRIES, false);
			level.setBlock(pos, blockState, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return InteractionResult.PASS;
		}
	}
}
