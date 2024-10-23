package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FluorescentCattailPlantBlock extends CattailPlantBlock {

	public FluorescentCattailPlantBlock(Supplier<Supplier<Item>> sprout) {
		super(sprout);
	}
}
