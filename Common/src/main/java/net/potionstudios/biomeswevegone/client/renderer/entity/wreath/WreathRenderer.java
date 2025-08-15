package net.potionstudios.biomeswevegone.client.renderer.entity.wreath;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.client.model.ModelAccess;
import net.potionstudios.biomeswevegone.world.entity.decoration.Wreath;
import org.jetbrains.annotations.NotNull;

public class WreathRenderer extends EntityRenderer<Wreath, WreathRenderState> {
	private final BlockRenderDispatcher blockRenderer;

	public WreathRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	@Override
	public void render(@NotNull WreathRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
		super.render(renderState, poseStack, bufferSource, packedLight);
		poseStack.pushPose();
		Direction direction = renderState.direction;
		Vec3 vec3 = getRenderOffset(renderState);
		poseStack.translate(-vec3.x, -vec3.y, -vec3.z);
		poseStack.translate(direction.getStepX() * 0.46875, direction.getStepY() * 0.46875, direction.getStepZ() * 0.46875);
		float f;
		float g;
		if (direction.getAxis().isHorizontal()) {
			f = 0.0F;
			g = 180.0F - direction.toYRot();
		} else {
			f = -90 * direction.getAxisDirection().getStep();
			g = 180.0F;
		}
		poseStack.mulPose(Axis.XP.rotationDegrees(f));
		poseStack.mulPose(Axis.YP.rotationDegrees(g));
		if (!renderState.isInvisible) {
			poseStack.pushPose();
			poseStack.translate(-0.5, -0.5, -0.5);
			ModelBlockRenderer.renderModel(poseStack.last(),
					bufferSource.getBuffer(RenderType.entityCutout(TextureAtlas.LOCATION_BLOCKS)),
                    ModelAccess.MODEL_ACCESS.getModel(renderState.type.getSerializedName() + "_wreath", blockRenderer),
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
	public @NotNull WreathRenderState createRenderState() {
		return new WreathRenderState();
	}

	@Override
	public void extractRenderState(@NotNull Wreath entity, @NotNull WreathRenderState reusedState, float partialTick) {
		super.extractRenderState(entity, reusedState, partialTick);
		reusedState.direction = entity.getDirection();
		reusedState.type = entity.getVariant();
	}
}
