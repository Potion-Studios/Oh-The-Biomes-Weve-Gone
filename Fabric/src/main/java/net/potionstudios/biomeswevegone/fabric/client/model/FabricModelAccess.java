package net.potionstudios.biomeswevegone.fabric.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;
import net.potionstudios.biomeswevegone.fabric.client.BiomesWeveGoneClientFabric;

@AutoService(ModelAccess.class)
public class FabricModelAccess implements ModelAccess {
    @Override
    public BlockStateModel getModel(String name, BlockModelResolver blockRenderDispatcher) {
        return Minecraft.getInstance().getModelManager().getModel(BiomesWeveGoneClientFabric.EXTRA_MODELS.get(name));
    }
}
