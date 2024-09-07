package net.potionstudios.biomeswevegone.world.entity.pumpkinwarden;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Pumpkin Warden Entity Renderer
 * @see GeoEntityRenderer
 * @author YaBoiChips
 */
public class PumpkinWardenRenderer<T extends PumpkinWarden> extends GeoEntityRenderer<T> {

    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(BiomesWeveGone.id("pumpkinwarden"), "main");

    public PumpkinWardenRenderer(EntityRendererProvider.Context context) {
        super(context, new PumpkinWardenModel<>());
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        stack.scale(0.5f, 0.5f, 0.5f);
        shadowRadius = 0.8f;
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public void renderRecursively(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (bone.getName().equals("RightArm")) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees(15));
            poseStack.mulPose(Axis.YP.rotationDegrees(0));
            poseStack.mulPose(Axis.ZP.rotationDegrees(3.5f));
            poseStack.translate(0.05D, 0.2D, -1D);
            poseStack.scale(2f, 2f, 2f);
            Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, packedOverlay, poseStack, bufferSource, animatable.level(), 1);
            poseStack.popPose();
            buffer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(animatable)));
        }

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }
}
