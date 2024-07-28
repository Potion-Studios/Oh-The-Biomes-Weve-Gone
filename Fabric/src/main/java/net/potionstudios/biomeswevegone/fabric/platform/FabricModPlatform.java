package net.potionstudios.biomeswevegone.fabric.platform;

import com.google.auto.service.AutoService;
import net.fabricmc.loader.api.FabricLoader;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.platform.ModPlatform;

import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class FabricModPlatform implements ModPlatform {

    @Override
    public Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(BiomesWeveGone.MOD_ID);
    }
}
