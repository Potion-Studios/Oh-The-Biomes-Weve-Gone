package net.potionstudios.biomeswevegone.compat.wthit;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.component.PairComponent;
import net.minecraft.network.chat.Component;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.BWGBerryBush;


/**
 * Allows seeing bwg plant information in Waila.
 * @see IBlockComponentProvider
 * @author Joseph T. McQuigg
 */
enum BWGPlantProvider implements IBlockComponentProvider {

	INSTANCE;

	private static void addMaturityTooltip(ITooltip tooltip, float growthValue) {
		growthValue *= 100.0F;
		if (growthValue < 100.0F) {
			tooltip.addLine(new PairComponent(
					Component.translatable("tooltip.waila.crop_growth"), Component.literal(String.format("%.0f%%", growthValue))));
		} else
			tooltip.addLine(new PairComponent(
					Component.translatable("tooltip.waila.crop_growth" ), Component.translatable("tooltip.waila.crop_mature")
			));
	}

	@Override
	public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
		if (accessor.getBlock() instanceof BWGBerryBush)
			addMaturityTooltip(tooltip, (float) accessor.getBlockState().getValue(BWGBerryBush.AGE) / BWGBerryBush.MAX_AGE);
	}

}
