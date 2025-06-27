package net.potionstudios.biomeswevegone.forge.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;

@AutoService(ModelAccess.class)
public final class ForgeModelAccess implements ModelAccess {
	@Override
	public BakedModel getModel(String location) {
		return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(BiomesWeveGone.id(location), ""));
	}
}
