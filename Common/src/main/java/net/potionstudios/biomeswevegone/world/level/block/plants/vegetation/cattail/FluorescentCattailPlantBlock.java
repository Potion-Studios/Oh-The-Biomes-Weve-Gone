package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.cattail;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FluorescentCattailPlantBlock extends CattailPlantBlock {

	public static final EnumProperty<Color> COLOR = EnumProperty.create("color", Color.class);

	public FluorescentCattailPlantBlock(Supplier<Supplier<Item>> sprout) {
		super(BlockBehaviour.Properties.of().noCollission().noCollission().sound(SoundType.WET_GRASS).strength(0.0F).lightLevel(level -> 12), sprout);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false).setValue(COLOR, Color.NO_COLOR));
	}

	@Override
	protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
		if (stack.is(Items.BLUE_DYE))
			level.setBlockAndUpdate(pos, state.setValue(COLOR, Color.BLUE));
		else if (stack.is(Items.RED_DYE))
			level.setBlockAndUpdate(pos, state.setValue(COLOR, Color.RED));
		else if (stack.is(Items.GREEN_DYE))
			level.setBlockAndUpdate(pos, state.setValue(COLOR, Color.GREEN));
		else if (stack.is(Items.YELLOW_DYE))
			level.setBlockAndUpdate(pos, state.setValue(COLOR, Color.YELLOW));
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(COLOR);
		super.createBlockStateDefinition(builder);
	}
}

