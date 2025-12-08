package net.potionstudios.biomeswevegone.world.level.levelgen.biome.selector;

import corgitaco.corgilib.serialization.codec.Wrapped;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import terrablender.api.Region;

import java.util.List;

public class BWGBiomeSelectors {

    public static final Wrapped<List<List<ResourceKey<Biome>>>> OCEANS_BWG = BiomeSelectorsUtil.create("oceans/oceans_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.DEAD_SEA},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.DEAD_SEA}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> OCEANS_2_BWG = BiomeSelectorsUtil.create("oceans/oceans_2_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.LUSH_STACKS, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.LUSH_STACKS, Region.DEFERRED_PLACEHOLDER}
    });

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
//BIOME REGION #1

    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_BWG = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.CRIMSON_TUNDRA, BWGBiomes.CRIMSON_TUNDRA, BWGBiomes.CRIMSON_TUNDRA, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
            {BWGBiomes.ASPEN_BOREAL, BWGBiomes.ASPEN_BOREAL, BWGBiomes.ASPEN_BOREAL, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST},
            {BWGBiomes.PRAIRIE, BWGBiomes.PRAIRIE, BWGBiomes.PRAIRIE, BWGBiomes.TEMPERATE_GROVE, BWGBiomes.TEMPERATE_GROVE},
            {BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.EBONY_WOODS, BWGBiomes.EBONY_WOODS},
            {BWGBiomes.MOJAVE_DESERT, BWGBiomes.MOJAVE_DESERT, BWGBiomes.MOJAVE_DESERT, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_BWG = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.CONIFEROUS_FOREST},
            {BWGBiomes.ALLIUM_SHRUBLAND, BWGBiomes.ALLIUM_SHRUBLAND, BWGBiomes.ALLIUM_SHRUBLAND, BWGBiomes.ORCHARD, BWGBiomes.ORCHARD},
            {BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
            {BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_BWG = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
            {BWGBiomes.ASPEN_BOREAL, BWGBiomes.ASPEN_BOREAL, BWGBiomes.ASPEN_BOREAL, BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW},
            {BWGBiomes.ROSE_FIELDS, BWGBiomes.ROSE_FIELDS, BWGBiomes.ROSE_FIELDS, BWGBiomes.DACITE_RIDGES, BWGBiomes.DACITE_RIDGES},
            {BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.EBONY_WOODS, BWGBiomes.EBONY_WOODS, BWGBiomes.EBONY_WOODS},
            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_BWG = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.SKYRIS_VALE, BWGBiomes.SKYRIS_VALE, BWGBiomes.SKYRIS_VALE, BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.WEEPING_WITCH_FOREST},
            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS},
            {BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_BWG = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
            {BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_BWG = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
            {BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_BWG = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_BWG = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_BWG = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.DACITE_SHORE, BWGBiomes.DACITE_SHORE},
            {BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA},
            {BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
//BIOME REGION #2

    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_2_BWG = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_2_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.CRIMSON_TUNDRA, BWGBiomes.CRIMSON_TUNDRA, BWGBiomes.CRIMSON_TUNDRA, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
            {BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST},
            {BWGBiomes.PRAIRIE, BWGBiomes.PRAIRIE, BWGBiomes.PRAIRIE, BWGBiomes.TEMPERATE_GROVE, BWGBiomes.TEMPERATE_GROVE},
            {BWGBiomes.BAOBAB_SAVANNA, BWGBiomes.BAOBAB_SAVANNA, BWGBiomes.BAOBAB_SAVANNA, BWGBiomes.TROPICAL_RAINFOREST, BWGBiomes.TROPICAL_RAINFOREST},
            {BWGBiomes.ATACAMA_OUTBACK, BWGBiomes.ATACAMA_OUTBACK, BWGBiomes.ATACAMA_OUTBACK, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_2_BWG = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_2_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.CIKA_WOODS, BWGBiomes.CIKA_WOODS, BWGBiomes.CIKA_WOODS, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.CONIFEROUS_FOREST},
            {BWGBiomes.PUMPKIN_VALLEY, BWGBiomes.PUMPKIN_VALLEY, BWGBiomes.PUMPKIN_VALLEY, BWGBiomes.FORGOTTEN_FOREST, BWGBiomes.FORGOTTEN_FOREST},
            {BWGBiomes.IRONWOOD_GOUR, BWGBiomes.IRONWOOD_GOUR, BWGBiomes.IRONWOOD_GOUR, BWGBiomes.JACARANDA_JUNGLE, BWGBiomes.JACARANDA_JUNGLE},
            {BWGBiomes.RUGGED_BADLANDS, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.RUGGED_BADLANDS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_2_BWG = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_2_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
            {BWGBiomes.MAPLE_TAIGA, BWGBiomes.MAPLE_TAIGA, BWGBiomes.MAPLE_TAIGA, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CANADIAN_SHIELD},
            {BWGBiomes.ROSE_FIELDS, BWGBiomes.ROSE_FIELDS, BWGBiomes.REDWOOD_THICKET, BWGBiomes.REDWOOD_THICKET, BWGBiomes.REDWOOD_THICKET},
            {BWGBiomes.BAOBAB_SAVANNA, BWGBiomes.BAOBAB_SAVANNA, BWGBiomes.TROPICAL_RAINFOREST, BWGBiomes.TROPICAL_RAINFOREST, BWGBiomes.TROPICAL_RAINFOREST},
            {BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_2_BWG = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_2_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.SKYRIS_VALE, BWGBiomes.SKYRIS_VALE, BWGBiomes.SKYRIS_VALE, BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.WEEPING_WITCH_FOREST},
            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS},
            {BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_2_BWG = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_2_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_2_BWG = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_2_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_2_BWG = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_2_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_2_BWG = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_2_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_2_BWG = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_2_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.DACITE_SHORE, BWGBiomes.DACITE_SHORE},
            {BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA},
            {BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
//BIOME REGION #3
    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_3_BWG = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_3_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {BWGBiomes.PINE_BARRENS, BWGBiomes.PINE_BARRENS, BWGBiomes.PINE_BARRENS, BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.WEEPING_WITCH_FOREST},
            {BWGBiomes.TUSCANY_PLAINS, BWGBiomes.TUSCANY_PLAINS, BWGBiomes.TUSCANY_PLAINS, BWGBiomes.FORGOTTEN_FOREST, BWGBiomes.FORGOTTEN_FOREST},
            {BWGBiomes.AMARANTH_GRASSLAND, BWGBiomes.AMARANTH_GRASSLAND, BWGBiomes.AMARANTH_GRASSLAND, BWGBiomes.JACARANDA_JUNGLE, BWGBiomes.JACARANDA_JUNGLE},
            {BWGBiomes.RUGGED_BADLANDS, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_3_BWG = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_3_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.SHATTERED_GLACIER, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.CIKA_WOODS, BWGBiomes.CIKA_WOODS, BWGBiomes.CIKA_WOODS, BWGBiomes.MAPLE_TAIGA, BWGBiomes.MAPLE_TAIGA},
            {BWGBiomes.PUMPKIN_VALLEY, BWGBiomes.PUMPKIN_VALLEY, BWGBiomes.PUMPKIN_VALLEY, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS},
            {BWGBiomes.IRONWOOD_GOUR, BWGBiomes.IRONWOOD_GOUR, BWGBiomes.IRONWOOD_GOUR, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.MOJAVE_DESERT, BWGBiomes.MOJAVE_DESERT}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_3_BWG = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_3_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
            {BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CANADIAN_SHIELD},
            {BWGBiomes.ASPHODEL_MEADOW, BWGBiomes.ASPHODEL_MEADOW, BWGBiomes.ASPHODEL_MEADOW, BWGBiomes.REDWOOD_THICKET, BWGBiomes.REDWOOD_THICKET},
            {BWGBiomes.BAOBAB_SAVANNA, BWGBiomes.BAOBAB_SAVANNA, BWGBiomes.TROPICAL_RAINFOREST, BWGBiomes.TROPICAL_RAINFOREST, BWGBiomes.TROPICAL_RAINFOREST},
            {BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_3_BWG = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_3_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.SKYRIS_VALE, BWGBiomes.SKYRIS_VALE, BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.WEEPING_WITCH_FOREST, BWGBiomes.WEEPING_WITCH_FOREST},
            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.FORGOTTEN_FOREST, BWGBiomes.FORGOTTEN_FOREST, BWGBiomes.FORGOTTEN_FOREST},
            {BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ENCHANTED_TANGLE, BWGBiomes.ENCHANTED_TANGLE, BWGBiomes.ENCHANTED_TANGLE},
            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_3_BWG = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_3_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_3_BWG = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_3_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_3_BWG = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_3_biomes_weve_gone", "", new ResourceKey[][]{
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
            {BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FRAGMENT_JUNGLE, BWGBiomes.FRAGMENT_JUNGLE},
            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_3_BWG = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_3_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_3_BWG = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_3_biomes_weve_gone", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.DACITE_SHORE, BWGBiomes.DACITE_SHORE},
            {BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA},
            {BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

//    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
////BIOME REGION #4
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_4_BWG = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.SHATTERED_GLACIER, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.CONIFEROUS_FOREST},
//            {BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.MAPLE_TAIGA, BWGBiomes.ZELKOVA_FOREST, BWGBiomes.ZELKOVA_FOREST},
//            {BWGBiomes.PRAIRIE, BWGBiomes.PRAIRIE, BWGBiomes.ALLIUM_SHRUBLAND, BWGBiomes.ASPEN_BOREAL, BWGBiomes.WEEPING_WITCH_FOREST},
//            {BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.IRONWOOD_GOUR, BWGBiomes.AMARANTH_GRASSLAND, BWGBiomes.ENCHANTED_TANGLE, BWGBiomes.FRAGMENT_JUNGLE},
//            {BWGBiomes.MOJAVE_DESERT, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.ATACAMA_OUTBACK, BWGBiomes.RED_ROCK_VALLEY}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_4_BWG = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FORGOTTEN_FOREST, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FORGOTTEN_FOREST, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_4_BWG = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.SHATTERED_GLACIER, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.CONIFEROUS_FOREST},
//            {BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.ROSE_FIELDS, BWGBiomes.WEEPING_WITCH_FOREST},
//            {BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.REDWOOD_THICKET, BWGBiomes.EBONY_WOODS, BWGBiomes.TROPICAL_RAINFOREST},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_4_BWG = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_4_BWG = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_4_BWG = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_4_BWG = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.REDWOOD_THICKET, BWGBiomes.REDWOOD_THICKET, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_4_BWG = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_4_BWG = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_4_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, BWGBiomes.DACITE_SHORE, BWGBiomes.DACITE_SHORE},
//            {BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA},
//            {BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
////BIOME REGION #5
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_5_BWG = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.SHATTERED_GLACIER, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.CONIFEROUS_FOREST},
//            {BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.MAPLE_TAIGA, BWGBiomes.ZELKOVA_FOREST, BWGBiomes.ZELKOVA_FOREST},
//            {BWGBiomes.PRAIRIE, BWGBiomes.PRAIRIE, BWGBiomes.ALLIUM_SHRUBLAND, BWGBiomes.ASPEN_BOREAL, BWGBiomes.WEEPING_WITCH_FOREST},
//            {BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.IRONWOOD_GOUR, BWGBiomes.AMARANTH_GRASSLAND, BWGBiomes.ENCHANTED_TANGLE, BWGBiomes.FRAGMENT_JUNGLE},
//            {BWGBiomes.MOJAVE_DESERT, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.ATACAMA_OUTBACK, BWGBiomes.RED_ROCK_VALLEY}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_5_BWG = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FORGOTTEN_FOREST, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FORGOTTEN_FOREST, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_5_BWG = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.SHATTERED_GLACIER, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.CONIFEROUS_FOREST},
//            {BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.ROSE_FIELDS, BWGBiomes.WEEPING_WITCH_FOREST},
//            {BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.REDWOOD_THICKET, BWGBiomes.EBONY_WOODS, BWGBiomes.TROPICAL_RAINFOREST},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_5_BWG = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_5_BWG = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_5_BWG = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_5_BWG = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.REDWOOD_THICKET, BWGBiomes.REDWOOD_THICKET, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_5_BWG = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_5_BWG = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_5_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, BWGBiomes.DACITE_SHORE, BWGBiomes.DACITE_SHORE},
//            {BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA},
//            {BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
////BIOME REGION #6
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_6_BWG = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.SHATTERED_GLACIER, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.CONIFEROUS_FOREST},
//            {BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.MAPLE_TAIGA, BWGBiomes.ZELKOVA_FOREST, BWGBiomes.ZELKOVA_FOREST},
//            {BWGBiomes.PRAIRIE, BWGBiomes.PRAIRIE, BWGBiomes.ALLIUM_SHRUBLAND, BWGBiomes.ASPEN_BOREAL, BWGBiomes.WEEPING_WITCH_FOREST},
//            {BWGBiomes.FIRECRACKER_CHAPARRAL, BWGBiomes.IRONWOOD_GOUR, BWGBiomes.AMARANTH_GRASSLAND, BWGBiomes.ENCHANTED_TANGLE, BWGBiomes.FRAGMENT_JUNGLE},
//            {BWGBiomes.MOJAVE_DESERT, BWGBiomes.RUGGED_BADLANDS, BWGBiomes.WINDSWEPT_DESERT, BWGBiomes.ATACAMA_OUTBACK, BWGBiomes.RED_ROCK_VALLEY}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_6_BWG = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FORGOTTEN_FOREST, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.FORGOTTEN_FOREST, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS, BWGBiomes.CRAG_GARDENS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_6_BWG = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.SHATTERED_GLACIER, BWGBiomes.FROSTED_CONIFEROUS_FOREST, BWGBiomes.FROSTED_TAIGA, BWGBiomes.CONIFEROUS_FOREST},
//            {BWGBiomes.COCONINO_MEADOW, BWGBiomes.COCONINO_MEADOW, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST, BWGBiomes.BLACK_FOREST},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, BWGBiomes.ROSE_FIELDS, BWGBiomes.WEEPING_WITCH_FOREST},
//            {BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.ARAUCARIA_SAVANNA, BWGBiomes.REDWOOD_THICKET, BWGBiomes.EBONY_WOODS, BWGBiomes.TROPICAL_RAINFOREST},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_VALLEY, BWGBiomes.RED_ROCK_VALLEY}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_6_BWG = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.OVERGROWTH_WOODLANDS, BWGBiomes.OVERGROWTH_WOODLANDS, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {BWGBiomes.SAKURA_GROVE, BWGBiomes.SAKURA_GROVE, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.CRAG_GARDENS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_6_BWG = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_6_BWG = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS},
//            {Region.DEFERRED_PLACEHOLDER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_6_BWG = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.ERODED_BOREALIS, BWGBiomes.FROSTED_TAIGA, BWGBiomes.FROSTED_TAIGA},
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.ERODED_BOREALIS, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
//            {BWGBiomes.SHATTERED_GLACIER, BWGBiomes.HOWLING_PEAKS, BWGBiomes.CANADIAN_SHIELD, BWGBiomes.CONIFEROUS_FOREST, BWGBiomes.FROSTED_CONIFEROUS_FOREST},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.REDWOOD_THICKET, BWGBiomes.REDWOOD_THICKET, BWGBiomes.CRAG_GARDENS},
//            {BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.SIERRA_BADLANDS, BWGBiomes.RED_ROCK_PEAKS, BWGBiomes.RED_ROCK_PEAKS}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_6_BWG = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });
//
//    public static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_6_BWG = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_6_biomes_weve_gone", "", new ResourceKey[][]{
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
//            {Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, BWGBiomes.DACITE_SHORE, BWGBiomes.DACITE_SHORE},
//            {BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA, BWGBiomes.BASALT_BARRERA},
//            {BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH, BWGBiomes.RAINBOW_BEACH},
//            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
//    });

}
