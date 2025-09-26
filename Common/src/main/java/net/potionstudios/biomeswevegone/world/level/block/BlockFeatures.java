package net.potionstudios.biomeswevegone.world.level.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.*;
import net.potionstudios.biomeswevegone.world.level.block.plants.cactus.BWGCactusBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.BWGDoublePlantBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.FlatVegetationBlock;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.BiConsumer;

/**
 * Block Features that need to be added separately from registration
 * @author Joseph T. McQuigg
 */
public class BlockFeatures {

    public static void registerCompostables(BiConsumer<ItemLike, Float> consumer) {
        BWGBlocks.BLOCKS.forEach(object -> {
            Block block = object.get();
            if (block instanceof TallGrassBlock || block instanceof BWGDoublePlantBlock || block instanceof PinkPetalsBlock)
                consumer.accept(block, 0.3F);
            else if (block instanceof VineBlock || block instanceof DesertPlantBlock || block instanceof BWGCactusBlock)
                consumer.accept(block, 0.5F);
            else if (block instanceof FlowerBlock || block instanceof TallFlowerBlock || block instanceof WaterlilyBlock || block instanceof MushroomBlock || block instanceof FlatVegetationBlock || block instanceof BWGPlacementBushBlock || block instanceof PumpkinBlock || block instanceof CarvedPumpkinBlock || block instanceof SporeBlossomBlock)
                consumer.accept(block, 0.65F);
            else if (block instanceof HugeMushroomBlock || block instanceof HayBlock)
                consumer.accept(block, 0.85F);
        });
        compostItems(consumer, 0.4F, BWGBlocks.CATTAIL_THATCH_SLAB.get(), BWGBlocks.CATTAIL_THATCH_STAIRS.get(), BWGBlocks.CATTAIL_THATCH_CARPET.get());

        BWGWood.WOOD.forEach(entry -> {
            if (entry.get() instanceof LeavesBlock || entry.get() instanceof SaplingBlock || entry.get() instanceof MangroveRootsBlock)
                consumer.accept(entry.get(), 0.3F);
        });
        compostItems(consumer, 0.85F, BWGBlocks.CYAN_PITCHER_PLANT.get(), BWGBlocks.MAGENTA_PITCHER_PLANT.get());

        compostItems(consumer, 0.3F, BWGItems.PALE_PUMPKIN_SEEDS.get(), BWGItems.BLUEBERRIES.get(), BWGBlocks.WITCH_HAZEL_BRANCH.get());
        compostItems(consumer, 0.5f, BWGItems.BLUE_GLOWCANE_SHOOT.get(), BWGItems.GREEN_GLOWCANE_SHOOT.get(), BWGItems.RED_GLOWCANE_SHOOT.get(),
                BWGItems.YELLOW_GLOWCANE_SHOOT.get(), BWGItems.CATTAIL_SPROUT.get(), BWGItems.FLUORESCENT_CATTAIL_SPROUT.get());
        compostItems(consumer, 0.65f, BWGItems.GREEN_APPLE.get(), BWGItems.BAOBAB_FRUIT.get(), BWGItems.SOUL_FRUIT.get(), BWGItems.YUCCA_FRUIT.get(),
                BWGItems.ODDION_BULB.get(), BWGItems.WHITE_PUFFBALL_SPORES.get(), BWGItems.WHITE_PUFFBALL_CAP.get());
        compostItems(consumer, 0.75f, BWGItems.COOKED_YUCCA_FRUIT.get(), BWGItems.COOKED_ODDION_BULB.get(), BWGItems.COOKED_WHITE_PUFFBALL_CAP.get(),
                BWGItems.WREATH.get(), BWGItems.HOLLY_WREATH.get(), BWGItems.PETAL_WREATH.get(), BWGItems.ROSY_WREATH.get(), BWGItems.WINTER_ROSY_WREATH.get(), BWGItems.ODDION_WREATH.get(), BWGItems.MUSHROOM_WREATH.get());
        compostItems(consumer, 1f, BWGItems.GREEN_APPLE_PIE.get(), BWGItems.BLUEBERRY_PIE.get());
    }

    private static void compostItems(BiConsumer<ItemLike, Float> consumer, float chance, ItemLike... items) {
        for (ItemLike item : items) consumer.accept(item, chance);
    }

