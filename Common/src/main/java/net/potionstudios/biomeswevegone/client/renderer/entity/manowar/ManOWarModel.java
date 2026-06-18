package net.potionstudios.biomeswevegone.client.renderer.entity.manowar;

import net.minecraft.resources.Identifier;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.manowar.ManOWar;
import org.jspecify.annotations.NonNull;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

/**
 * ManOWar Model
 * @see GeoModel
 * @author YaBoiChips
 */
class ManOWarModel<T extends ManOWar> extends GeoModel<T> {

    @Override
    public @NonNull Identifier getModelResource(@NonNull GeoRenderState renderState) {
        return BiomesWeveGone.id("man_o_war");
    }

    @Override
    public @NonNull Identifier getTextureResource(GeoRenderState renderState) {
        return BiomesWeveGone.id("textures/entity/manowar/" + renderState.getGeckolibData(ManOWarRenderer.COLOR) + ".png");
    }

    @Override
    public @NonNull Identifier getAnimationResource(@NonNull T manOWar) {
        return BiomesWeveGone.id("man_o_war");
    }
}