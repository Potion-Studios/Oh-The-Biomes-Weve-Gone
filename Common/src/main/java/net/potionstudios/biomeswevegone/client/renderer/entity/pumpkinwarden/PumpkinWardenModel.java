package net.potionstudios.biomeswevegone.client.renderer.entity.pumpkinwarden;

import net.minecraft.resources.Identifier;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jspecify.annotations.NonNull;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

/**
 * Pumpkin Warden Model
 * @see GeoModel
 * @author YaBoiChips
 */
class PumpkinWardenModel<T extends PumpkinWarden> extends GeoModel<T> {

    @Override
    public @NonNull Identifier getModelResource(@NonNull GeoRenderState renderState) {
        return BiomesWeveGone.id("pumpkinwarden");
    }

    @Override
    public @NonNull Identifier getTextureResource(GeoRenderState renderState) {
        return renderState.getGeckolibData(PumpkinWardenRenderer.HIDING) ? BiomesWeveGone.id("textures/entity/pumpkin_warden/" + renderState.getGeckolibData(PumpkinWardenRenderer.VARIANT) + "_hiding.png") : BiomesWeveGone.id("textures/entity/pumpkin_warden/" + renderState.getGeckolibData(PumpkinWardenRenderer.VARIANT) + ".png");
    }

    @Override
    public @NonNull Identifier getAnimationResource(T pumpkinWarden) {
        return BiomesWeveGone.id("pumpkinwarden");
    }
}