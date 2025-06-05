package net.potionstudios.biomeswevegone.client.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;

import java.util.ServiceLoader;

public interface ModelAccess {

    ModelAccess MODEL_ACCESS = load();

    private static ModelAccess load() {
        return ServiceLoader.load(ModelAccess.class)
                .findFirst()
                .orElseGet(() -> new ModelAccess() {});
    }

    default BakedModel getModel(ModelResourceLocation location, ModelManager modelManager) {
        return modelManager.getModel(location);
    }
}
