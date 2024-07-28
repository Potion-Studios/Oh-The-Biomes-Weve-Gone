package net.potionstudios.biomeswevegone.compat.wthit;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.BWGBerryBush;

/**
 * Waila plugin for BiomesWeveGone.
 * @see IWailaPlugin
 * @author Joseph T. McQuigg
 */
public class BWGWTHITPlugin implements IWailaPlugin {
	@Override
	public void register(IRegistrar registrar) {
		registrar.addComponent(BWGPlantProvider.INSTANCE, TooltipPosition.BODY, BWGBerryBush.class);
	}
}
