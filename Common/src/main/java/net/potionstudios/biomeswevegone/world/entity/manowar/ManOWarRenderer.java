package net.potionstudios.biomeswevegone.world.entity.manowar;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import java.util.Map;

/**
 * ManOWar Entity Renderer
 * @see GeoEntityRenderer
 * @author YaBoiChips
 */
public class ManOWarRenderer<T extends ManOWar> extends GeoEntityRenderer<T> {

    public static final Map<ManOWar.Colors, ResourceLocation> TEXTURES = Util.make(Maps.newEnumMap(ManOWar.Colors.class), (map) -> {
        map.put(ManOWar.Colors.MAGENTA, BiomesWeveGone.id("textures/entity/manowar/magenta.png"));
        map.put(ManOWar.Colors.RAINBOW, BiomesWeveGone.id("textures/entity/manowar/rainbow.png"));
        map.put(ManOWar.Colors.PURPLE, BiomesWeveGone.id("textures/entity/manowar/purple.png"));
        map.put(ManOWar.Colors.BLUE, BiomesWeveGone.id("textures/entity/manowar/blue.png"));
    });


    public ManOWarRenderer(EntityRendererProvider.Context context) {
        super(context, new ManOWarModel<>());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (animatable.isBaby()) poseStack.scale(0.5f, 0.5f, 0.5f);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(T entity) {
        return TEXTURES.get(entity.getColor());
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        float i = Mth.lerp(partialTick, animatable.xBodyRotO, animatable.xBodyRot);
        float j = Mth.lerp(partialTick, animatable.zBodyRotO, animatable.zBodyRot);
        poseStack.translate(0.0D, 0.5D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(i));
        poseStack.mulPose(Axis.YP.rotationDegrees(j));
        poseStack.translate(0.0D, -1.2000000476837158D, 0.0D);
    }

    @Override
    protected float getShadowRadius(@NotNull T entity) {
	    return entity.isBaby() ? 0.5f : 0.8f;
    }
}
