package net.potionstudios.biomeswevegone.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

/**
 * Holds Custom Block Tags for Biomes We've Gone
 * @see net.minecraft.tags.BlockTags
 * @author Joseph T. McQuigg
 */
public class BWGBlockTags {
    public static final TagKey<Block> PALO_VERDE_LOGS = create("palo_verde_logs");
    public static final TagKey<Block> BLACK_ICE = create("black_ice");
    public static final TagKey<Block> BOREALIS_ICE = create("borealis_ice");
    public static final TagKey<Block> SCORCHED_PLANT_PLACEABLE = create("scorched_plant_placeable");
    public static final TagKey<Block> SNOWY_PLANT_PLACEABLE = create("snowy_plant_placeable");
    public static final TagKey<Block> WARPED_CORAL_PLACEABLE = create("warped_coral_placeable");
    public static final TagKey<Block> ODDITY_PLANT_PLACEABLE = create("oddity_plant_placeable");
    public static final TagKey<Block> BWG_MUSHROOM_PLACEABLE = create("bwg_mushroom_placeable");
    public static final TagKey<Block> HYDRANGEA_BUSH_PLACEABLE = create("hydrangea_bush_placeable");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, BiomesWeveGone.id(name));
    }
}
