package net.potionstudios.biomeswevegone.client.renderer.entity.bizzar;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.potionstudios.biomeswevegone.world.entity.bizzar.Bizzar;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BizzarRenderer<T extends Bizzar> extends GeoEntityRenderer<T> {
	public BizzarRenderer(EntityRendererProvider.Context context) {
		super(context, new BizzarModel<>());
	}
}
