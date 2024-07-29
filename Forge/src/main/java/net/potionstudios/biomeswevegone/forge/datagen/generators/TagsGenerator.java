package net.potionstudios.biomeswevegone.forge.datagen.generators;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.tags.BWGBiomeTags;
import net.potionstudios.biomeswevegone.tags.BWGBlockTags;
import net.potionstudios.biomeswevegone.tags.BWGItemTags;
import net.potionstudios.biomeswevegone.tags.BWGStructureTags;
import net.potionstudios.biomeswevegone.world.damagesource.BWGDamageTypes;
import net.potionstudios.biomeswevegone.world.entity.ai.village.poi.BWGPoiTypes;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.sand.BWGSandSet;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sereneseasons.init.ModTags;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Used to generate tags for blocks and items.
 * @author Joseph T. McQuigg
 */
public class TagsGenerator {

    public static void init(DataGenerator generator, boolean run, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        BlockTagGenerator BlockTags = generator.addProvider(run, new BlockTagGenerator(output, lookupProvider, helper));
        generator.addProvider(run, new ItemTagGenerator(output, lookupProvider, BlockTags, helper));
        generator.addProvider(run, new BiomeTagGenerator(output, lookupProvider, helper));
        generator.addProvider(run, new StructureTagGenerator(output, lookupProvider, helper));
        generator.addProvider(run, new PoiTagGenerator(output, lookupProvider, helper));
        generator.addProvider(run, new DamageTypeTagGenerator(output, lookupProvider, helper));
    }

    public static void sortTagsAlphabeticallyAndRemoveDuplicateTagEntries(Map<?, TagBuilder> tags) {
        for (TagBuilder value : tags.values()) {
            List<TagEntry> builderEntries = value.entries;

            Set<TagEntry> noDuplicates = new HashSet<>(builderEntries);
            builderEntries.clear();
            builderEntries.addAll(noDuplicates);
            builderEntries.sort(Comparator.comparing(TagEntry::toString));
        }
    }


    /**
     * Used to generate tags for blocks.
     * @see BlockTagsProvider
     */
    private static class BlockTagGenerator extends BlockTagsProvider {
        private BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, BiomesWeveGone.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            BWGBlocks.BLOCKS.forEach(block -> easyBlockTags(block.get()));
            BWGWoodSet.woodsets().forEach(set -> {
                tag(BlockTags.PLANKS).add(set.planks());
                tag(BlockTags.WOODEN_SLABS).add(set.slab());
                tag(BlockTags.WOODEN_STAIRS).add(set.stairs());
                tag(BlockTags.WOODEN_BUTTONS).add(set.button());
                tag(BlockTags.WOODEN_PRESSURE_PLATES).add(set.pressurePlate());
                tag(BlockTags.WOODEN_TRAPDOORS).add(set.trapdoor());
                tag(BlockTags.WOODEN_DOORS).add(set.door());
                tag(BlockTags.WOODEN_FENCES).add(set.fence());
                tag(BlockTags.FENCE_GATES).add(set.fenceGate());
                tag(BlockTags.STANDING_SIGNS).add(set.sign());
                tag(BlockTags.WALL_SIGNS).add(set.wallSign());
                tag(BlockTags.CEILING_HANGING_SIGNS).add(set.hangingSign());
                tag(BlockTags.WALL_HANGING_SIGNS).add(set.wallHangingSign());
                tag(Tags.Blocks.BOOKSHELVES).add(set.bookshelf());
                tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(set.bookshelf());
                if (set.leaves() != null) tag(BlockTags.LEAVES).add(set.leaves());
                tag(set.logBlockTag()).add(set.logstem(), set.wood(), set.strippedLogStem(), set.strippedWood());
                tag(BlockTags.LOGS).addTag(set.logBlockTag());
                tag(BlockTags.LOGS_THAT_BURN).addTag(set.logBlockTag());
            });

            tag(BlockTags.LEAVES).add(BWGWood.PALO_VERDE_LEAVES.get());
            tag(BWGBlockTags.PALO_VERDE_LOGS).add(BWGWood.PALO_VERDE_LOG.get(), BWGWood.STRIPPED_PALO_VERDE_LOG.get(), BWGWood.PALO_VERDE_WOOD.get(), BWGWood.STRIPPED_PALO_VERDE_WOOD.get());
            tag(BlockTags.LOGS).addTag(BWGBlockTags.PALO_VERDE_LOGS);

