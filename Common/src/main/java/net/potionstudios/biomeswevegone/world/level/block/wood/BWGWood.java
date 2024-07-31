package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.plants.PottedBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.tree.grower.BWGTreeGrowers;
import net.potionstudios.biomeswevegone.world.level.block.plants.tree.sapling.BWGSaplingBlock;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Holds all the wood sets
 *
 * @author Joseph T. McQuigg
 * @see BWGWoodSet
 */
public class BWGWood {

    public static final ArrayList<Supplier<? extends Block>> WOOD = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> WOOD_BLOCK_ITEMS = new ArrayList<>();

    public static final BWGWoodSet ASPEN = new BWGWoodSet("aspen", MapColor.QUARTZ, BWGTreeGrowers.ASPEN);
    public static final BWGWoodSet BAOBAB = new BWGWoodSet("baobab", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.BAOBAB);
    public static final BWGWoodSet BLUE_ENCHANTED = new BWGWoodSet("blue_enchanted", MapColor.COLOR_BLUE, BWGTreeGrowers.BLUE_ENCHANTED, true);
    public static final Supplier<ImbuedBlock> IMBUED_BLUE_ENCHANTED_WOOD = registerBlockItem("imbued_blue_enchanted_wood", () -> new ImbuedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE)));
    //public static final BWGWoodSet BULBIS = new BWGWoodSet("bulbis", MapColor.COLOR_PURPLE, false, false);
    public static final BWGWoodSet CIKA = new BWGWoodSet("cika", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.CIKA);
    public static final BWGWoodSet CYPRESS = new BWGWoodSet("cypress", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.CYPRESS);
    public static final BWGWoodSet EBONY = new BWGWoodSet("ebony", MapColor.COLOR_BLACK, BWGTreeGrowers.EBONY);
    //public static final BWGWoodSet EMBUR = new BWGWoodSet("embur", MapColor.COLOR_BROWN, false, false);
    //public static final BWGWoodSet ETHER = new BWGWoodSet("ether", MapColor.COLOR_LIGHT_BLUE, true, false);
    public static final BWGWoodSet FIR = new BWGWoodSet("fir", MapColor.TERRACOTTA_WHITE,BWGTreeGrowers.FIR);
    public static final BWGWoodSet FLORUS = new BWGWoodSet(new BlockSetType("florus"), MapColor.COLOR_GREEN, BWGWoodSet.LogStem.STEM, null, false, true, false);
    public static final BWGWoodSet GREEN_ENCHANTED = new BWGWoodSet("green_enchanted", MapColor.COLOR_LIGHT_GREEN, BWGTreeGrowers.GREEN_ENCHANTED, true);
    public static final Supplier<ImbuedBlock> IMBUED_GREEN_ENCHANTED_WOOD = registerBlockItem("imbued_green_enchanted_wood", () -> new ImbuedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final BWGWoodSet HOLLY = new BWGWoodSet("holly", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.HOLLY);
    public static final BWGWoodSet IRONWOOD = new BWGWoodSet("ironwood", MapColor.COLOR_GRAY, BWGTreeGrowers.IRONWOOD);
    //public static final BiomesWeveGoneWoodSet IMPARIUS = new BiomesWeveGoneWoodSet(BiomesWeveGoneWoodType.IMPARIUS, MapColor.TERRACOTTA_WHITE);
    public static final BWGWoodSet JACARANDA = new BWGWoodSet("jacaranda", MapColor.COLOR_PINK, BWGTreeGrowers.JACARANDA);
    public static final BWGWoodSet MAHOGANY = new BWGWoodSet("mahogany", MapColor.COLOR_PINK, BWGTreeGrowers.MAHOGANY);
    public static final BWGWoodSet MAPLE = new BWGWoodSet("maple", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.MAPLE);
    //public static final BWGWoodSet NIGHTSHADE = new BWGWoodSet("nightshade", MapColor.COLOR_ORANGE, true, false);
    public static final BWGWoodSet PALM = new BWGWoodSet("palm", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.PALM);
    public static final BWGWoodSet PINE = new BWGWoodSet("pine", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.PINE);
    public static final BWGWoodSet RAINBOW_EUCALYPTUS = new BWGWoodSet("rainbow_eucalyptus", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.RAINBOW_EUCALYPTUS);
    public static final BWGWoodSet REDWOOD = new BWGWoodSet("redwood", MapColor.COLOR_RED, BWGTreeGrowers.REDWOOD);
    public static final BWGWoodSet SAKURA = new BWGWoodSet(BlockSetType.register(new BlockSetType("sakura", true, SoundType.CHERRY_WOOD, SoundEvents.CHERRY_WOOD_DOOR_CLOSE, SoundEvents.CHERRY_WOOD_DOOR_OPEN, SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE, SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON)),
            MapColor.COLOR_RED, null, false, true);
    public static final PottedBlock WHITE_SAKURA_SAPLING = createSapling("white_sakura", BWGTreeGrowers.WHITE_SAKURA, BlockTags.DIRT);
    public static final PottedBlock YELLOW_SAKURA_SAPLING = createSapling("yellow_sakura", BWGTreeGrowers.YELLOW_SAKURA, BlockTags.DIRT);
    public static final BWGWoodSet SKYRIS = new BWGWoodSet("skyris", MapColor.COLOR_LIGHT_BLUE, BWGTreeGrowers.SKYRIS);
    //public static final BWGWoodSet SYTHIAN = new BWGWoodSet("sythian", MapColor.COLOR_YELLOW, true, false);
    public static final BWGWoodSet WHITE_MANGROVE = new BWGWoodSet("white_mangrove", MapColor.TERRACOTTA_WHITE, BWGTreeGrowers.WHITE_MANGROVE);
    public static final BWGWoodSet WILLOW = new BWGWoodSet("willow", MapColor.COLOR_GREEN, BWGTreeGrowers.WILLOW);
    public static final BWGWoodSet WITCH_HAZEL = new BWGWoodSet("witch_hazel", MapColor.COLOR_GREEN, BWGTreeGrowers.WITCH_HAZEL);
    public static final BWGWoodSet ZELKOVA = new BWGWoodSet("zelkova", MapColor.COLOR_ORANGE, BWGTreeGrowers.ZELKOVA);

    public static final Supplier<RotatedPillarBlock> PALO_VERDE_LOG = registerBlockItem("palo_verde_log", () -> Blocks.log(MapColor.COLOR_GREEN, MapColor.COLOR_GREEN));
    public static final Supplier<RotatedPillarBlock> PALO_VERDE_WOOD = registerBlockItem("palo_verde_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Supplier<RotatedPillarBlock> STRIPPED_PALO_VERDE_LOG = registerBlockItem("stripped_palo_verde_log", () -> Blocks.log(MapColor.COLOR_GREEN, MapColor.COLOR_GREEN));
    public static final Supplier<RotatedPillarBlock> STRIPPED_PALO_VERDE_WOOD = registerBlockItem("stripped_palo_verde_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));

    public static final Supplier<LeavesBlock> PALO_VERDE_LEAVES = registerBlockItem("palo_verde_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));

    public static final PottedBlock PALO_VERDE_SAPLING = createSapling("palo_verde", BWGTreeGrowers.PALO_VERDE, BlockTags.SAND);

    // Other Saplings
    public static final PottedBlock ARAUCARIA_SAPLING = createSapling("araucaria", BWGTreeGrowers.ARAUCARIA, BlockTags.DIRT);
    public static final PottedBlock BLUE_SPRUCE_SAPLING = createSapling("blue_spruce", BWGTreeGrowers.BLUE_SPRUCE, BlockTags.DIRT);
    public static final PottedBlock BROWN_BIRCH_SAPLING = createSapling("brown_birch", BWGTreeGrowers.BROWN_BIRCH, BlockTags.DIRT);
    public static final PottedBlock BROWN_OAK_SAPLING = createSapling("brown_oak", BWGTreeGrowers.BROWN_OAK, BlockTags.DIRT);
    public static final PottedBlock BROWN_ZELKOVA_SAPLING = createSapling("brown_zelkova", BWGTreeGrowers.BROWN_ZELKOVA, BlockTags.DIRT);
    public static final PottedBlock INDIGO_JACARANDA_SAPLING = createSapling("indigo_jacaranda", BWGTreeGrowers.INDIGO_JACARANDA, BlockTags.DIRT);
    public static final PottedBlock ORANGE_BIRCH_SAPLING = createSapling("orange_birch", BWGTreeGrowers.ORANGE_BIRCH, BlockTags.DIRT);
    public static final PottedBlock ORANGE_OAK_SAPLING = createSapling("orange_oak", BWGTreeGrowers.ORANGE_OAK, BlockTags.DIRT);
    public static final PottedBlock ORANGE_SPRUCE_SAPLING = createSapling("orange_spruce", BWGTreeGrowers.ORANGE_SPRUCE, BlockTags.DIRT);
    public static final PottedBlock ORCHARD_SAPLING = createSapling("orchard", BWGTreeGrowers.ORCHARD, BlockTags.DIRT);
    public static final PottedBlock RED_BIRCH_SAPLING = createSapling("red_birch", BWGTreeGrowers.RED_BIRCH, BlockTags.DIRT);
    public static final PottedBlock RED_MAPLE_SAPLING = createSapling("red_maple", BWGTreeGrowers.RED_MAPLE, BlockTags.DIRT);
    public static final PottedBlock RED_OAK_SAPLING = createSapling("red_oak", BWGTreeGrowers.RED_OAK, BlockTags.DIRT);
    public static final PottedBlock RED_SPRUCE_SAPLING = createSapling("red_spruce", BWGTreeGrowers.RED_SPRUCE, BlockTags.DIRT);
    public static final PottedBlock SILVER_MAPLE_SAPLING = createSapling("silver_maple", BWGTreeGrowers.SILVER_MAPLE, BlockTags.DIRT);
    public static final PottedBlock YELLOW_BIRCH_SAPLING = createSapling("yellow_birch", BWGTreeGrowers.YELLOW_BIRCH, BlockTags.DIRT);
    public static final PottedBlock YELLOW_SPRUCE_SAPLING = createSapling("yellow_spruce", BWGTreeGrowers.YELLOW_SPRUCE, BlockTags.DIRT);
    public static final PottedBlock YUCCA_SAPLING = createSapling("yucca", BWGTreeGrowers.YUCCA, BlockTags.SAND);

    protected static PottedBlock createSapling(String key, Supplier<AbstractTreeGrower> grower, TagKey<Block> ground) {
        Supplier<SaplingBlock> sapling = registerBlockItem(key + "_sapling", () -> new BWGSaplingBlock(ground, grower.get()));
        return new PottedBlock(sapling, register("potted_" + key + "_sapling", PlatformHandler.PLATFORM_HANDLER.createPottedBlock(sapling)));
    }

    protected static <B extends Block> Supplier<B> registerBlockItem(String key, Supplier<B> blockSupplier) {
        Supplier<B> block = register(key, blockSupplier);
        registerItem(key, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    protected static <B extends Block> Supplier<B> register(String id, Supplier<B> blockSupplier) {
        Supplier<B> block = BWGBlocks.register(id, blockSupplier);
        WOOD.add(block);
        return block;
    }

    protected static <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item) {
        Supplier<I> supplier = BWGItems.register(id, item);
        WOOD_BLOCK_ITEMS.add(supplier);
        return supplier;
    }

    public static void wood() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Wood");
    }
}
