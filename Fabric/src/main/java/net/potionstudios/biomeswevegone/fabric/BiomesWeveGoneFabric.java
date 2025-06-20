package net.potionstudios.biomeswevegone.fabric;

import corgitaco.corgilib.fabric.CorgiLibFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.commands.BWGCommands;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;

/**
 * This class is the entrypoint for the mod on the Fabric platform.
 * @see ModInitializer
 * @see BiomesWeveGone
 * @author Joseph T. McQuigg
 */
public class BiomesWeveGoneFabric implements ModInitializer {

    private static String firstInitializedFrom = null;

    @Override
    public void onInitialize() {
        initializeBiomesWeveGone("Biomes We've Gone Fabric Mod Initializer");
    }

    public static void initializeBiomesWeveGone(String initializedFrom) {
        CorgiLibFabric.initializeCorgiLib(initializedFrom);
        if (firstInitializedFrom != null) {
            BiomesWeveGone.LOGGER.debug("Attempted to Initialize Oh The Biomes We've Gone (BWG) from \"{}\" but BWG already was initialized from \"{}\", this should not be a problem.", initializedFrom, firstInitializedFrom);
            return;
        }
        firstInitializedFrom = initializedFrom;

        BiomesWeveGone.init();
        VanillaCompatFabric.init();
        BWGEntityType.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
        BWGEntityType.registerSpawnPlacements((consumer) -> SpawnPlacements.register(consumer.entityType(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate()));
        BiomesWeveGone.commonSetup();
        BiomesWeveGone.postInit();
        ServerLifecycleEvents.SERVER_STARTING.register(BiomesWeveGone::serverStart);
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> BiomesWeveGone.onEntityLoad(entity));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> BWGCommands.register(dispatcher::register));
    }
}
