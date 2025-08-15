package net.potionstudios.biomeswevegone.client.renderer.entity.manowar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;
import net.potionstudios.biomeswevegone.world.entity.manowar.ManOWar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

/**
 * ManOWar Entity Renderer
 * @see GeoEntityRenderer
 * @author YaBoiChips
 */
public class ManOWarRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<ManOWar, R> {

    protected static final DataTicket<Boolean> BABY = DataTicket.create("baby", Boolean.class);
    protected static final DataTicket<String> COLOR = DataTicket.create("color", String.class);
    protected static final DataTicket<Float> xBODY_ROT = DataTicket.create("x_body_rot", Float.class);
    protected static final DataTicket<Float> zBODY_ROT = DataTicket.create("z_body_rot", Float.class);
    protected static final DataTicket<Float> xBODY_ROT_O = DataTicket.create("x_body_rot_o", Float.class);
    protected static final DataTicket<Float> zBODY_ROT_O = DataTicket.create("z_body_rot_o", Float.class);

    public ManOWarRenderer(EntityRendererProvider.Context context) {
        super(context, new ManOWarModel<>());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int renderColor) {
        if (renderState.getGeckolibData(BABY)) poseStack.scale(0.5f, 0.5f, 0.5f);
        super.preRender(renderState, poseStack, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, renderColor);
    }

    @Override
    protected void applyRotations(R renderState, PoseStack poseStack, float nativeScale) {
        float i = Mth.lerp(renderState.getGeckolibData(DataTickets.PARTIAL_TICK), renderState.getGeckolibData(xBODY_ROT_O), renderState.getGeckolibData(xBODY_ROT));
        float j = Mth.lerp(renderState.getGeckolibData(DataTickets.PARTIAL_TICK), renderState.getGeckolibData(zBODY_ROT_O), renderState.getGeckolibData(zBODY_ROT));
        poseStack.translate(0.0D, 0.5D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - renderState.getGeckolibData(DataTickets.ENTITY_YAW))); //TODO: This used to be rotationYaw, but that was removed in 1.21.6, so we use entity yaw instead?
        poseStack.mulPose(Axis.XP.rotationDegrees(i));
        poseStack.mulPose(Axis.YP.rotationDegrees(j));
        poseStack.translate(0.0D, -1.2000000476837158D, 0.0D);
    }

    @Override
    public void addRenderData(ManOWar animatable, Void relatedObject, R renderState) {
        renderState.addGeckolibData(BABY, animatable.isBaby());
        renderState.addGeckolibData(COLOR, animatable.getColor().getSerializedName());
        renderState.addGeckolibData(xBODY_ROT, animatable.xBodyRot);
        renderState.addGeckolibData(zBODY_ROT, animatable.zBodyRot);
        renderState.addGeckolibData(xBODY_ROT_O, animatable.xBodyRotO);
        renderState.addGeckolibData(zBODY_ROT_O, animatable.zBodyRotO);
    }

    @Override
    protected float getShadowRadius(@NotNull R renderState) {
        return renderState.getGeckolibData(BABY) ? 0.5f : 0.8f;
    }
}
