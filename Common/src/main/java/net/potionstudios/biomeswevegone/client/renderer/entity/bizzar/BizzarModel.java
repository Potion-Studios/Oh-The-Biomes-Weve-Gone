package net.potionstudios.biomeswevegone.client.renderer.entity.bizzar;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.bizzar.Bizzar;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class BizzarModel<T extends Bizzar> extends GeoModel<T> {
	private static final Map<DyeColor, ResourceLocation> BIZZAR_TEXTURES = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
		map.put(DyeColor.WHITE, BiomesWeveGone.id("textures/entity/bizzar/white.png"));
		map.put(DyeColor.ORANGE, BiomesWeveGone.id("textures/entity/bizzar/orange.png"));
		map.put(DyeColor.MAGENTA, BiomesWeveGone.id("textures/entity/bizzar/magenta.png"));
		map.put(DyeColor.LIGHT_BLUE, BiomesWeveGone.id("textures/entity/bizzar/light_blue.png"));
		map.put(DyeColor.YELLOW, BiomesWeveGone.id("textures/entity/bizzar/yellow.png"));
		map.put(DyeColor.LIME, BiomesWeveGone.id("textures/entity/bizzar/lime.png"));
		map.put(DyeColor.PINK, BiomesWeveGone.id("textures/entity/bizzar/pink.png"));
		map.put(DyeColor.GRAY, BiomesWeveGone.id("textures/entity/bizzar/gray.png"));
		map.put(DyeColor.LIGHT_GRAY, BiomesWeveGone.id("textures/entity/bizzar/light_gray.png"));
		map.put(DyeColor.CYAN, BiomesWeveGone.id("textures/entity/bizzar/cyan.png"));
		map.put(DyeColor.PURPLE, BiomesWeveGone.id("textures/entity/bizzar/purple.png"));
		map.put(DyeColor.BLUE, BiomesWeveGone.id("textures/entity/bizzar/blue.png"));
		map.put(DyeColor.BROWN, BiomesWeveGone.id("textures/entity/bizzar/brown.png"));
		map.put(DyeColor.GREEN, BiomesWeveGone.id("textures/entity/bizzar/green.png"));
		map.put(DyeColor.RED, BiomesWeveGone.id("textures/entity/bizzar/red.png"));
		map.put(DyeColor.BLACK, BiomesWeveGone.id("textures/entity/bizzar/black.png"));
	});

	@Override
	public ResourceLocation getModelResource(T animatable) {
		return BiomesWeveGone.id("geo/bizzar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		return BIZZAR_TEXTURES.getOrDefault(animatable.getColor(), BIZZAR_TEXTURES.get(DyeColor.WHITE));
	}

	@Override
	public ResourceLocation getAnimationResource(T animatable) {
		return BiomesWeveGone.id("animations/bizzar.animation.json");
	}
}
