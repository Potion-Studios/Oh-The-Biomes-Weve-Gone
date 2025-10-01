package net.potionstudios.biomeswevegone.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.commands.BWGCommands;
import net.potionstudios.biomeswevegone.neoforge.loot.LootModifiersRegister;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTrades;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGOverworldSurfaceRules;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGTerraBlenderRegion;
import terrablender.api.SurfaceRuleManager;

@Mod(BiomesWeveGone.MOD_ID)
public class BiomesWeveGoneNeoForge {
	public BiomesWeveGoneNeoForge(final IEventBus eventBus) {
		IEventBus EVENT_BUS = NeoForge.EVENT_BUS;
		BiomesWeveGone.init();
		NeoForgePlatformHandler.register(eventBus);
		eventBus.addListener(this::onInitialize);
		eventBus.addListener(this::onPostInitialize);
		EVENT_BUS.addListener((ServerAboutToStartEvent event) -> BiomesWeveGone.serverStart(event.getServer()));
		eventBus.addListener((EntityAttributeCreationEvent event) -> BWGEntityType.registerEntityAttributes(event::put));
		eventBus.addListener((RegisterSpawnPlacementsEvent event) -> BWGEntityType.registerSpawnPlacements((consumer) -> event.register(consumer.entityType(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate(), RegisterSpawnPlacementsEvent.Operation.OR)));
		EVENT_BUS.addListener((RegisterCommandsEvent event) -> BWGCommands.register(event.getDispatcher()::register));
		EVENT_BUS.addListener((EntityJoinLevelEvent event) -> BiomesWeveGone.onEntityLoad(event.getEntity()));
		VanillaCompatNeoForge.registerVanillaCompatEvents(EVENT_BUS);
		LootModifiersRegister.register(eventBus);
        eventBus.addListener(this::onRegister);
	}

	/**
	 * Should initialize everything where a specific event does not cover it.
	 * @see FMLCommonSetupEvent
	 */
	private void onInitialize(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			BiomesWeveGone.commonSetup();
			VanillaCompatNeoForge.init();
			BWGTerraBlenderRegion.registerTerrablenderRegions();
			NeoForgePlatformHandler.registerPottedPlants();
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

    /**
     * Handles missing mappings for biomes and other registries.
     * @see RegisterEvent
     */
    private void onRegister(RegisterEvent event) {
        event.getRegistry().addAlias(BiomesWeveGone.id("skyrise_vale"), BWGBiomes.SKYRIS_VALE.location());
    }
}
