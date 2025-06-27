package net.potionstudios.biomeswevegone.client.model;

import net.minecraft.client.resources.model.BakedModel;

import java.util.ServiceLoader;

public interface ModelAccess {
	ModelAccess MODEL_ACCESS = load();

	private static ModelAccess load() {
		return ServiceLoader.load(ModelAccess.class)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service ModelAccess"));
	}

	BakedModel getModel(String location);
}
