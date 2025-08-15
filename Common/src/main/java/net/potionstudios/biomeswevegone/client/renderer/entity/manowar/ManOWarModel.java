package net.potionstudios.biomeswevegone.client.renderer.entity.manowar;

import net.minecraft.resources.ResourceLocation;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.manowar.ManOWar;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

/**
 * ManOWar Model
 * @see GeoModel
 * @author YaBoiChips
 */
class ManOWarModel<T extends ManOWar> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return BiomesWeveGone.id("man_o_war");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return BiomesWeveGone.id("textures/entity/manowar/" + renderState.getGeckolibData(ManOWarRenderer.COLOR) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T manOWar) {
        return BiomesWeveGone.id("man_o_war");
    }
}