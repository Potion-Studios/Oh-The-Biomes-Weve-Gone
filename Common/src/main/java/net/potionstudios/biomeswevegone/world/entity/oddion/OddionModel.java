package net.potionstudios.biomeswevegone.world.entity.oddion;

import net.minecraft.resources.ResourceLocation;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.model.GeoModel;

/**
 * Oddion Model
 * @see GeoModel
 * @author YaBoiChips, Joseph T. McQuigg
 */
public class OddionModel<T extends GeoAnimatable> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(T object) {
        return BiomesWeveGone.id("geo/oddion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return BiomesWeveGone.id("textures/entity/oddion/" + ((Oddion) object).getVariant().getName() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return BiomesWeveGone.id("animations/oddion.animation.json");
    }

    @Override
    public Animation getAnimation(T animatable, String name) {
        return super.getAnimation(animatable, name);
    }
}