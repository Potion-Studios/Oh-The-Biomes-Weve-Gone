package net.potionstudios.biomeswevegone.neoforge.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;
import net.potionstudios.biomeswevegone.neoforge.client.BiomesWeveGoneClientNeoForge;

@AutoService(ModelAccess.class)
public class NeoForgeModelAccess implements ModelAccess {
    @Override
    public BlockStateModel getModel(String name, BlockRenderDispatcher blockRenderDispatcher) {
        return Minecraft.getInstance().getModelManager().getStandaloneModel(BiomesWeveGoneClientNeoForge.ADDITIONAL_MODELS.get(name));
    }
}
