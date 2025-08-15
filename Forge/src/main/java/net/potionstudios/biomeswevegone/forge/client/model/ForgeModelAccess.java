package net.potionstudios.biomeswevegone.forge.client.model;

import com.google.auto.service.AutoService;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;
import net.potionstudios.biomeswevegone.world.entity.decoration.Wreath;

@AutoService(ModelAccess.class)
public class ForgeModelAccess implements ModelAccess {
    @Override
    public BlockStateModel getModel(String name, BlockRenderDispatcher blockRenderDispatcher) {
        return blockRenderDispatcher.getBlockModel(WreathBlockState.STATE.any().setValue(WreathBlockState.TYPE, Wreath.Type.byName(name.replace("_wreath", ""))));
    }
}
