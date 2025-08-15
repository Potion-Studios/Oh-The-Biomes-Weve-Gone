package net.potionstudios.biomeswevegone.client.model;

import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;

import java.util.ServiceLoader;

public interface ModelAccess {
    ModelAccess MODEL_ACCESS = load();

    private static ModelAccess load() {
        return ServiceLoader.load(ModelAccess.class)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service ModelAccess"));
    }

    BlockStateModel getModel(String name, BlockRenderDispatcher blockRenderDispatcher);
}