    /**
     * Register a block as flammable.
     **/
    public static void registerFlammable(TriConsumer<Block, Integer, Integer> consumer) {
        BWGWoodSet.woodsets().forEach(set -> {
            consumer.accept(set.planks(), 5, 20);
            consumer.accept(set.slab(), 5, 20);
            consumer.accept(set.stairs(), 5, 20);
            consumer.accept(set.fence(), 5, 20);
            consumer.accept(set.fenceGate(), 5, 20);
            consumer.accept(set.logstem(), 5, 5);
            consumer.accept(set.strippedLogStem(), 5, 5);
            consumer.accept(set.wood(), 5, 5);
            consumer.accept(set.strippedWood(), 5, 5);
            consumer.accept(set.bookshelf(), 30, 20);
        });
        BWGWood.WOOD.forEach(block -> {
            if (block.get() instanceof LeavesBlock)
                consumer.accept(block.get(), 30, 60);
            else if (block.get() instanceof MangroveRootsBlock)
                consumer.accept(block.get(), 5, 20);
        });
        consumer.accept(BWGWood.PALO_VERDE_LOG.get(), 5, 5);
        consumer.accept(BWGWood.STRIPPED_PALO_VERDE_LOG.get(), 5, 5);
        consumer.accept(BWGWood.PALO_VERDE_WOOD.get(), 5, 5);
        consumer.accept(BWGWood.STRIPPED_PALO_VERDE_WOOD.get(), 5, 5);
        consumer.accept(BWGWood.IMBUED_BLUE_ENCHANTED_WOOD.get(), 5, 5);
        consumer.accept(BWGWood.IMBUED_GREEN_ENCHANTED_WOOD.get(), 5, 5);
        BWGBlocks.BLOCKS.forEach(entry -> {
            Block block = entry.get();
            if (block instanceof LeavesBlock)
                consumer.accept(block, 30, 60);
            else if (block instanceof SweetBerryBushBlock || block instanceof FlowerBlock || block instanceof TallFlowerBlock || block instanceof TallGrassBlock || block instanceof PinkPetalsBlock || block instanceof FlatVegetationBlock || block instanceof SporeBlossomBlock)
                consumer.accept(block, 60, 100);
            else if (block instanceof FloweringBushBlock || block instanceof FlowerableBushBlock || block instanceof ShrubBlock || block instanceof VineBlock)
                consumer.accept(block, 15, 100);
        });
        consumer.accept(BWGBlocks.HYDRANGEA_HEDGE.get(), 60, 100);
        consumer.accept(BWGBlocks.HYDRANGEA_BUSH.getBlock(), 60, 100);
        consumer.accept(BWGBlocks.CATTAIL_THATCH.get(), 60, 20);
        consumer.accept(BWGBlocks.CATTAIL_THATCH_SLAB.get(), 60, 20);
        consumer.accept(BWGBlocks.CATTAIL_THATCH_STAIRS.get(), 60, 20);
        consumer.accept(BWGBlocks.CATTAIL_THATCH_CARPET.get(), 60, 20);
		consumer.accept(BWGBlocks.WITCH_HAZEL_BRANCH.get(), 30, 60);
    }

    public static void registerFurnaceFuels(BiConsumer<ItemLike, Integer> consumer) {
        consumer.accept(BWGBlocks.PEAT.get().asItem(), 1200);
        consumer.accept(BWGBlocks.CATTAIL_THATCH.get().asItem(), 300);
        consumer.accept(BWGBlocks.CATTAIL_THATCH_SLAB.get().asItem(), 150);
        consumer.accept(BWGBlocks.CATTAIL_THATCH_STAIRS.get().asItem(), 300);
        consumer.accept(BWGWood.SPIRIT_ROOTS.get(), 300);
        BWGWoodSet.woodsets().forEach(bwgWoodSet -> {
            consumer.accept(bwgWoodSet.bookshelf(), 300);
            consumer.accept(bwgWoodSet.craftingTable(), 300);
        });
        consumer.accept(BWGBlocks.FORAGERS_TABLE.get(), 300);
        consumer.accept(BWGBlocks.WITCH_HAZEL_BRANCH.get().asItem(), 100);
    }
}