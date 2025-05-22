package net.potionstudios.biomeswevegone.world.level.saveddata.maps;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public class BWGMapDecorationTypes {

    public static final Supplier<Holder.Reference<MapDecorationType>> BOG_TRIAL = register("bog_trial", "bog_trial", true, 12741452, false, true);

    private static Supplier<Holder.Reference<MapDecorationType>> register(String name, String assetId, boolean showOnItemFrame, int mapColor, boolean explorationMapElement, boolean trackCount) {
        return PlatformHandler.PLATFORM_HANDLER.registerForHolder(BuiltInRegistries.MAP_DECORATION_TYPE, name, () -> new MapDecorationType(BiomesWeveGone.id(assetId), showOnItemFrame, mapColor, trackCount, explorationMapElement));
    }

    public static void mapDecorationTypes() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Map Decoration Types");
    }
}
