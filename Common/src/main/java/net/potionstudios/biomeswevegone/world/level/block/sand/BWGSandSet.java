package net.potionstudios.biomeswevegone.world.level.block.sand;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Sand and Sandstone blocks for Oh The Biomes We've Gone.
 * @author Joseph T. McQuigg
 **/
public class BWGSandSet {

    private static final ArrayList<BWGSandSet> sandSets = new ArrayList<>();
    private final String name;
    private final Supplier<SandBlock> sand;
    private final Supplier<Block> sandstone;
    private final Supplier<SlabBlock> sandstoneSlab;
    private final Supplier<StairBlock> sandstoneStairs;
    private final Supplier<WallBlock> sandstoneWall;
    private final Supplier<Block> chiseledSandstone;
    private final Supplier<Block> smoothSandstone;
    private final Supplier<SlabBlock> smoothSandstoneSlab;
    private final Supplier<StairBlock> smoothSandstoneStairs;
    private final Supplier<Block> cutSandstone;
    private final Supplier<SlabBlock> cutSandstoneSlab;
    private final TagKey<Block> sandstoneBlocksTag;
    private final TagKey<Item> sandstoneBlocksItemTag;
    private final TagKey<Block> sandBlockTag;
    private final TagKey<Item> sandItemTag;


    public BWGSandSet(String name, int dustColor) {
        this.name = name;
        this.sand = BWGBlocks.registerCubeAllBlockItem(name + "_sand", () -> new SandBlock(dustColor, BlockBehaviour.Properties.copy(Blocks.SAND)));
        this.sandstone = BWGBlocks.registerBlockItem(name + "_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));
        this.sandstoneSlab = BWGBlocks.registerBlockItem(name + "_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE_SLAB)));
        this.sandstoneStairs = BWGBlocks.registerBlockItem(name + "_sandstone_stairs", () -> new StairBlock(sandstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SANDSTONE_STAIRS)));
        this.sandstoneWall = BWGBlocks.registerBlockItem(name + "_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE_WALL)));
        this.chiseledSandstone = BWGBlocks.registerBlockItem("chiseled_" + name + "_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_SANDSTONE)));
        this.smoothSandstone = BWGBlocks.registerBlockItem("smooth_" + name + "_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE)));
        this.smoothSandstoneSlab = BWGBlocks.registerBlockItem("smooth_" + name + "_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE_SLAB)));
        this.smoothSandstoneStairs = BWGBlocks.registerBlockItem("smooth_" + name + "_sandstone_stairs", () -> new StairBlock(smoothSandstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE_STAIRS)));
        this.cutSandstone = BWGBlocks.registerBlockItem("cut_" + name + "_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE)));
        this.cutSandstoneSlab = BWGBlocks.registerBlockItem("cut_" + name + "_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE_SLAB)));
        this.sandstoneBlocksTag = TagKey.create(Registries.BLOCK, BiomesWeveGone.id(name + "_sandstone_blocks"));
        this.sandstoneBlocksItemTag = TagKey.create(Registries.ITEM, BiomesWeveGone.id(name + "_sandstone_blocks"));
        this.sandBlockTag = TagKey.create(Registries.BLOCK, BiomesWeveGone.id("sand/" + name));
        this.sandItemTag = TagKey.create(Registries.ITEM, BiomesWeveGone.id("sand/" + name));
        sandSets.add(this);
    }

    public String getName() {
        return name;
    }

    public SandBlock getSand() {
        return sand.get();
    }

    public Block getSandstone() {
        return sandstone.get();
    }

    public SlabBlock getSandstoneSlab() {
        return sandstoneSlab.get();
    }

    public StairBlock getSandstoneStairs() {
        return sandstoneStairs.get();
    }

    public WallBlock getSandstoneWall() {
        return sandstoneWall.get();
    }

    public Block getChiseledSandstone() {
        return chiseledSandstone.get();
    }

    public Block getSmoothSandstone() {
        return smoothSandstone.get();
    }

    public SlabBlock getSmoothSandstoneSlab() {
        return smoothSandstoneSlab.get();
    }

    public StairBlock getSmoothSandstoneStairs() {
        return smoothSandstoneStairs.get();
    }

    public Block getCutSandstone() {
        return cutSandstone.get();
    }

    public SlabBlock getCutSandstoneSlab() {
        return cutSandstoneSlab.get();
    }

    public TagKey<Block> getSandstoneBlocksTag() {
        return sandstoneBlocksTag;
    }

    public TagKey<Item> getSandstoneBlocksItemTag() {
        return sandstoneBlocksItemTag;
    }

    public TagKey<Block> getSandBlockTag() {
        return sandBlockTag;
    }

    public TagKey<Item> getSandItemTag() {
        return sandItemTag;
    }

    public BlockFamily getSandStoneFamily() {
        return BlockFamilies.familyBuilder(getSandstone())
                .wall(getSandstoneWall())
                .stairs(getSandstoneStairs())
                .slab(getSandstoneSlab())
                .chiseled(getChiseledSandstone())
                .cut(getCutSandstone())
                .dontGenerateRecipe()
                .getFamily();
    }

    public BlockFamily getSmoothSandStoneFamily() {
        return BlockFamilies.familyBuilder(getSmoothSandstone())
                .stairs(getSmoothSandstoneStairs())
                .slab(getSmoothSandstoneSlab())
                .getFamily();
    }

    public BlockFamily getCutSandStoneFamily() {
        return BlockFamilies.familyBuilder(getCutSandstone())
                .slab(getCutSandstoneSlab())
                .getFamily();
    }

    public static ArrayList<BWGSandSet> getSandSets() {
        return sandSets;
    }
}
