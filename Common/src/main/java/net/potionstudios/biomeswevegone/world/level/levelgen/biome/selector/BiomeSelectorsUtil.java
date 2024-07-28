package net.potionstudios.biomeswevegone.world.level.levelgen.biome.selector;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import corgitaco.corgilib.serialization.codec.Wrapped;
import corgitaco.corgilib.serialization.jankson.JanksonUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.util.BWGUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BiomeSelectorsUtil {
    public static final Map<String, Pair<Map<String, String>, Wrapped<List<List<ResourceKey<Biome>>>>>> BIOME_LAYOUTS = new HashMap<>();

    protected static Wrapped<List<List<ResourceKey<Biome>>>> create(String id, String header, ResourceKey<Biome>[][] biomeKeys) {
        return create(id, biomeKeys, ImmutableMap.of("", JanksonUtil.HEADER_OPEN + "\n" + header + "*/"));
    }

    protected static Wrapped<List<List<ResourceKey<Biome>>>> create(String id, ResourceKey<Biome>[][] biomeKeys) {
        return create(id, biomeKeys, new HashMap<>());
    }

    protected static Wrapped<List<List<ResourceKey<Biome>>>> create(String id, ResourceKey<Biome>[][] biomeKeys, Map<String, String> comments) {
        Wrapped<List<List<ResourceKey<Biome>>>> result = new Wrapped<>(Optional.of(id), BWGUtil.convert2DArray(biomeKeys));
        BIOME_LAYOUTS.put(id, Pair.of(comments, result));
        return result;
    }
}
