package net.potionstudios.biomeswevegone.fabric;

import net.potionstudios.biomeswevegone.world.level.levelgen.biome.TerraBlenderRegister;
import terrablender.api.TerraBlenderApi;

/**
 * Handles the initialization of TerraBlender for Biomes We've Gone
 * @see TerraBlenderApi
 * @author CorgiTaco
 */
public class BWGTerraBlenderEntry implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        BiomesWeveGoneFabric.initializeBiomesWeveGone("TerraBlender Initializer");
        TerraBlenderRegister.register();
    }
}
