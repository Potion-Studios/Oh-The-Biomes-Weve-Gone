package net.potionstudios.biomeswevegone.forge.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.forge.datagen.generators.*;
import net.potionstudios.biomeswevegone.forge.datagen.generators.loot.GlobalLootModifiersGenerator;
import net.potionstudios.biomeswevegone.forge.datagen.generators.loot.LootGenerator;
import net.potionstudios.biomeswevegone.world.damagesource.BWGDamageTypes;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.modifiers.BWGBiomeModifiers;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.configured.ConfiguredFeaturesUtil;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.PlacedFeaturesUtil;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructureSets;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructures;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGTemplatePools;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.BWGStructureProcessorLists;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * This class is used to register the data generators for the mod.
 * @see GatherDataEvent
 * @author Joseph T. McQuigg
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BiomesWeveGone.MOD_ID)
class DataGeneratorsRegister {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModelGenerators.init(generator, event.includeClient(), output, existingFileHelper);
        generator.addProvider(event.includeClient(), new LangGenerator(output, "en_us"));
        generator.addProvider(event.includeServer(), new RecipeGenerator(output));
        generator.addProvider(event.includeServer(), new LootGenerator(output));
        generator.addProvider(event.includeServer(), new GlobalLootModifiersGenerator(output));
        BWGBiomeModifiers.init();
        DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, BUILDER, Set.of(BiomesWeveGone.MOD_ID));
        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);
        TagsGenerator.init(generator, event.includeServer(), output, datapackBuiltinEntriesProvider.getRegistryProvider(), existingFileHelper);
        generator.addProvider(event.includeServer(), new ForgeAdvancementProvider(output, lookupProvider, existingFileHelper, ImmutableList.of(new AdvancementGenerator())));
        generator.addProvider(event.includeClient(), new ParticleDescriptionGenerator(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new SoundDefinitionsGenerator(output, existingFileHelper));
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, configuredFeatureHolderGetter -> ConfiguredFeaturesUtil.CONFIGURED_FEATURES_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> configuredFeatureHolderGetter.register(biomeResourceKey, biomeFactory.generate(configuredFeatureHolderGetter)))))
            .add(Registries.PLACED_FEATURE, pContext -> PlacedFeaturesUtil.PLACED_FEATURE_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.CONFIGURED_FEATURE)))))
            .add(Registries.BIOME, biomeBootstapContext ->  BWGBiomes.BIOME_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> biomeBootstapContext.register(biomeResourceKey, biomeFactory.generate(biomeBootstapContext.lookup(Registries.PLACED_FEATURE), biomeBootstapContext.lookup(Registries.CONFIGURED_CARVER))))))
            .add(Registries.TEMPLATE_POOL, context -> BWGTemplatePools.TEMPLATE_POOL_FACTORIES.forEach((templatePoolResourceKey, templatePoolFactory) -> context.register(templatePoolResourceKey, templatePoolFactory.generate(context))))
            .add(Registries.STRUCTURE, context -> BWGStructures.STRUCTURE_FACTORIES.forEach((structureResourceKey, structureFactory) -> context.register(structureResourceKey, structureFactory.generate(context))))
            .add(Registries.STRUCTURE_SET, context -> BWGStructureSets.STRUCTURE_SET_FACTORIES.forEach((structureSetResourceKey, structureSetFactory) -> context.register(structureSetResourceKey, structureSetFactory.generate(context.lookup(Registries.STRUCTURE)))))
            .add(Registries.PROCESSOR_LIST, pContext -> BWGStructureProcessorLists.STRUCTURE_PROCESSOR_LIST_FACTORIES.forEach((structureProcessorListResourceKey, processorListFactory) -> pContext.register(structureProcessorListResourceKey, processorListFactory.generate(pContext.lookup(Registries.PROCESSOR_LIST)))))
            .add(Registries.DAMAGE_TYPE, pContext -> BWGDamageTypes.DAMAGE_TYPE_FACTORIES.forEach(((damageTypeResourceKey, damageTypeFactory) -> pContext.register(damageTypeResourceKey, damageTypeFactory.generate(pContext)))))
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, pContext -> BWGBiomeModifiers.BIOME_MODIFIERS_FACTORIES.forEach((id, modifier) -> pContext.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, id), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(Arrays.stream(modifier.biomes()).map(biome -> pContext.lookup(Registries.BIOME).getOrThrow(biome)).collect(Collectors.toList())),
                    HolderSet.direct(pContext.lookup(Registries.PLACED_FEATURE).getOrThrow(modifier.feature())),
                    modifier.step()
            ))));
}
