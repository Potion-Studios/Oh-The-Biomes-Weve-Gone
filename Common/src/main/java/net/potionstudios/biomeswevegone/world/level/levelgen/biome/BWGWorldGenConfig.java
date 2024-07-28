package net.potionstudios.biomeswevegone.world.level.levelgen.biome;


import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.serialization.codec.CommentedCodec;
import corgitaco.corgilib.serialization.jankson.JanksonJsonOps;
import corgitaco.corgilib.shadow.blue.endless.jankson.Jankson;
import corgitaco.corgilib.shadow.blue.endless.jankson.JsonElement;
import corgitaco.corgilib.shadow.blue.endless.jankson.JsonGrammar;
import corgitaco.corgilib.shadow.blue.endless.jankson.JsonObject;
import corgitaco.corgilib.shadow.blue.endless.jankson.api.SyntaxError;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.platform.ModPlatform;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

public record BWGWorldGenConfig(Map<ResourceKey<Biome>, Boolean> enabledBiomes, int regionWeight) {

    public static final Path PATH = ModPlatform.INSTANCE.configPath().resolve("world_generation.json5");

    @NotNull
    public static Supplier<BWGWorldGenConfig> INSTANCE = Suppliers.memoize(BWGWorldGenConfig::getOrCreateConfigFromDisk);

    public static final Codec<BWGWorldGenConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    CommentedCodec.of(Codec.unboundedMap(ResourceKey.codec(Registries.BIOME), Codec.BOOL), "enabled_biomes", "Which biomes are enabled, if disabled the biome will default to its vanilla counterpart for the given region").forGetter(BWGWorldGenConfig::enabledBiomes),
                    CommentedCodec.of(Codec.INT, "region_weight", "How much each BWG region weighs. This weight applies to all 3 BWG Regions").forGetter(BWGWorldGenConfig::regionWeight)
            ).apply(instance, BWGWorldGenConfig::new)
    );

    public static BWGWorldGenConfig createDefault() {
        Object2BooleanMap<ResourceKey<Biome>> enabledBiomes = new Object2BooleanOpenHashMap<>();
        for (ResourceKey<Biome> biomeResourceKey : BWGBiomes.BIOME_FACTORIES.keySet()) {
            enabledBiomes.put(biomeResourceKey, true);
        }

        enabledBiomes.put(BWGBiomes.ERODED_BOREALIS, false);

        return new BWGWorldGenConfig(enabledBiomes, 8);
    }

    public static BWGWorldGenConfig getOrCreateConfigFromDisk() {
        BWGWorldGenConfig defaultWorldGenConfig = createDefault();

        if (!PATH.toFile().exists()) {
            createDefaultFile(defaultWorldGenConfig);
            return defaultWorldGenConfig;
        } else {

            Jankson build = new Jankson.Builder().build();
            try {
                String configFile = Files.readString(PATH).stripTrailing().trim().strip().stripLeading();
                JsonObject load = build.load(configFile);
                Pair<BWGWorldGenConfig, JsonElement> configResult = CODEC.decode(JanksonJsonOps.INSTANCE, load).result().orElseThrow();
                BWGWorldGenConfig config = configResult.getFirst();


                boolean rewrite = false;
                for (ResourceKey<Biome> biomeResourceKey : defaultWorldGenConfig.enabledBiomes.keySet()) {
                    if (!config.enabledBiomes.containsKey(biomeResourceKey)) {
                        rewrite = true;
                        break;
                    }
                }

                if (rewrite) {
                    Map<ResourceKey<Biome>, Boolean> temporary = new Reference2ObjectOpenHashMap<>();

                    temporary.putAll(defaultWorldGenConfig.enabledBiomes);
                    temporary.putAll(config.enabledBiomes);

                    BWGWorldGenConfig toCreate = new BWGWorldGenConfig(temporary, config.regionWeight);

                    createDefaultFile(toCreate);

                    return toCreate;
                } else {
                    return config;
                }

            } catch (IOException | SyntaxError e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void createDefaultFile(BWGWorldGenConfig defaultWorldGenConfig) {
        JsonElement jsonElement = CODEC.encodeStart(JanksonJsonOps.INSTANCE, defaultWorldGenConfig).result().orElseThrow();
        String json = jsonElement.toJson(JsonGrammar.JSON5);
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
