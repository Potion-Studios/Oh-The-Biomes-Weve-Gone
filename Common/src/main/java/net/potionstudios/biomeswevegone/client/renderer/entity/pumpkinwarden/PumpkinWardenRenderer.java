package net.potionstudios.biomeswevegone.client.renderer.entity.pumpkinwarden;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.ItemInHandGeoLayer;

/**
 * Pumpkin Warden Entity Renderer
 * @see GeoEntityRenderer
 * @author YaBoiChips
 */
public class PumpkinWardenRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<PumpkinWarden, R> {
    protected static final DataTicket<Boolean> HIDING = DataTicket.create("hiding", Boolean.class);
    protected static final DataTicket<String> VARIANT = DataTicket.create("variant", String.class);

    public PumpkinWardenRenderer(EntityRendererProvider.Context context) {
        super(context, new PumpkinWardenModel<>());
        addRenderLayer(new ItemInHandGeoLayer<>(this));
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int renderColor) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
        super.preRender(renderState, poseStack, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, renderColor);
    }

    @Override
    protected float getShadowRadius(@NotNull R renderState) {
        return renderState.getGeckolibData(HIDING) ? 0.0f : 0.6f;
    }

    @Override
    public void addRenderData(PumpkinWarden animatable, Void relatedObject, R renderState) {
        renderState.addGeckolibData(HIDING, animatable.isHiding());
        renderState.addGeckolibData(VARIANT, animatable.getVariant().getSerializedName());
    }
}
