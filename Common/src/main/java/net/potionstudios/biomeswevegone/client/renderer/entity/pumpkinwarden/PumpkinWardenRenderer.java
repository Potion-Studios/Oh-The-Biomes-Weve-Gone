package net.potionstudios.biomeswevegone.client.renderer.entity.pumpkinwarden;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jspecify.annotations.NonNull;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.builtin.ItemInHandGeoLayer;

/**
 * Pumpkin Warden Entity Renderer
 * @see GeoEntityRenderer
 * @author YaBoiChips, Joseph T. McQuigg
 */
public class PumpkinWardenRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<PumpkinWarden, @NonNull R> {
    protected static final DataTicket<Boolean> HIDING = DataTicket.create("hiding", Boolean.class);
    protected static final DataTicket<String> VARIANT = DataTicket.create("variant", String.class);

    public PumpkinWardenRenderer(EntityRendererProvider.Context context) {
        super(context, new PumpkinWardenModel<>());
        getRenderLayers().add(new ItemInHandGeoLayer<>(this));
    }

    @Override
    public void preRenderPass(RenderPassInfo<@NonNull R> renderPassInfo, @NonNull SubmitNodeCollector renderTasks) {
        renderPassInfo.poseStack().scale(0.5f, 0.5f, 0.5f);
        super.preRenderPass(renderPassInfo, renderTasks);
    }

    @Override
    protected float getShadowRadius(@NonNull R renderState) {
        return renderState.getGeckolibData(HIDING) ? 0.0f : 0.6f;
    }

    @Override
    public void addRenderData(PumpkinWarden animatable, Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(HIDING, animatable.isHiding());
        renderState.addGeckolibData(VARIANT, animatable.getVariant().getSerializedName());
    }
}
