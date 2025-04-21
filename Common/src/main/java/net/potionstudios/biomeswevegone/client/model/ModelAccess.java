package net.potionstudios.biomeswevegone.client.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

import java.util.ServiceLoader;

public interface ModelAccess {

    ModelAccess MODEL_ACCESS = load(ModelAccess.class);

    private static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        BiomesWeveGone.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    default BakedModel getModel(ModelResourceLocation location, ModelManager modelManager) {
        return modelManager.getModel(location);
    }
}
