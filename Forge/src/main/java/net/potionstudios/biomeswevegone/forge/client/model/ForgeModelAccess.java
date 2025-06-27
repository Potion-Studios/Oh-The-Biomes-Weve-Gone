package net.potionstudios.biomeswevegone.forge.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;

@AutoService(ModelAccess.class)
public final class ForgeModelAccess implements ModelAccess {
	@Override
	public BakedModel getModel(ResourceLocation location) {
		return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(location, ""));
	}
}
