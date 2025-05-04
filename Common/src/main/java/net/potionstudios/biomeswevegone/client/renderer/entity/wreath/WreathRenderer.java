package net.potionstudios.biomeswevegone.client.renderer.entity.wreath;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;
import net.potionstudios.biomeswevegone.world.entity.decoration.Wreath;
import org.jetbrains.annotations.NotNull;

public class WreathRenderer extends EntityRenderer<Wreath> {
	private final BlockRenderDispatcher blockRenderer;

	public WreathRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	@Override
	public void render(@NotNull Wreath entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		poseStack.pushPose();
		Direction direction = entity.getDirection();
		Vec3 vec3 = this.getRenderOffset(entity, partialTick);
		poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
		poseStack.translate(direction.getStepX() * 0.46875, direction.getStepY() * 0.46875, direction.getStepZ() * 0.46875);
		poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));
		if (!entity.isInvisible()) {
			ModelManager modelManager = blockRenderer.getBlockModelShaper().getModelManager();
			poseStack.pushPose();
			poseStack.translate(-0.5F, -0.5F, -0.5F);
			blockRenderer.getModelRenderer()
					.renderModel(
							poseStack.last(),
							bufferSource.getBuffer(Sheets.cutoutBlockSheet()),
							null,
							ModelAccess.MODEL_ACCESS.getModel(new ModelResourceLocation(BiomesWeveGone.id("block/" + entity.getVariant().getSerializedName() + "_wreath"), "standalone"), modelManager),
							1.0F,
							1.0F,
							1.0F,
							packedLight,
							OverlayTexture.NO_OVERLAY);
			poseStack.popPose();
		}
		poseStack.popPose();
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Wreath entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
