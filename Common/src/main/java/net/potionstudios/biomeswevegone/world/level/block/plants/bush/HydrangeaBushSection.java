package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum HydrangeaBushSection implements StringRepresentable  {
    SINGLE("single"),
    TALL_TOP("tall_top"),
    TALL_BOTTOM("tall_bottom"),
    TALLER_BOTTOM("taller_bottom"),
    TALLER_MIDDLE("taller_middle"),
    TALLER_TOP("taller_top");
    
    private final String name;

    HydrangeaBushSection(String string) {
        this.name = string;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