            BWGWood.WOOD.forEach(blocks -> {
                if (blocks.get() instanceof FlowerPotBlock)
                    tag(BlockTags.FLOWER_POTS).add(blocks.get());
                else if (blocks.get() instanceof SaplingBlock)
                    tag(BlockTags.SAPLINGS).add(blocks.get());
                else tag(BlockTags.MINEABLE_WITH_AXE).add(blocks.get());
            });
            BWGSandSet.getSandSets().forEach(set -> {
                tag(set.getSandstoneBlocksTag()).add(set.getSandstone(), set.getChiseledSandstone(), set.getCutSandstone(), set.getSmoothSandstone());
                tag(set.getSandBlockTag()).add(set.getSand());
                tag(Tags.Blocks.SANDSTONE).addTag(set.getSandstoneBlocksTag());
                tag(Tags.Blocks.SAND).addTag(set.getSandBlockTag());
            });
            tag(BWGBlockTags.BLACK_ICE).add(BWGBlocks.BLACK_ICE.get(), BWGBlocks.PACKED_BLACK_ICE.get());
            tag(BWGBlockTags.BOREALIS_ICE).add(BWGBlocks.BOREALIS_ICE.get(), BWGBlocks.PACKED_BOREALIS_ICE.get());
            tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON).addTag(BWGBlockTags.BLACK_ICE).addTag(BWGBlockTags.BOREALIS_ICE);
            tag(BlockTags.ICE).addTag(BWGBlockTags.BLACK_ICE).addTag(BWGBlockTags.BOREALIS_ICE);
            //tag(Tags.Blocks.GLASS).add(BWGBlocks.THERIUM_GLASS.get());
            //tag(Tags.Blocks.GLASS_PANES).add(BWGBlocks.THERIUM_GLASS_PANE.get());
            tag(BlockTags.FROG_PREFER_JUMP_TO).add(BWGBlocks.FLOWERING_TINY_LILY_PADS.get(), BWGBlocks.TINY_LILY_PADS.get());
            tag(BlockTags.WOOL_CARPETS).add(BWGBlocks.CATTAIL_THATCH_CARPET.get());
            tag(BlockTags.MUSHROOM_GROW_BLOCK).add(BWGBlocks.PODZOL_DACITE.get());
            tag(BWGBlockTags.SCORCHED_PLANT_PLACEABLE).addTag(BlockTags.NYLIUM).addTag(BlockTags.BASE_STONE_NETHER).add(Blocks.SOUL_SAND, Blocks.MYCELIUM).addOptionalTag(Tags.Blocks.END_STONES);
            tag(BWGBlockTags.SNOWY_PLANT_PLACEABLE).add(Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW).addTag(BlockTags.DIRT);
            tag(BWGBlockTags.ODDITY_PLANT_PLACEABLE).add(Blocks.END_STONE, Blocks.END_STONE_BRICKS).addOptionalTag(Tags.Blocks.END_STONES);
            //tag(BWGBlockTags.WARPED_BUSH_PLACEABLE).add(BWGBlocks.WARPED_SOUL_SAND.get(), BWGBlocks.WARPED_SOUL_SOIL.get());
            //tag(BWGBlockTags.WARPED_CORAL_PLACEABLE).add(BWGBlocks.WARPED_SOUL_SAND.get(), BWGBlocks.WARPED_SOUL_SOIL.get());
            tag(BWGBlockTags.BWG_MUSHROOM_PLACEABLE).addTag(BlockTags.MUSHROOM_GROW_BLOCK).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.DIRT);
            tag(BWGBlockTags.HYDRANGEA_BUSH_PLACEABLE).addTag(BlockTags.DIRT);
            tag(BlockTags.DIRT).add(BWGBlocks.LUSH_GRASS_BLOCK.get(), BWGBlocks.SANDY_DIRT.get(), BWGBlocks.LUSH_DIRT.get(), BWGBlocks.PEAT.get(), BWGBlocks.OVERGROWN_DACITE.get(), BWGBlocks.OVERGROWN_STONE.get(), BWGBlocks.PODZOL_DACITE.get());
            tag(BlockTags.CLIMBABLE).add(BWGBlocks.SKYRIS_VINE.get());
            tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
                    .add(BWGBlocks.OVERGROWN_DACITE.get(), BWGBlocks.OVERGROWN_STONE.get(), BWGBlocks.PODZOL_DACITE.get(), //BWGBlocks.SOAPSTONE_SET.getBase(),
                            BWGBlocks.ROCKY_STONE_SET.getBase(), BWGBlocks.MOSSY_STONE_SET.getBase(), BWGBlocks.LUSH_DIRT.get(), BWGBlocks.SANDY_DIRT.get(), BWGBlocks.PEAT.get());
            tag(BlockTags.CROPS).add(BWGBlocks.ODDION_CROP.get());
            tag(BlockTags.BEE_GROWABLES).add(BWGBlocks.BLUEBERRY_BUSH.get());
            tag(BlockTags.SNIFFER_DIGGABLE_BLOCK).add(BWGBlocks.LUSH_DIRT_PATH.get(), BWGBlocks.SANDY_DIRT.get(), BWGBlocks.SANDY_DIRT_PATH.get(), BWGBlocks.LUSH_DIRT.get(), BWGBlocks.LUSH_GRASS_BLOCK.get(), BWGBlocks.PEAT.get());
            tag(BlockTags.SNIFFER_EGG_HATCH_BOOST).add(BWGBlocks.LUSH_GRASS_BLOCK.get());
            tag(BlockTags.MAINTAINS_FARMLAND).add(BWGBlocks.ODDION_CROP.get());
            tag(BlockTags.MINEABLE_WITH_HOE).add(BWGBlocks.ALLIUM_PETAL_BLOCK.get(), BWGBlocks.PINK_ALLIUM_PETAL_BLOCK.get(), BWGBlocks.WHITE_ALLIUM_PETAL_BLOCK.get(), BWGBlocks.ROSE_PETAL_BLOCK.get());
            tag(BlockTags.REPLACEABLE).add(BWGBlocks.PRAIRIE_GRASS.get(), BWGBlocks.TALL_PRAIRIE_GRASS.get(), BWGBlocks.BEACH_GRASS.get(), BWGBlocks.TALL_BEACH_GRASS.get(), BWGBlocks.SKYRIS_VINE.get());
            sortTagsAlphabeticallyAndRemoveDuplicateTagEntries(this.builders);
        }

        private void easyBlockTags(Block object) {
            if (object instanceof SlabBlock) tag(BlockTags.SLABS).add(object);
            else if (object instanceof StairBlock) tag(BlockTags.STAIRS).add(object);
            else if (object instanceof WallBlock) tag(BlockTags.WALLS).add(object);
            else if (object instanceof SandBlock) tag(BlockTags.SAND).add(object);
            else if (object instanceof FlowerBlock) tag(BlockTags.SMALL_FLOWERS).add(object);
            else if (object instanceof TallFlowerBlock) tag(BlockTags.TALL_FLOWERS).add(object);
            else if (object instanceof LeavesBlock) tag(BlockTags.LEAVES).add(object);
            else if (object instanceof CampfireBlock) tag(BlockTags.CAMPFIRES).add(object);
            else if (object instanceof FlowerPotBlock) tag(BlockTags.FLOWER_POTS).add(object);
            //else if (object instanceof ButtonBlock) tag(BlockTags.BUTTONS).add(object);
            //else if (object instanceof PressurePlateBlock) tag(BlockTags.PRESSURE_PLATES).add(object);
            SoundType type = object.defaultBlockState().getSoundType();
            if (type == SoundType.STONE || type == SoundType.DEEPSLATE)
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(object);
            else if (type == SoundType.WOOD || type == SoundType.SWEET_BERRY_BUSH)
                tag(BlockTags.MINEABLE_WITH_AXE).add(object);
            else if (type == SoundType.GRAVEL || type == SoundType.SAND || type == SoundType.SNOW || type == SoundType.GRASS)
                if (object instanceof LeavesBlock) tag(BlockTags.MINEABLE_WITH_HOE).add(object);
                else tag(BlockTags.MINEABLE_WITH_SHOVEL).add(object);
        }
    }

    /**
     * Used to generate tags for items.
     * @see ItemTagsProvider
     */
    private static class ItemTagGenerator extends ItemTagsProvider {

        private ItemTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, BlockTagGenerator blockTagGenerator, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, blockTagGenerator.contentsGetter(), BiomesWeveGone.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            BWGWoodSet.woodsets().forEach(set -> {
                copy(set.logBlockTag(), set.logItemTag());
                if (set.boatItem() != null) tag(ItemTags.BOATS).add(set.boatItem().get());
                if (set.chestBoatItem() != null) tag(ItemTags.CHEST_BOATS).add(set.chestBoatItem().get());
            });
            copy(BlockTags.SLABS, ItemTags.SLABS);
            copy(BlockTags.STAIRS, ItemTags.STAIRS);
            copy(BlockTags.WALLS, ItemTags.WALLS);
            //copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
            copy(BlockTags.PLANKS, ItemTags.PLANKS);
            copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
            copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
            copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
            copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
            copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
            copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
            copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
            copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
            copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
            copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
            copy(Tags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES);
            copy(BlockTags.LOGS, ItemTags.LOGS);
            copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
            copy(BlockTags.SAND, ItemTags.SAND);
            copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
            copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
            copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            copy(BlockTags.LEAVES, ItemTags.LEAVES);
            copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
            BWGSandSet.getSandSets().forEach(set -> {
                copy(set.getSandstoneBlocksTag(), set.getSandstoneBlocksItemTag());
                copy(set.getSandBlockTag(), set.getSandItemTag());
            });
            copy(Tags.Blocks.SANDSTONE, Tags.Items.SANDSTONE);
            copy(Tags.Blocks.SAND, Tags.Items.SAND);
            copy(BWGBlockTags.BLACK_ICE, BWGItemTags.BLACK_ICE);
            copy(BWGBlockTags.BOREALIS_ICE, BWGItemTags.BOREALIS_ICE);
            //copy(Tags.Blocks.GLASS, Tags.Items.GLASS);
            //copy(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES);

            BWGBlocks.BLOCKS.stream().filter(entry -> entry.get() instanceof FlowerBlock).forEach(
                    entry -> {
                        FlowerBlock block = (FlowerBlock) entry.get();
                        if (block.defaultMapColor() == MapColor.COLOR_BLACK)
                            tag(BWGItemTags.MAKES_BLACK_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_BLUE)
                            tag(BWGItemTags.MAKES_BLUE_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_BROWN)
                            tag(BWGItemTags.MAKES_BROWN_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_CYAN)
                            tag(BWGItemTags.MAKES_CYAN_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_GRAY)
                            tag(BWGItemTags.MAKES_GRAY_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_GREEN)
                            tag(BWGItemTags.MAKES_GREEN_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_LIGHT_BLUE)
                            tag(BWGItemTags.MAKES_LIGHT_BLUE_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_LIGHT_GRAY)
                            tag(BWGItemTags.MAKES_LIGHT_GRAY_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_MAGENTA)
                            tag(BWGItemTags.MAKES_MAGENTA_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_ORANGE)
                            tag(BWGItemTags.MAKES_ORANGE_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_PINK)
                            tag(BWGItemTags.MAKES_PINK_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_PURPLE)
                            tag(BWGItemTags.MAKES_PURPLE_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_RED)
                            tag(BWGItemTags.MAKES_RED_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.TERRACOTTA_WHITE)
                            tag(BWGItemTags.MAKES_WHITE_DYE).add(block.asItem());
                        else if (block.defaultMapColor() == MapColor.COLOR_YELLOW)
                            tag(BWGItemTags.MAKES_YELLOW_DYE).add(block.asItem());
                    }
            );
            copy(BWGBlockTags.PALO_VERDE_LOGS, BWGItemTags.PALO_VERDE_LOGS);
            tag(BWGItemTags.SHEARS).addOptionalTag(Tags.Items.SHEARS).addOptionalTag(new ResourceLocation("c", "shears"));
            tag(ItemTags.MUSIC_DISCS).add(BWGItems.MUSIC_DISC_PIXIE_CLUB.get());
            tag(ItemTags.STONE_CRAFTING_MATERIALS).add(BWGBlocks.ROCKY_STONE_SET.getBase().asItem());
            sortTagsAlphabeticallyAndRemoveDuplicateTagEntries(this.builders);
        }
    }


    private static class BiomeTagGenerator extends BiomeTagsProvider {
        private BiomeTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, BiomesWeveGone.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            BWGBiomes.BIOMES_BY_TAG.forEach((tag, biome) -> tag(tag).add(biome));

            //tag(Tags.Biomes.IS_HOT_OVERWORLD).addOptionalTag(BWGBiomeTags.HOT_OVERWORLD);
            tag(Tags.Biomes.IS_WET_OVERWORLD).addOptionalTag(BWGBiomeTags.WET_OVERWORLD);
            //tag(Tags.Biomes.IS_DRY_OVERWORLD).addOptionalTag(BWGBiomeTags.DRY_OVERWORLD);
            tag(Tags.Biomes.IS_COLD_OVERWORLD).addOptionalTag(BWGBiomeTags.COLD_OVERWORLD);
            //tag(Tags.Biomes.IS_SPARSE_OVERWORLD).addOptionalTag(BWGBiomeTags.SPARSE_OVERWORLD);
            tag(Tags.Biomes.IS_DENSE_OVERWORLD).addOptionalTag(BWGBiomeTags.DENSE_OVERWORLD);

            tag(Tags.Biomes.IS_PLAINS).addOptionalTag(BWGBiomeTags.PLAINS);
            tag(BiomeTags.IS_FOREST).addOptionalTag(BWGBiomeTags.FOREST);
            tag(BiomeTags.IS_TAIGA).addOptionalTag(BWGBiomeTags.TAIGA);
            tag(Tags.Biomes.IS_DESERT).addOptionalTag(BWGBiomeTags.DESERT);
            tag(BiomeTags.IS_SAVANNA).addOptionalTag(BWGBiomeTags.SAVANNA);
            tag(BiomeTags.IS_JUNGLE).addOptionalTag(BWGBiomeTags.JUNGLE);
            tag(BiomeTags.IS_MOUNTAIN).addOptionalTag(BWGBiomeTags.MOUNTAIN);
            tag(Tags.Biomes.IS_MOUNTAIN).addOptionalTag(BWGBiomeTags.MOUNTAIN);
            tag(BiomeTags.IS_BEACH).addOptionalTag(BWGBiomeTags.BEACH);
            tag(BiomeTags.IS_BADLANDS).addOptionalTag(BWGBiomeTags.BADLANDS);
            tag(Tags.Biomes.IS_SWAMP).addOptionalTag(BWGBiomeTags.SWAMP);
            tag(Tags.Biomes.IS_SANDY).addOptionalTag(BWGBiomeTags.SANDY);
            //tag(Tags.Biomes.IS_CAVE).addTag(BWGBiomeTags.CAVE);
            tag(Tags.Biomes.IS_SNOWY).addOptionalTag(BWGBiomeTags.SNOWY);
            tag(BiomeTags.IS_OCEAN).addOptionalTag(BWGBiomeTags.OCEAN);

            tag(BWGBiomeTags.WET_OVERWORLD).addOptionalTag(BWGBiomeTags.SWAMP);
            tag(BWGBiomeTags.DENSE_OVERWORLD).addOptionalTag(BWGBiomeTags.FOREST).addOptionalTag(BWGBiomeTags.JUNGLE);

            tag(BWGBiomeTags.SHARPENED_ROCKS).add(BWGBiomes.RUGGED_BADLANDS);
            tag(BWGBiomeTags.IRONWOOD_GOUR_PLATEAU).add(BWGBiomes.IRONWOOD_GOUR);
            tag(BWGBiomeTags.LARGE_COLD_LAKE).add(BWGBiomes.MAPLE_TAIGA).add(BWGBiomes.CRIMSON_TUNDRA).add(BWGBiomes.OVERGROWTH_WOODLANDS);

            tag(BWGBiomeTags.VanillaOnlyTags.BEACH).add(Biomes.BEACH);


            /**
             * Structure Location Biome Tags
             */
            tag(BWGBiomeTags.StructureHasTags.HAS_PRAIRIE_HOUSE).addOptional(BWGBiomes.PRAIRIE.location());
            tag(BWGBiomeTags.StructureHasTags.HAS_RUGGED_FOSSIL).addOptional(BWGBiomes.RUGGED_BADLANDS.location());
            tag(BWGBiomeTags.StructureHasTags.HAS_ASPEN_MANOR).addOptional(BWGBiomes.ASPEN_BOREAL.location());
            tag(BWGBiomeTags.StructureHasTags.HAS_VILLAGE_FORGOTTEN).addOptional(BWGBiomes.FORGOTTEN_FOREST.location());
            tag(BWGBiomeTags.StructureHasTags.HAS_VILLAGE_SKYRIS).addOptional(BWGBiomes.SKYRIS_VALE.location());
            tag(BWGBiomeTags.StructureHasTags.HAS_VILLAGE_SALEM).addOptional(BWGBiomes.WEEPING_WITCH_FOREST.location());
            tag(BWGBiomeTags.StructureHasTags.HAS_VILLAGE_RED_ROCK).addOptional(BWGBiomes.RED_ROCK_VALLEY.location());
            tag(BWGBiomeTags.StructureHasTags.HAS_VILLAGE_PUMPKIN_PATCH)
                    .addOptional(BWGBiomes.PUMPKIN_VALLEY.location())
                    .addOptional(BWGBiomes.CIKA_WOODS.location());
            tag(BiomeTags.HAS_SWAMP_HUT).addTag(BWGBiomeTags.SWAMP);
            tag(BiomeTags.HAS_JUNGLE_TEMPLE).addTag(BWGBiomeTags.JUNGLE);
            tag(BiomeTags.HAS_RUINED_PORTAL_OCEAN).addTag(BWGBiomeTags.OCEAN);
            tag(BiomeTags.HAS_SHIPWRECK).addTag(BWGBiomeTags.OCEAN);
            tag(BiomeTags.HAS_OCEAN_RUIN_WARM).addTag(BWGBiomeTags.OCEAN);
            tag(BiomeTags.HAS_VILLAGE_SAVANNA).addTag(BWGBiomeTags.SAVANNA);
            tag(BiomeTags.HAS_PILLAGER_OUTPOST).addTag(BWGBiomeTags.SAVANNA);

            // Serene Seasons Support
            tag(ModTags.Biomes.TROPICAL_BIOMES)
                    .addOptionalTag(BWGBiomeTags.DESERT).addOptionalTag(BWGBiomeTags.JUNGLE);
            tag(ModTags.Biomes.INFERTILE_BIOMES)
                    .addOptional(BWGBiomes.DEAD_SEA.location());
            tag(ModTags.Biomes.BLACKLISTED_BIOMES)
                    .addOptionalTag(BWGBiomeTags.OCEAN);
            sortTagsAlphabeticallyAndRemoveDuplicateTagEntries(this.builders);
        }
    }

    private static class StructureTagGenerator extends StructureTagsProvider {

        private StructureTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, BiomesWeveGone.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(BWGStructureTags.PRAIRIE_HOUSES).add(BWGStructures.PRAIRIE_HOUSE).add(BWGStructures.ABANDONED_PRAIRIE_HOUSE);
            tag(BWGStructureTags.ASPEN_MANORS).add(BWGStructures.ASPEN_MANOR_1).add(BWGStructures.ASPEN_MANOR_2);
            tag(BWGStructureTags.VILLAGE)
                    .add(BWGStructures.FORGOTTEN_VILLAGE)
                    .add(BWGStructures.SKYRIS_VILLAGE)
                    .add(BWGStructures.SALEM_VILLAGE)
                    .add(BWGStructures.RED_ROCK_VILLAGE)
                    .add(BWGStructures.PUMPKIN_PATCH_VILLAGE);
            tag(StructureTags.VILLAGE).addTag(BWGStructureTags.VILLAGE);
            sortTagsAlphabeticallyAndRemoveDuplicateTagEntries(this.builders);
        }
    }

    private static class PoiTagGenerator extends PoiTypeTagsProvider {

        private PoiTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, BiomesWeveGone.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(BWGPoiTypes.FORAGER);
        }
    }

    private static class DamageTypeTagGenerator extends DamageTypeTagsProvider {

        public DamageTypeTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, BiomesWeveGone.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(DamageTypeTags.BYPASSES_ARMOR).add(BWGDamageTypes.IN_QUICKSAND);
            tag(DamageTypeTags.IS_EXPLOSION).add(BWGDamageTypes.CATTAIL_EXPLOSION);
        }
    }

}
