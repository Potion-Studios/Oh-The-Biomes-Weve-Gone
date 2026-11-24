package net.potionstudios.biomeswevegone.world.level.levelgen.biome.selector;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import terrablender.api.Region;

public class TerraBlenderBiomeSelectors {

    public static final ResourceKey<Biome>[][] OCEANS_TERRABLENDER = BiomeSelectorsUtil.create("oceans/oceans_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER}
    });

    public static final ResourceKey<Biome>[][] SLOPE_BIOMES_VARIANT_TERRABLENDER = BiomeSelectorsUtil.create("slope_biomes_variant/slope_biomes_variant_terrablender", "", new ResourceKey[][]{
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER},
            {Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID},
            {Region.DEFERRED_PLACEHOLDER, Region.DEFERRED_PLACEHOLDER, Biomes.THE_VOID, Biomes.THE_VOID, Biomes.THE_VOID}
    });
}
