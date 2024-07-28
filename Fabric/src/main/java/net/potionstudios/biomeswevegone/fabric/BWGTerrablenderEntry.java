package net.potionstudios.biomeswevegone.fabric;

import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGOverworldSurfaceRules;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGTerraBlenderRegion;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class BWGTerrablenderEntry implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        BiomesWeveGoneFabric.initializeBiomesWeveGone("TerraBlender Initializer");
        BWGTerraBlenderRegion.registerTerrablenderRegions();

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, BiomesWeveGone.MOD_ID, BWGOverworldSurfaceRules.makeRules());
    }
}
