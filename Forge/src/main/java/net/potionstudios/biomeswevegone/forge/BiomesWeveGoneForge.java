package net.potionstudios.biomeswevegone.forge;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.commands.BWGCommands;
import net.potionstudios.biomeswevegone.forge.loot.LootModifiersRegister;
import net.potionstudios.biomeswevegone.forge.client.BiomesWeveGoneClientForge;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTrades;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGOverworldSurfaceRules;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGTerraBlenderRegion;
import terrablender.api.SurfaceRuleManager;

/**
 * Main class for the mod on the Forge platform.
 * @see Mod
 * @see BiomesWeveGone
 * @author Joseph T. McQuigg
 */
@Mod(BiomesWeveGone.MOD_ID)
public class BiomesWeveGoneForge {
    public BiomesWeveGoneForge(final FMLJavaModLoadingContext context) {
        BusGroup modBusGroup = context.getModBusGroup();
        BiomesWeveGone.init();
        ForgePlatformHandler.register(modBusGroup);
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::onInitialize);
        FMLLoadCompleteEvent.getBus(modBusGroup).addListener(this::onPostInitialize);
        ServerAboutToStartEvent.BUS.addListener((ServerAboutToStartEvent event) -> BiomesWeveGone.serverStart(event.getServer()));
        EntityAttributeCreationEvent.getBus(modBusGroup).addListener((EntityAttributeCreationEvent event) -> BWGEntityType.registerEntityAttributes(event::put));
        SpawnPlacementRegisterEvent.getBus(modBusGroup).addListener((SpawnPlacementRegisterEvent event) -> BWGEntityType.registerSpawnPlacements((consumer) -> event.register(consumer.entityType(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate(), SpawnPlacementRegisterEvent.Operation.OR)));
        RegisterCommandsEvent.BUS.addListener((RegisterCommandsEvent event) -> BWGCommands.register(event.getDispatcher()::register));
        EntityJoinLevelEvent.BUS.addListener((EntityJoinLevelEvent event) -> BiomesWeveGone.onEntityLoad(event.getEntity()));
        VanillaCompatForge.registerVanillaCompatEvents(modBusGroup);
        if (FMLEnvironment.dist.isClient()) BiomesWeveGoneClientForge.init(modBusGroup);
        LootModifiersRegister.register(modBusGroup);
    }

    /**
     * Should initialize everything where a specific event does not cover it.
     * @see FMLCommonSetupEvent
     */
    private void onInitialize(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BiomesWeveGone.commonSetup();
            VanillaCompatForge.init();
            BWGTerraBlenderRegion.registerTerrablenderRegions();
            ForgePlatformHandler.registerPottedPlants();
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, BiomesWeveGone.MOD_ID, BWGOverworldSurfaceRules.makeRules());
        });
    }

    /**
     * Initializes things that should be done after the mod is fully loaded.
     * @see FMLLoadCompleteEvent
     */
    private void onPostInitialize(final FMLLoadCompleteEvent event) {
        event.enqueueWork(BiomesWeveGone::postInit);
        BWGVillagerTrades.makeTrades();
        BWGVillagerTrades.makeWanderingTrades();
    }
}
