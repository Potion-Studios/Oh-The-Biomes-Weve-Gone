package net.potionstudios.biomeswevegone.world.level.levelgen.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.potionstudios.biomeswevegone.util.BWGUtil;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class BWGRegionUtils {
    @SafeVarargs
    public static void dumpArrays(Consumer<ResourceKey<Biome>> biomeConsumer, ResourceKey<Biome>[][]... resourceKeys) {
        for (ResourceKey<Biome>[][] resourceKey : resourceKeys) {
            for (ResourceKey<Biome>[] keys : resourceKey) {
                for (ResourceKey<Biome> key : keys) {
                    biomeConsumer.accept(key);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static ResourceKey<Biome>[][] filter(String configKey, ResourceLocation regionName, int configIDX, ResourceKey<Biome>[][] biomeArray, Predicate<ResourceKey<Biome>> filter, boolean throwsException) {
        return Arrays.stream(biomeArray).map(resourceKeys -> Arrays.stream(resourceKeys).map(key -> !filter.test(key) ? null : key).peek(biomeResourceKey -> {
            if (biomeResourceKey == null && throwsException) {
                String error = String.format("\"%s\" is not an allowed entry, specify a valid biome key!\nBWG OverworldRegion: \"%s\" failed in biome array: \"%s\" in region %s.\nCurrent value:\n%s", Biomes.THE_VOID.location(), regionName.toString(), configKey, configIDX, BWGUtil.print2DResourceKeyArray(biomeArray));
                throw new IllegalArgumentException(error);
            }
        }).toList().toArray(ResourceKey[]::new)).toArray(ResourceKey[][]::new);
    }
}