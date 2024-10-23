package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.cattail;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FluorescentCattailPlantBlock extends CattailPlantBlock {

	public static final EnumProperty<Color> COLOR = EnumProperty.create("color", Color.class);

	public FluorescentCattailPlantBlock(Supplier<Supplier<Item>> sprout) {
		super(sprout);
	}

	@Override
	protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(COLOR);
		super.createBlockStateDefinition(builder);
	}
}

