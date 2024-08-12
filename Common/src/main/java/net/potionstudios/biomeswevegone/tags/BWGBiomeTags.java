package net.potionstudios.biomeswevegone.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

/**
 * Holds Custom Biome Tags for Biomes We've Gone
 * @see net.minecraft.tags.BlockTags
 * @author Joseph T. McQuigg
 */
public class BWGBiomeTags {

    public static final TagKey<Biome> SHARPENED_ROCKS = create("sharpened_rocks");
    public static final TagKey<Biome> IRONWOOD_GOUR_PLATEAU = create("ironwood_gour_plateau");
    public static final TagKey<Biome> LARGE_COLD_LAKE = create("large_cold_lake");
    public static final TagKey<Biome> LUSH_ARCH = create("lush_arch");
    public static final TagKey<Biome> RED_ROCK_ARCH = create("red_rock_arch");
    public static final TagKey<Biome> DRIPSTONE_ARCH = create("dripstone_arch");
    public static final TagKey<Biome> CANYON = create("canyon");
    public static final TagKey<Biome> HAS_TERRAIN_ADDITIONS = create("has_terrain_additions");

    public static final TagKey<Biome> OVERWORLD = create("overworld");

    public static final TagKey<Biome> HOT_OVERWORLD = create("climate/hot/overworld");
    public static final TagKey<Biome> WET_OVERWORLD = create("climate/wet/overworld");
    public static final TagKey<Biome> DRY_OVERWORLD = create("climate/dry/overworld");
    public static final TagKey<Biome> COLD_OVERWORLD = create("climate/cold/overworld");

    public static final TagKey<Biome> SPARSE_OVERWORLD = create("density/sparse/overworld");
    public static final TagKey<Biome> DENSE_OVERWORLD = create("density/dense/overworld");

    public static final TagKey<Biome> PLAINS = create("plains");
    public static final TagKey<Biome> FOREST = create("forest");
    public static final TagKey<Biome> TAIGA = create("taiga");
    public static final TagKey<Biome> DESERT = create("desert");
    public static final TagKey<Biome> SAVANNA = create("savanna");
    public static final TagKey<Biome> JUNGLE = create("jungle");
    public static final TagKey<Biome> BEACH = create("beach");
    public static final TagKey<Biome> SWAMP = create("swamp");
    public static final TagKey<Biome> MOUNTAIN = create("mountain");
    public static final TagKey<Biome> CAVE = create("cave");
    public static final TagKey<Biome> SNOWY = create("snowy");
    public static final TagKey<Biome> BADLANDS = create("badlands");
    public static final TagKey<Biome> SANDY = create("sandy");
    public static final TagKey<Biome> DEAD = create("dead");

    public static final TagKey<Biome> OCEAN = create("ocean");

    public static class StructureHasTags {
        public static final TagKey<Biome> HAS_PRAIRIE_HOUSE = create("has_structure/prairie_house");
        public static final TagKey<Biome> HAS_RUGGED_FOSSIL = create("has_structure/rugged_fossil");
        public static final TagKey<Biome> HAS_ASPEN_MANOR = create("has_structure/aspen_manor");
        public static final TagKey<Biome> HAS_VILLAGE_FORGOTTEN = create("has_structure/village_forgotten");
        public static final TagKey<Biome> HAS_VILLAGE_SKYRIS = create("has_structure/village_skyris");
        public static final TagKey<Biome> HAS_VILLAGE_SALEM = create("has_structure/village_salem");
        public static final TagKey<Biome> HAS_VILLAGE_RED_ROCK = create("has_structure/village_red_rock");
        public static final TagKey<Biome> HAS_VILLAGE_PUMPKIN_PATCH = create("has_structure/village_pumpkin_patch");
    }

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, BiomesWeveGone.id(name));
    }
}
