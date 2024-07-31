package net.potionstudios.biomeswevegone.world.entity.ai.village.poi;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;

import java.util.function.Supplier;

public class BWGPoiTypes {

    public static final ResourceKey<PoiType> FORAGER = register("forager", () -> new PoiType(PoiTypes.getBlockStates(BWGBlocks.FORAGERS_TABLE.get()), 1, 1));

    private static ResourceKey<PoiType> register(String id, Supplier<PoiType> poiType){
        RegistrationHandlerA.REGISTRATION.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, id, poiType);
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, BiomesWeveGone.id(id));
    }

    public static void poiTypes() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Poi Types");
    }
}
