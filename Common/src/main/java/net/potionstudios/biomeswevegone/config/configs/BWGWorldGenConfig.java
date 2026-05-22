package net.potionstudios.biomeswevegone.config.configs;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.config.ConfigLoader;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGOverworldTreePlacedFeatures;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGVanillaPlacedFeatures;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BWGWorldGenConfig {

    public static BWGWorldGenConfig INSTANCE = ConfigLoader.loadConfig(BWGWorldGenConfig.class, "world_generation");

    public Map<String, Boolean> enabledBiomes = getDefaultBiomes();
    public int region_1_weight = 8;
    public int region_2_weight = 8;
    public int region_3_weight = 8;
    public boolean vanilla_additions = true;
    public Map<String, Boolean> enabled_vanilla_additions = getVanillaPlacedFeatureAdditions();

    public BWGWorldGenConfig() {
    }

    private static @NotNull Map<String, Boolean> getDefaultBiomes() {
        Map<String, Boolean> enabledBiomes = new HashMap<>();
        for (ResourceKey<Biome> biomeResourceKey : BWGBiomes.BIOME_FACTORIES.keySet()) {
            enabledBiomes.put(biomeResourceKey.location().toString(), true);
        }

        enabledBiomes.replace(BWGBiomes.ERODED_BOREALIS.location().toString(), false);
        return enabledBiomes;
    }

    private static @NotNull Map<String, Boolean> getVanillaPlacedFeatureAdditions() {
        Map<String, Boolean> enabledFeatures = new HashMap<>();
        enabledFeatures.put(BWGVanillaPlacedFeatures.FLOWER_DEFAULT.location().toString(), true);
        enabledFeatures.put(BWGVanillaPlacedFeatures.FLOWER_PLAINS.location().toString(), true);
        enabledFeatures.put(BWGVanillaPlacedFeatures.FOREST_FLOWERS.location().toString(), true);
        enabledFeatures.put(BWGVanillaPlacedFeatures.FLOWER_WARM.location().toString(), true);
        enabledFeatures.put(BWGOverworldTreePlacedFeatures.PALM_TREES.location().toString(), true);

        return enabledFeatures;
    }

    public static void reload() {
        INSTANCE = ConfigLoader.loadConfig(BWGWorldGenConfig.class, "world_generation");
    }
}
