package net.potionstudios.biomeswevegone.platform;

import net.potionstudios.biomeswevegone.BiomesWeveGone;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface ModPlatform {
    ModPlatform INSTANCE = load(ModPlatform.class);

    Path configPath();

    static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        BiomesWeveGone.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
