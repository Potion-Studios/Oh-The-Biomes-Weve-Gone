package net.potionstudios.biomeswevegone.forge.platform;

import com.google.auto.service.AutoService;
import net.minecraftforge.fml.loading.FMLPaths;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.platform.ModPlatform;

import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class ForgeModPlatform implements ModPlatform {

    @Override
    public Path configPath() {
        return FMLPaths.CONFIGDIR.get().resolve(BiomesWeveGone.MOD_ID);
    }
}
