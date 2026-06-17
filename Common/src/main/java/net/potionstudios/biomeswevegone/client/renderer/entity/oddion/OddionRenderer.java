package net.potionstudios.biomeswevegone.client.renderer.entity.oddion;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.potionstudios.biomeswevegone.world.entity.oddion.Oddion;
import org.jspecify.annotations.NonNull;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;

/**
 * Oddion Entity Renderer
 * @see GeoEntityRenderer
 * @author Joseph T. McQuigg
 */
public class OddionRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<Oddion, @NonNull R> {
    protected static final DataTicket<String> VARIANT = DataTicket.create("variant", String.class);

    public OddionRenderer(EntityRendererProvider.Context context) {
        super(context, new OddionModel());
    }

    @Override
    protected float getShadowRadius(R renderState) {
        return 0.4f;
    }

    @Override
    public void addRenderData(Oddion animatable, Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(VARIANT, animatable.getVariant().getSerializedName());
    }
}
