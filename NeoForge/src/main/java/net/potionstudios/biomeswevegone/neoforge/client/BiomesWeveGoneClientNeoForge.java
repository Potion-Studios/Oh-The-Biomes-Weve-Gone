package net.potionstudios.biomeswevegone.neoforge.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.BiomesWeveGoneClient;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to initialize the Forge client side of the mod.
 * @see BiomesWeveGoneClient
 * @author Joseph T. McQuigg
 */
@Mod(value = BiomesWeveGone.MOD_ID, dist = Dist.CLIENT)
public class BiomesWeveGoneClientNeoForge {

    public static final Map<String, StandaloneModelKey<BlockStateModel>> ADDITIONAL_MODELS = new HashMap<>();

    /**
     * Constructor for the client side of the NeoForge mod.
     * @param eventBus The event bus to register the client side of the mod to.
     */
    public BiomesWeveGoneClientNeoForge(final IEventBus eventBus) {
        eventBus.addListener((FMLClientSetupEvent event) -> {
            BiomesWeveGoneClient.onInitialize();
            BiomesWeveGoneClient.registerBlockRenderTypes(ItemBlockRenderTypes::setRenderLayer);
        });
        eventBus.addListener((EntityRenderersEvent.RegisterRenderers event) -> BiomesWeveGoneClient.registerEntityRenderers(event::registerEntityRenderer));
        eventBus.addListener((EntityRenderersEvent.RegisterRenderers event) -> BiomesWeveGoneClient.registerBlockEntityRenderers(event::registerBlockEntityRenderer));
        eventBus.addListener((RegisterParticleProvidersEvent event) -> BiomesWeveGoneClient.registerParticles((type, spriteProviderFactory) -> event.registerSpriteSet(type, spriteProviderFactory::apply)));
        eventBus.addListener((EntityRenderersEvent.RegisterLayerDefinitions event) -> BiomesWeveGoneClient.registerLayerDefinitions(event::registerLayerDefinition));
        eventBus.addListener((RegisterColorHandlersEvent.BlockTintSources event) -> BiomesWeveGoneClient.registerBlockColors(event::register));
        eventBus.addListener((RegisterColorHandlersEvent.ItemTintSources event) -> BiomesWeveGoneClient.registerItemTintSources(event::register));
        eventBus.addListener((ModelEvent.RegisterStandalone event) -> BiomesWeveGoneClient.registerAdditionalModels((name) -> {
            StandaloneModelKey<BlockStateModel> key = new StandaloneModelKey<>(() -> BiomesWeveGone.MOD_ID + ":" + name);
            ADDITIONAL_MODELS.put(name, key);
            event.register(key, SimpleUnbakedStandaloneModel.blockStateModel(BiomesWeveGone.id("block/" + name)));
        }));
    }
}