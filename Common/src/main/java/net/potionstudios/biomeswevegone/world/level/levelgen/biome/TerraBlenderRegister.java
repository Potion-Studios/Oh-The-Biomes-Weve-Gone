package net.potionstudios.biomeswevegone.world.level.levelgen.biome;

import net.potionstudios.biomeswevegone.BiomesWeveGone;
import terrablender.api.SurfaceRuleManager;

public class TerraBlenderRegister {
	public static void register() {
		BWGTerraBlenderRegion.registerTerraBlenderRegions();
		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, BiomesWeveGone.MOD_ID, BWGOverworldSurfaceRules.makeRules());
	}
}
