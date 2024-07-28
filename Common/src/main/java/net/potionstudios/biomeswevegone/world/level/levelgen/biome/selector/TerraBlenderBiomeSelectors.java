package net.potionstudios.biomeswevegone.world.level.levelgen.biome.selector;

import corgitaco.corgilib.serialization.codec.Wrapped;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import terrablender.api.Region;

import java.util.List;

public class TerraBlenderBiomeSelectors {

    public static final Wrapped<List<List<ResourceKey<Biome>>>> OCEANS_TERRABLENDER = BiomeSelectorsUtil.create("oceans/oceans_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });
    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_TERRABLENDER = BiomeSelectorsUtil.create("middle_biomes/middle_biomes_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });
    public static final Wrapped<List<List<ResourceKey<Biome>>>> MIDDLE_BIOMES_VARIANT_TERRABLENDER = BiomeSelectorsUtil.create("middle_biomes_variant/middle_biomes_variant_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Biomes.THE_VOID, Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });
    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_TERRABLENDER = BiomeSelectorsUtil.create("plateau_biomes/plateau_biomes_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });
    public static final Wrapped<List<List<ResourceKey<Biome>>>> PLATEAU_BIOMES_VARIANT_TERRABLENDER = BiomeSelectorsUtil.create("plateau_biomes_variant/plateau_biomes_variant_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_TERRABLENDER = BiomeSelectorsUtil.create("peak_biomes/peak_biomes_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SHATTERED_BIOMES_TERRABLENDER = BiomeSelectorsUtil.create("shattered_biomes/shattered_biomes_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> BEACH_BIOMES_TERRABLENDER = BiomeSelectorsUtil.create("beach_biomes/beach_biomes_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> PEAK_BIOMES_VARIANT_TERRABLENDER = BiomeSelectorsUtil.create("peak_biomes_variant/peak_biomes_variant_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_TERRABLENDER = BiomeSelectorsUtil.create("slope_biomes/slope_biomes_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> SLOPE_BIOMES_VARIANT_TERRABLENDER = BiomeSelectorsUtil.create("slope_biomes_variant/slope_biomes_variant_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });
}
