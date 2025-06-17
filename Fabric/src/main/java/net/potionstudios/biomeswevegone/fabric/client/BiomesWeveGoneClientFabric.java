package net.potionstudios.biomeswevegone.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.potionstudios.biomeswevegone.client.BiomesWeveGoneClient;

/**
 * Initializes the Fabric client.
 * @see ClientModInitializer#onInitializeClient()
 * @see BiomesWeveGoneClient
 * @author Joseph T. McQuigg
 */
@Environment(EnvType.CLIENT)
public class BiomesWeveGoneClientFabric implements ClientModInitializer, ModelLoadingPlugin {
    @Override
    public void onInitializeClient() {
        BiomesWeveGoneClient.onInitialize();
        BiomesWeveGoneClient.registerBlockRenderTypes(BlockRenderLayerMap.INSTANCE::putBlock);
        BiomesWeveGoneClient.registerEntityRenderers(EntityRendererRegistry::register);
        BiomesWeveGoneClient.registerBlockEntityRenderers(BlockEntityRenderers::register);
        BiomesWeveGoneClient.registerParticles((type, spriteProviderFactory) -> ParticleFactoryRegistry.getInstance().register(type, spriteProviderFactory::apply));
        BiomesWeveGoneClient.registerLayerDefinitions((a, b) -> EntityModelLayerRegistry.registerModelLayer(a, b::get));
        BiomesWeveGoneClient.registerBlockColors(ColorProviderRegistry.BLOCK::register);
        BiomesWeveGoneClient.registerItemTintSources(ItemTintSources.ID_MAPPER::put);
        ModelLoadingPlugin.register(this);
    }

    @Override
    public void initialize(Context context) {
        BiomesWeveGoneClient.registerAdditionalModels((modelResourceLocation -> context.addModels(modelResourceLocation.id())));
    }
}
