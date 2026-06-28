package net.potionstudios.biomeswevegone.forge.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;
import net.potionstudios.biomeswevegone.world.entity.decoration.Wreath;

@AutoService(ModelAccess.class)
public class ForgeModelAccess implements ModelAccess {
    @Override
    public BlockStateModel getModel(String name, BlockModelResolver blockRenderDispatcher) {
        return Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(WreathBlockState.STATE.any().setValue(WreathBlockState.TYPE, Wreath.Type.byName(name.replace("_wreath", ""))));
    }
}
