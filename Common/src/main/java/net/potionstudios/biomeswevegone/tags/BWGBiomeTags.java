package net.potionstudios.biomeswevegone.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

/**
 * Holds Custom Biome Tags for Biomes We've Gone
 * @see BiomeTags
 * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags
 * @see net.minecraftforge.common.Tags.Biomes
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

    /** Correlates to
     * @see BiomeTags#IS_OVERWORLD
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#IN_OVERWORLD,
     **/
    public static final TagKey<Biome> OVERWORLD = create("overworld");

    /** Correlates to
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#CLIMATE_HOT
     * @see net.minecraftforge.common.Tags.Biomes#IS_HOT_OVERWORLD
     **/
    public static final TagKey<Biome> HOT = create("climate/hot");
    /** Correlates to
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#CLIMATE_TEMPERATE*/
    public static final TagKey<Biome> TEMPERATE = create("climate/temperate");
    /** Correlates to
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#CLIMATE_COLD
     * @see net.minecraftforge.common.Tags.Biomes#IS_COLD_OVERWORLD
     **/
    public static final TagKey<Biome> COLD = create("climate/cold");
    /** Correlates to
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#CLIMATE_WET
     * @see net.minecraftforge.common.Tags.Biomes#IS_WET_OVERWORLD
     */
    public static final TagKey<Biome> WET = create("climate/wet");
    /** Correlates to
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#CLIMATE_DRY
     * @see net.minecraftforge.common.Tags.Biomes#IS_DRY_OVERWORLD
     */
    public static final TagKey<Biome> DRY = create("climate/dry");

    /** Correlates to
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#VEGETATION_SPARSE
     * @see net.minecraftforge.common.Tags.Biomes#IS_SPARSE_OVERWORLD
     **/
    public static final TagKey<Biome> SPARSE = create("density/sparse");
    /** Correlates to
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#VEGETATION_DENSE
     * @see net.minecraftforge.common.Tags.Biomes#IS_DENSE_OVERWORLD
     */
    public static final TagKey<Biome> DENSE = create("density/dense");

    public static final TagKey<Biome> PLAINS = create("plains");
    public static final TagKey<Biome> FOREST = create("forest");
    public static final TagKey<Biome> TAIGA = create("taiga");
    public static final TagKey<Biome> DESERT = create("desert");
    public static final TagKey<Biome> SAVANNA = create("savanna");
    public static final TagKey<Biome> JUNGLE = create("jungle");
    public static final TagKey<Biome> BEACH = create("beach");
    public static final TagKey<Biome> SWAMP = create("swamp");
    public static final TagKey<Biome> MOUNTAIN = create("mountain");
    public static final TagKey<Biome> SNOWY = create("snowy");
    public static final TagKey<Biome> BADLANDS = create("badlands");
    public static final TagKey<Biome> SANDY = create("sandy");
    public static final TagKey<Biome> FLORAL = create("floral");
    public static final TagKey<Biome> CONIFEROUS = create("coniferous");
    public static final TagKey<Biome> DEAD = create("dead");
    public static final TagKey<Biome> WASTELAND = create("wasteland");

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
