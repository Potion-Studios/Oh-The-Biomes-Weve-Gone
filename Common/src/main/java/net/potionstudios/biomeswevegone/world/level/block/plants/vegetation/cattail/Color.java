package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.cattail;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum Color implements StringRepresentable {
    NO_COLOR("default"),
    BLUE("blue"),
    GREEN("green"),
    RED("red"),
    YELLOW("yellow");

    private final String Color;

    Color(String color) {
        this.Color = color;
    }

    @Override
    public @NotNull String getSerializedName() {
        return Color;
    }
}
