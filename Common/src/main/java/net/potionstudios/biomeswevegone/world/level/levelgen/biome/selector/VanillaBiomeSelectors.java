package net.potionstudios.biomeswevegone.world.level.levelgen.biome.selector;

import corgitaco.corgilib.serialization.codec.Wrapped;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;

public class VanillaBiomeSelectors {

    private static final Wrapped<List<List<ResourceKey<Biome>>>> OCEANS_VANILLA = BiomeSelectorsUtil.create("oceans/oceans_vanilla", "", new ResourceKey[][]{
            {Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN},
            {Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN}
    });
    private static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VANILLA = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_vanilla", "", new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.TAIGA},
            {Biomes.PLAINS, Biomes.PLAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.FLOWER_FOREST, Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST},
            {Biomes.SAVANNA, Biomes.SAVANNA, Biomes.FOREST, Biomes.JUNGLE, Biomes.JUNGLE},
            {Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT}
    });
    private static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_VANILLA = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_vanilla", "", new ResourceKey[][]{
            {Biomes.ICE_SPIKES, Biomes.THE_VOID, Biomes.SNOWY_TAIGA, Biomes.THE_VOID, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.SUNFLOWER_PLAINS, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.PLAINS, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });
    private static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VANILLA = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_vanilla", "", new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA},
            {Biomes.MEADOW, Biomes.MEADOW, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.DARK_FOREST},
            {Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA_PLATEAU, Biomes.FOREST, Biomes.FOREST, Biomes.JUNGLE},
            {Biomes.BADLANDS, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
    });
    private static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_VANILLA = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_vanilla", "", new ResourceKey[][]{
            {Biomes.ICE_SPIKES, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Biomes.CHERRY_GROVE, Biomes.THE_VOID, Biomes.MEADOW, Biomes.MEADOW, Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });

    private static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VANILLA = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_vanilla", "", new ResourceKey[][]{
            {Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS},
            {Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS},
            {Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS, Biomes.JAGGED_PEAKS},
            {Biomes.STONY_PEAKS, Biomes.STONY_PEAKS, Biomes.STONY_PEAKS, Biomes.STONY_PEAKS, Biomes.STONY_PEAKS},
            {Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
    });

    private static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_VANILLA = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_vanilla", "", new ResourceKey[][]{
            {Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });

    private static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_VANILLA = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_vanilla", "", new ResourceKey[][]{
            {Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH},
            {Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, Biomes.BEACH},
            {Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, Biomes.BEACH},
            {Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, Biomes.BEACH, Biomes.BEACH},
            {Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT}
    });

    private static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_VANILLA = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_vanilla", "", new ResourceKey[][]{
            {Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS},
            {Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS},
            {Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS, Biomes.FROZEN_PEAKS},
            {Biomes.STONY_PEAKS, Biomes.STONY_PEAKS, Biomes.STONY_PEAKS, Biomes.STONY_PEAKS, Biomes.STONY_PEAKS},
            {Biomes.BADLANDS, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
    });

    private static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_VANILLA = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_vanilla", "", new ResourceKey[][]{
            {Biomes.SNOWY_SLOPES, Biomes.SNOWY_SLOPES, Biomes.GROVE, Biomes.GROVE, Biomes.GROVE},
            {Biomes.SNOWY_SLOPES, Biomes.SNOWY_SLOPES, Biomes.GROVE, Biomes.GROVE, Biomes.GROVE},
            {Biomes.SNOWY_SLOPES, Biomes.SNOWY_SLOPES, Biomes.GROVE, Biomes.GROVE, Biomes.GROVE},
            {Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA_PLATEAU, Biomes.FOREST, Biomes.FOREST, Biomes.JUNGLE},
            {Biomes.BADLANDS, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
    });

    private static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_VARIANT_VANILLA = BiomeSelectorsUtil.create("slope_biomes_variant/slope_biomes_variant_vanilla", "", new ResourceKey[][]{
            {Biomes.SNOWY_SLOPES, Biomes.SNOWY_SLOPES, Biomes.GROVE, Biomes.GROVE, Biomes.GROVE},
            {Biomes.SNOWY_SLOPES, Biomes.SNOWY_SLOPES, Biomes.GROVE, Biomes.GROVE, Biomes.GROVE},
            {Biomes.SNOWY_SLOPES, Biomes.SNOWY_SLOPES, Biomes.GROVE, Biomes.GROVE, Biomes.GROVE},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });
}
