package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BWGCaveVinesPlantBlock extends CaveVinesPlantBlock {
	private final ResourceKey<Item> item;
	private final Supplier<? extends Block> headBlock;
	public BWGCaveVinesPlantBlock(Properties properties, ResourceKey<Item> item, Supplier<? extends Block> headBlock) {
		super(properties);
		this.item = item;
		this.headBlock = headBlock;
	}

	@Override
	protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
		return (GrowingPlantHeadBlock) headBlock.get();
	}

	@Override
	public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return new ItemStack(DataFixUtils.orElse(level.registryAccess().registryOrThrow(Registries.ITEM).getOptional(item), this));
	}
}
