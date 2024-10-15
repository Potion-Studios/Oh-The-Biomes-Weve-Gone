package net.potionstudios.biomeswevegone.world.entity.oddion;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


/**
 * Oddion Entity Renderer
 * @see GeoEntityRenderer
 * @author YaBoiChips
 */
public class OddionRenderer<T extends Oddion> extends GeoEntityRenderer<T> {

    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(BiomesWeveGone.id("oddion"), "main");

    public OddionRenderer(EntityRendererProvider.Context context) {
        super(context, new OddionModel<>());
    }

    @Override
    protected float getShadowRadius(@NotNull T entity) {
        return 0.4f;
    }
}
