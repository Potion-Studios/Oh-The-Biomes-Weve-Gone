package net.potionstudios.biomeswevegone.world.entity.oddion;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
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
    public void render(@NotNull T entity, float entityYaw, float partialTicks, PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        shadowRadius = 0.4f;
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
