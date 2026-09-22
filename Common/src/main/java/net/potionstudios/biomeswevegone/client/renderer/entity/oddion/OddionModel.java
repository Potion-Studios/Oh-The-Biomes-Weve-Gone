package net.potionstudios.biomeswevegone.client.renderer.entity.oddion;

import net.minecraft.resources.Identifier;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.oddion.Oddion;
import org.jspecify.annotations.NonNull;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

/**
 * Oddion Model
 * @see GeoModel
 * @author YaBoiChips, Joseph T. McQuigg
 */
class OddionModel extends GeoModel<Oddion> {

    @Override
    public @NonNull Identifier getModelResource(@NonNull GeoRenderState renderState) {
        return BiomesWeveGone.id("oddion");
    }

    @Override
    public @NonNull Identifier getTextureResource(GeoRenderState renderState) {
        return BiomesWeveGone.id("textures/entity/oddion/" + renderState.getGeckolibData(OddionRenderer.VARIANT) + ".png");
    }

    @Override
    public @NonNull Identifier getAnimationResource(Oddion animatable) {
        return BiomesWeveGone.id("oddion");
    }
}