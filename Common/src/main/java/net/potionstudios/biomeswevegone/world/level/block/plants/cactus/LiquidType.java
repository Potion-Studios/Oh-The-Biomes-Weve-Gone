package net.potionstudios.biomeswevegone.world.level.block.plants.cactus;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LiquidType implements StringRepresentable {
	WATER("water"),
	HONEY("honey"),
	EMPTY("empty");

	private final String name;

	LiquidType(String name) {
		this.name = name;
	}

	@Override
	public @NotNull String getSerializedName() {
		return this.name;
	}
}
