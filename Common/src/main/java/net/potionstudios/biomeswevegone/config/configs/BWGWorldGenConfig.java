package net.potionstudios.biomeswevegone.config.configs;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.config.ConfigLoader;
import net.potionstudios.biomeswevegone.config.ConfigUtils;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.modifiers.BWGBiomeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class BWGWorldGenConfig {

    public static BWGWorldGenConfig INSTANCE = ConfigLoader.loadConfig(BWGWorldGenConfig.class, "world_generation");

    public Map<ResourceLocation, Boolean> biomes = getDefaultBiomes();
    public int region_1_weight = 8;
    public int region_2_weight = 8;
    public int region_3_weight = 8;
    public ConfigUtils.CommentValue<Boolean> vanilla_additions = ConfigUtils.CommentValue.of("Setting this to False will disable all vanilla additions, making the enabled_vanilla_additions section ignored.  (Only Available on Fabric and NeoForge)", true);
    public Map<ResourceLocation, ConfigUtils.CommentValue<Boolean>> individual_vanilla_additions = getVanillaPlacedFeatureAdditions();

    private static @NotNull Map<ResourceLocation, Boolean> getDefaultBiomes() {
        Map<ResourceLocation, Boolean> enabledBiomes = BWGBiomes.BIOME_FACTORIES.keySet().stream()
                .map(ResourceKey::location)
                .sorted(Comparator.comparing(ResourceLocation::toString))
                .collect(Collectors.toMap(loc -> loc, loc -> true, (a, b) -> a, LinkedHashMap::new));

        enabledBiomes.replace(BWGBiomes.ERODED_BOREALIS.location(), false);
        return enabledBiomes;
    }

    private static @NotNull Map<ResourceLocation, ConfigUtils.CommentValue<Boolean>> getVanillaPlacedFeatureAdditions() {
        BWGBiomeModifiers.init();
        Map<ResourceLocation, ConfigUtils.CommentValue<Boolean>> enabledFeatures = new HashMap<>();
        BWGBiomeModifiers.BIOME_MODIFIERS_FACTORIES.values().forEach(bwgBiomeModifier -> {
            BiomesWeveGone.LOGGER.info(bwgBiomeModifier.lang());
            enabledFeatures.put(bwgBiomeModifier.feature().location(), ConfigUtils.CommentValue.of(bwgBiomeModifier.lang() + Arrays.stream(bwgBiomeModifier.biomes())
                    .map(entry -> entry.location().toString())
                    .collect(Collectors.joining(", ", "", ".")), true));
        });
        return enabledFeatures;
    }

    public static void reload() {
        INSTANCE = ConfigLoader.loadConfig(BWGWorldGenConfig.class, "world_generation");
    }
}
