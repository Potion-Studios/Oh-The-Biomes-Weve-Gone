package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
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
}
