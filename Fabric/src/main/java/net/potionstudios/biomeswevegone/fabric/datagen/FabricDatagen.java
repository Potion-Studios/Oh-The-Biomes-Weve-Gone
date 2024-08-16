package net.potionstudios.biomeswevegone.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.tags.BWGBiomeTags;
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.sand.BWGSandSet;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;

import java.util.concurrent.CompletableFuture;

/**
 * DataGeneratorEntrypoint for Fabric
 * @see DataGeneratorEntrypoint
 * @author Joseph T. McQuigg
 */
public class FabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        FabricTagProvider.BlockTagProvider blockProvider = pack.addProvider(FabricBlockTagGenerator::new);
        pack.addProvider((output, completableFuture) -> new FabricItemTagGenerator(output, completableFuture, blockProvider));
        pack.addProvider(FabicEntityTagGenerator::new);
        pack.addProvider(FabricBiomeTagGenerator::new);
    }

    private static class FabricBlockTagGenerator extends FabricTagProvider.BlockTagProvider {

        public FabricBlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            BWGWoodSet.woodsets().forEach(set -> getOrCreateTagBuilder(ConventionalBlockTags.BOOKSHELVES).add(set.bookshelf()));
            BWGSandSet.getSandSets().forEach(set -> {
                getOrCreateTagBuilder(ConventionalBlockTags.SANDSTONE_BLOCKS).forceAddTag(set.getSandstoneBlocksTag());
                getOrCreateTagBuilder(ConventionalBlockTags.SANDSTONE_SLABS).forceAddTag(set.getSandstoneSlabsTag());
                getOrCreateTagBuilder(ConventionalBlockTags.SANDSTONE_STAIRS).forceAddTag(set.getSandstoneStairsTag());
            });
            getOrCreateTagBuilder(ConventionalBlockTags.VILLAGER_JOB_SITES).add(BWGBlocks.FORAGERS_TABLE.get());
            getOrCreateTagBuilder(ConventionalBlockTags.BUDDING_BLOCKS).add(BWGWood.IMBUED_BLUE_ENCHANTED_WOOD.get(), BWGWood.IMBUED_GREEN_ENCHANTED_WOOD.get());
        }
    }

    private static class FabricItemTagGenerator extends FabricTagProvider.ItemTagProvider {

        public FabricItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, FabricTagProvider.BlockTagProvider blockTags) {
            super(output, registriesFuture, blockTags);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            copy(ConventionalBlockTags.BOOKSHELVES, ConventionalItemTags.BOOKSHELVES);
            copy(ConventionalBlockTags.SANDSTONE_BLOCKS, ConventionalItemTags.SANDSTONE_BLOCKS);
            copy(ConventionalBlockTags.SANDSTONE_SLABS, ConventionalItemTags.SANDSTONE_SLABS);
            copy(ConventionalBlockTags.SANDSTONE_STAIRS, ConventionalItemTags.SANDSTONE_STAIRS);
            copy(ConventionalBlockTags.VILLAGER_JOB_SITES, ConventionalItemTags.VILLAGER_JOB_SITES);
            copy(ConventionalBlockTags.BUDDING_BLOCKS, ConventionalItemTags.BUDDING_BLOCKS);
            BWGItems.ITEMS.stream()
                    .filter(item -> item.get().isEdible())
                    .forEach(item -> getOrCreateTagBuilder(ConventionalItemTags.FOODS).add(item.get()));

            getOrCreateTagBuilder(ConventionalItemTags.ENTITY_WATER_BUCKETS).add(BWGItems.MAN_O_WAR_BUCKET.get());
        }
    }

    private static class FabicEntityTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

        public FabicEntityTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(ConventionalEntityTypeTags.BOATS).add(BWGEntities.BWG_BOAT.get(), BWGEntities.BWG_CHEST_BOAT.get());
        }
    }

    private static class FabricBiomeTagGenerator extends FabricTagProvider<Biome> {

        /**
         * Constructs a new {@link FabricTagProvider} with the default computed path.
         *
         * <p>Common implementations of this class are provided.
         *
         * @param output           the {@link FabricDataOutput} instance
         * @param registriesFuture the backing registry for the tag type
         */
        public FabricBiomeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, Registries.BIOME, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(ConventionalBiomeTags.IN_OVERWORLD).forceAddTag(BWGBiomeTags.OVERWORLD);

            getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_HOT).forceAddTag(BWGBiomeTags.HOT);
            getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE).forceAddTag(BWGBiomeTags.TEMPERATE);
            getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_COLD).forceAddTag(BWGBiomeTags.COLD);

            getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_WET).forceAddTag(BWGBiomeTags.WET);
            getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_DRY).forceAddTag(BWGBiomeTags.DRY);

            getOrCreateTagBuilder(ConventionalBiomeTags.VEGETATION_SPARSE).forceAddTag(BWGBiomeTags.SPARSE);
            getOrCreateTagBuilder(ConventionalBiomeTags.VEGETATION_DENSE).forceAddTag(BWGBiomeTags.DENSE);

            getOrCreateTagBuilder(ConventionalBiomeTags.PLAINS).forceAddTag(BWGBiomeTags.PLAINS);
            getOrCreateTagBuilder(ConventionalBiomeTags.FOREST).forceAddTag(BWGBiomeTags.FOREST);
            getOrCreateTagBuilder(ConventionalBiomeTags.TAIGA).forceAddTag(BWGBiomeTags.TAIGA);
            getOrCreateTagBuilder(ConventionalBiomeTags.DESERT).forceAddTag(BWGBiomeTags.DESERT);
            getOrCreateTagBuilder(ConventionalBiomeTags.SAVANNA).forceAddTag(BWGBiomeTags.SAVANNA);
            getOrCreateTagBuilder(ConventionalBiomeTags.JUNGLE).forceAddTag(BWGBiomeTags.JUNGLE);
            getOrCreateTagBuilder(ConventionalBiomeTags.BEACH).forceAddTag(BWGBiomeTags.BEACH);
            getOrCreateTagBuilder(ConventionalBiomeTags.OCEAN).forceAddTag(BWGBiomeTags.OCEAN);
            getOrCreateTagBuilder(ConventionalBiomeTags.SNOWY).forceAddTag(BWGBiomeTags.SNOWY);
            getOrCreateTagBuilder(ConventionalBiomeTags.MOUNTAIN).forceAddTag(BWGBiomeTags.MOUNTAIN);
            getOrCreateTagBuilder(ConventionalBiomeTags.BADLANDS).forceAddTag(BWGBiomeTags.BADLANDS);
            getOrCreateTagBuilder(ConventionalBiomeTags.SWAMP).forceAddTag(BWGBiomeTags.SWAMP);
            getOrCreateTagBuilder(ConventionalBiomeTags.MOUNTAIN_SLOPE).forceAddTag(BWGBiomeTags.SLOPE);
            getOrCreateTagBuilder(ConventionalBiomeTags.MOUNTAIN_PEAK).forceAddTag(BWGBiomeTags.PEAK);
            getOrCreateTagBuilder(ConventionalBiomeTags.FLORAL).forceAddTag(BWGBiomeTags.FLORAL);
            getOrCreateTagBuilder(ConventionalBiomeTags.TREE_CONIFEROUS).forceAddTag(BWGBiomeTags.CONIFEROUS);
            getOrCreateTagBuilder(ConventionalBiomeTags.DEAD).forceAddTag(BWGBiomeTags.DEAD);
            getOrCreateTagBuilder(ConventionalBiomeTags.WASTELAND).forceAddTag(BWGBiomeTags.WASTELAND);
            getOrCreateTagBuilder(ConventionalBiomeTags.WINDSWEPT).forceAddTag(BWGBiomeTags.WINDSWEPT);
        }
    }
}
