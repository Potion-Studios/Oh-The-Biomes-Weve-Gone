package net.potionstudios.biomeswevegone.forge.client;

import net.minecraft.client.color.item.ItemTintSources;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.BiomesWeveGoneClient;
import net.potionstudios.biomeswevegone.forge.client.model.WreathBlockState;

/**
 * This class is used to initialize the Forge client side of the mod.
 * @see BiomesWeveGoneClient
 * @author Joseph T. McQuigg
 */
@OnlyIn(Dist.CLIENT)
public class BiomesWeveGoneClientForge {

    /**
     * Initializes the client side of the Forge mod.
     * @param eventBus The event bus to register the client side of the mod to.
     */
    public static void init(final BusGroup eventBus) {
        FMLClientSetupEvent.getBus(eventBus).addListener((FMLClientSetupEvent event) -> {
            BiomesWeveGoneClient.onInitialize();
        });
        EntityRenderersEvent.RegisterRenderers.BUS.addListener((EntityRenderersEvent.RegisterRenderers event) -> {
            BiomesWeveGoneClient.registerEntityRenderers(event::registerEntityRenderer);
            BiomesWeveGoneClient.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
        });
        RegisterParticleProvidersEvent.BUS.addListener((RegisterParticleProvidersEvent event) -> BiomesWeveGoneClient.registerParticles((type, spriteProviderFactory) -> event.registerSpriteSet(type, spriteProviderFactory::apply)));
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener((EntityRenderersEvent.RegisterLayerDefinitions event) -> BiomesWeveGoneClient.registerLayerDefinitions(event::registerLayerDefinition));
        RegisterColorHandlersEvent.Block.BUS.addListener((RegisterColorHandlersEvent.Block event) -> BiomesWeveGoneClient.registerBlockColors(event::register));
        BiomesWeveGoneClient.registerItemTintSources(ItemTintSources.ID_MAPPER::put);
        ModelEvent.RegisterModelStateDefinitions.BUS.addListener((ModelEvent.RegisterModelStateDefinitions event) -> event.register(BiomesWeveGone.id("wreath"), WreathBlockState.STATE));
    }
}
