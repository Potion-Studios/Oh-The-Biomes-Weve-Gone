package net.potionstudios.biomeswevegone.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.BiomesWeveGoneClient;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Initializes the Fabric client.
 * @see ClientModInitializer#onInitializeClient()
 * @see BiomesWeveGoneClient
 * @author Joseph T. McQuigg
 */
@Environment(EnvType.CLIENT)
public class BiomesWeveGoneClientFabric implements ClientModInitializer, ModelLoadingPlugin {

    public static final Map<String, ExtraModelKey<BlockStateModel>> EXTRA_MODELS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        BiomesWeveGoneClient.onInitialize();
        BiomesWeveGoneClient.registerEntityRenderers(EntityRenderers::register);
        BiomesWeveGoneClient.registerBlockEntityRenderers(BlockEntityRenderers::register);
        BiomesWeveGoneClient.registerParticles((type, spriteProviderFactory) -> ParticleProviderRegistry.getInstance().register(type, spriteProviderFactory::apply));
        BiomesWeveGoneClient.registerLayerDefinitions((a, b) -> ModelLayerRegistry.registerModelLayer(a, b::get));
        BiomesWeveGoneClient.registerBlockColors(BlockColorRegistry::register);
        BiomesWeveGoneClient.registerItemTintSources(ItemTintSources.ID_MAPPER::put);
        ModelLoadingPlugin.register(this);
    }

    @Override
    public void initialize(@NonNull Context context) {
        BiomesWeveGoneClient.registerAdditionalModels((name) -> {
            ExtraModelKey<BlockStateModel> key = ExtraModelKey.create(() -> name);
            EXTRA_MODELS.put(name, key);
            context.addModel(key, blockStateModel(BiomesWeveGone.id("block/" + name)));
        });
    }

    private static SimpleUnbakedExtraModel<BlockStateModel> blockStateModel(Identifier model) {
        return new SimpleUnbakedExtraModel<>(model, (baked, baker) -> {
            TextureSlots textures = baked.getTopTextureSlots();
            return new SingleVariant(new SimpleModelWrapper(
                    baked.bakeTopGeometry(textures, baker, BlockModelRotation.IDENTITY),
                    baked.getTopAmbientOcclusion(),
                    baked.resolveParticleMaterial(textures, baker)
            ));
        });
    }
}
