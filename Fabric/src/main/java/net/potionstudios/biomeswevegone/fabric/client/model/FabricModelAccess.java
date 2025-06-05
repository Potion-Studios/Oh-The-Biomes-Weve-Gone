package net.potionstudios.biomeswevegone.fabric.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;

@AutoService(ModelAccess.class)
public class FabricModelAccess implements ModelAccess {
    @Override
    public BakedModel getModel(ModelResourceLocation location, ModelManager modelManager) {
        return Minecraft.getInstance().getModelManager().getModel(location.id());
    }
}
