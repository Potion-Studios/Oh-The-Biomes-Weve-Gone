package net.potionstudios.biomeswevegone.neoforge.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;

@AutoService(ModelAccess.class)
public final class NeoForgeModelAccess implements ModelAccess {
	@Override
	public BakedModel getModel(String location) {
		return Minecraft.getInstance().getModelManager().getStandaloneModel(BiomesWeveGone.id("block/" + location));
	}
}
