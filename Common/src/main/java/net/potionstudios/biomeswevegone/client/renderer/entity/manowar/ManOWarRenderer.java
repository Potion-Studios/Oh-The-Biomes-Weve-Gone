package net.potionstudios.biomeswevegone.client.renderer.entity.manowar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;
import net.potionstudios.biomeswevegone.world.entity.manowar.ManOWar;
import org.jspecify.annotations.NonNull;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;

/**
 * ManOWar Entity Renderer
 * @see GeoEntityRenderer
 * @author YaBoiChips
 */
public class ManOWarRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<ManOWar, @NonNull R> {

    protected static final DataTicket<Boolean> BABY = DataTicket.create("baby", Boolean.class);
    protected static final DataTicket<String> COLOR = DataTicket.create("color", String.class);
    protected static final DataTicket<Float> xBODY_ROT = DataTicket.create("x_body_rot", Float.class);
    protected static final DataTicket<Float> zBODY_ROT = DataTicket.create("z_body_rot", Float.class);
    protected static final DataTicket<Float> xBODY_ROT_O = DataTicket.create("x_body_rot_o", Float.class);
    protected static final DataTicket<Float> zBODY_ROT_O = DataTicket.create("z_body_rot_o", Float.class);

    public ManOWarRenderer(EntityRendererProvider.Context context) {
        super(context, new ManOWarModel<>());
        getRenderLayers().add(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preRenderPass(RenderPassInfo<@NonNull R> renderPassInfo, @NonNull SubmitNodeCollector renderTasks) {
        if (renderPassInfo.getGeckolibData(BABY)) renderPassInfo.poseStack().scale(0.5f, 0.5f, 0.5f);
        super.preRenderPass(renderPassInfo, renderTasks);
    }

    @Override
    protected void applyRotations(RenderPassInfo<@NonNull R> renderPassInfo, PoseStack poseStack, float nativeScale) {
        float i = Mth.lerp(renderPassInfo.getGeckolibData(DataTickets.PARTIAL_TICK), renderPassInfo.getGeckolibData(xBODY_ROT_O), renderPassInfo.getGeckolibData(xBODY_ROT));
        float j = Mth.lerp(renderPassInfo.getGeckolibData(DataTickets.PARTIAL_TICK), renderPassInfo.getGeckolibData(zBODY_ROT_O), renderPassInfo.getGeckolibData(zBODY_ROT));
        poseStack.translate(0.0D, 0.5D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - renderPassInfo.getGeckolibData(DataTickets.ENTITY_YAW))); //TODO: This used to be rotationYaw, but that was removed in 1.21.6, so we use entity yaw instead?
        poseStack.mulPose(Axis.XP.rotationDegrees(i));
        poseStack.mulPose(Axis.YP.rotationDegrees(j));
        poseStack.translate(0.0D, -1.2000000476837158D, 0.0D);
        super.applyRotations(renderPassInfo, poseStack, nativeScale);
    }

    @Override
    public void addRenderData(ManOWar animatable, Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(BABY, animatable.isBaby());
        renderState.addGeckolibData(COLOR, animatable.getColor().getSerializedName());
        renderState.addGeckolibData(xBODY_ROT, animatable.xBodyRot);
        renderState.addGeckolibData(zBODY_ROT, animatable.zBodyRot);
        renderState.addGeckolibData(xBODY_ROT_O, animatable.xBodyRotO);
        renderState.addGeckolibData(zBODY_ROT_O, animatable.zBodyRotO);
    }

    @Override
    protected float getShadowRadius(@NonNull R renderState) {
        return renderState.getGeckolibData(BABY) ? 0.5f : 0.8f;
    }
}
