package net.potionstudios.biomeswevegone.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.commands.BWGCommands;
import net.potionstudios.biomeswevegone.forge.loot.LootModifiersRegister;
import net.potionstudios.biomeswevegone.forge.client.BiomesWeveGoneClientForge;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTrades;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.TerraBlenderRegister;

/**
 * Main class for the mod on the Forge platform.
 * @see Mod
 * @see BiomesWeveGone
 * @author Joseph T. McQuigg
 */
@Mod(BiomesWeveGone.MOD_ID)
public class BiomesWeveGoneForge {
    public BiomesWeveGoneForge(final FMLJavaModLoadingContext context) {
        IEventBus MOD_BUS = context.getModEventBus();
        IEventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
        BiomesWeveGone.init();
        ForgePlatformHandler.register(MOD_BUS);
        MOD_BUS.addListener(this::onInitialize);
        MOD_BUS.addListener(this::onPostInitialize);
        EVENT_BUS.addListener((ServerAboutToStartEvent event) -> BiomesWeveGone.serverStart(event.getServer()));
        MOD_BUS.addListener((EntityAttributeCreationEvent event) -> BWGEntityType.registerEntityAttributes(event::put));
        MOD_BUS.addListener((SpawnPlacementRegisterEvent event) -> BWGEntityType.registerSpawnPlacements((consumer) -> event.register(consumer.entityType(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate(), SpawnPlacementRegisterEvent.Operation.OR)));
        EVENT_BUS.addListener((RegisterCommandsEvent event) -> BWGCommands.register(event.getDispatcher()::register));
        EVENT_BUS.addListener((EntityJoinLevelEvent event) -> BiomesWeveGone.onEntityLoad(event.getEntity()));
        VanillaCompatForge.registerVanillaCompatEvents(EVENT_BUS);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> BiomesWeveGoneClientForge.init(MOD_BUS));
        LootModifiersRegister.register(MOD_BUS);
    }

    /**
     * Should initialize everything where a specific event does not cover it.
     * @see FMLCommonSetupEvent
     */
    private void onInitialize(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BiomesWeveGone.commonSetup();
            VanillaCompatForge.init();
            TerraBlenderRegister.register();
            ForgePlatformHandler.registerPottedPlants();
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
