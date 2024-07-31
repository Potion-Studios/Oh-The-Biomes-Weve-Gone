package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.entity.boats.BWGBoatEntity;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.item.boat.BWGBoatItem;
import net.potionstudios.biomeswevegone.world.level.block.plants.PottedBlock;
import net.potionstudios.biomeswevegone.world.level.block.wood.sign.BWGCeilingHangingSignBlock;
import net.potionstudios.biomeswevegone.world.level.block.wood.sign.BWGStandingSignBlock;
import net.potionstudios.biomeswevegone.world.level.block.wood.sign.BWGWallHangingSignBlock;
import net.potionstudios.biomeswevegone.world.level.block.wood.sign.BWGWallSignBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Wood set for Biomes We've Gone
 * @see BWGWood
 * @author Joseph T. McQuigg
 */
public class BWGWoodSet {

    private static final ArrayList<BWGWoodSet> woodSets = new ArrayList<>();

    private final String name;
    private final WoodType woodType;
    private final Supplier<Block> planks;
    private final Supplier<SlabBlock> slab;
    private final Supplier<StairBlock> stairs;
    private final LogStem logstemEnum;
    private final Supplier<RotatedPillarBlock> logstem;
    private final Supplier<RotatedPillarBlock> strippedLogStem;
    private final TagKey<Block> logBlockTag;
    private final TagKey<Item> logItemTag;
    private final Supplier<RotatedPillarBlock> wood;
    private final Supplier<RotatedPillarBlock> strippedWood;
    private final Supplier<StandingSignBlock> sign;
    private final Supplier<WallSignBlock> wallSign;
    private final Supplier<SignItem> signItem;
    private final Supplier<CeilingHangingSignBlock> hangingSign;
    private final Supplier<WallHangingSignBlock> wallHangingSign;
    private final Supplier<HangingSignItem> hangingSignItem;
    private final Supplier<PressurePlateBlock> pressurePlate;
    private final Supplier<TrapDoorBlock> trapdoor;
    private final Supplier<ButtonBlock> button;
    private final Supplier<FenceGateBlock> fenceGate;
    private final Supplier<FenceBlock> fence;
    private final Supplier<DoorBlock> door;
    private final Supplier<Block> bookshelf;
    private final Supplier<CraftingTableBlock> craftingTable;
    private @Nullable PottedBlock sapling = null;
    private @Nullable Supplier<LeavesBlock> leaves = null;
    private @Nullable Supplier<Item> boatItem = null;
    private @Nullable Supplier<Item> chestBoatItem = null;

    private BlockFamily family = null;

    /**
     * Creates a new wood set
     *
     * @param blockSetType  The wood type
     * @param mapColor      The map color
     * @param saplingGrower Whether to include a sapling
     * @param boats         Whether to include a boat
     * @param glowLeaves
     */
    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, LogStem logstem, @Nullable Supplier<AbstractTreeGrower> saplingGrower, boolean leaves, boolean boats, boolean glowLeaves) {
        this.woodType = PlatformHandler.PLATFORM_HANDLER.createWoodType(blockSetType.name(), blockSetType);
        this.name = blockSetType.name().replace(BiomesWeveGone.MOD_ID + ":", "");
        this.planks = BWGWood.registerBlockItem(name + "_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.slab = BWGWood.registerBlockItem(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.stairs = BWGWood.registerBlockItem(name + "_stairs", () -> new StairBlock(planks.get().defaultBlockState(), BlockBehaviour.Properties.copy(planks.get())));
        this.logstemEnum = logstem;
        this.logstem = BWGWood.registerBlockItem(name + "_" + logstem.getName(), () -> Blocks.log(mapColor, mapColor));
        this.strippedLogStem = BWGWood.registerBlockItem("stripped_" + name + "_" + logstem.getName(), () -> Blocks.log(mapColor, mapColor));
        this.wood = BWGWood.registerBlockItem(name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.strippedWood = BWGWood.registerBlockItem("stripped_" + name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.sign = BWGWood.register(name + "_sign", () ->  new BWGStandingSignBlock(BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), woodType));
        this.wallSign = BWGWood.register(name + "_wall_sign", () -> new BWGWallSignBlock(BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(sign.get()).ignitedByLava(), woodType));
        this.signItem = BWGItems.register(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), sign.get(), wallSign.get()));
        BWGWood.WOOD_BLOCK_ITEMS.add(signItem);
        this.hangingSign = BWGWood.register(name + "_hanging_sign", () -> new BWGCeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), woodType));
        this.wallHangingSign = BWGWood.register(name + "_wall_hanging_sign", () -> new BWGWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(hangingSign.get()).ignitedByLava(), woodType));
        this.hangingSignItem = BWGItems.register(name + "_hanging_sign", () -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), new Item.Properties().stacksTo(16)));
        BWGWood.WOOD_BLOCK_ITEMS.add(hangingSignItem);
        this.pressurePlate = BWGWood.registerBlockItem(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), woodType.setType()));
        this.trapdoor = BWGWood.registerBlockItem(name + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never).ignitedByLava(), woodType.setType()));
        this.button = BWGWood.registerBlockItem(name + "_button", () -> Blocks.woodenButton(woodType.setType()));
        this.fenceGate = BWGWood.registerBlockItem(name + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), woodType));
        this.fence = BWGWood.registerBlockItem(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD)));
        this.door = BWGWood.registerBlockItem(name + "_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), woodType.setType()));
        this.bookshelf = BWGWood.registerBlockItem(name + "_bookshelf", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).mapColor(mapColor)));
        this.craftingTable = BWGWood.registerBlockItem(name + "_crafting_table", () -> new BWGCraftingTable(mapColor));
        if (saplingGrower != null)
            this.sapling = BWGWood.createSapling(name, saplingGrower, BlockTags.DIRT);

        if (leaves) {
            if (glowLeaves) this.leaves = BWGWood.registerBlockItem(name + "_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).lightLevel(level -> 8).mapColor(mapColor)));
            else this.leaves = BWGWood.registerBlockItem(name + "_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(mapColor)));
        }

        if (boats) {
            this.boatItem = BWGWood.registerItem(name + "_boat", () -> new BWGBoatItem(false, BWGBoatEntity.Type.byName(name), new Item.Properties().stacksTo(16)));
            this.chestBoatItem = BWGWood.registerItem(name + "_chest_boat", () -> new BWGBoatItem(true, BWGBoatEntity.Type.byName(name), new Item.Properties().stacksTo(16)));
        }
        this.logBlockTag = TagKey.create(Registries.BLOCK, BiomesWeveGone.id(name + "_logs"));
        this.logItemTag = TagKey.create(Registries.ITEM, BiomesWeveGone.id(name + "_logs"));
        woodSets.add(this);
    }

    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor) {
        this(blockSetType, mapColor, LogStem.LOG, null, true, true, false);
    }

    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, @Nullable Supplier<AbstractTreeGrower> saplingGrower, boolean leaves, boolean boats) {
        this(blockSetType, mapColor, LogStem.LOG, saplingGrower, leaves, boats, false);
    }

    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, @Nullable Supplier<AbstractTreeGrower> saplingGrower, boolean leaves, boolean boats, boolean glowLeaves) {
        this(blockSetType, mapColor, LogStem.LOG, saplingGrower, leaves, boats, glowLeaves);
    }

    protected BWGWoodSet(String name, MapColor mapColor, boolean leaves, boolean boats) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, LogStem.LOG, null, leaves, boats, false);
    }

    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, LogStem logstem) {
        this(blockSetType, mapColor, logstem, null, true, true, false);
    }

    protected BWGWoodSet(String name, MapColor mapColor, @Nullable Supplier<AbstractTreeGrower> saplingGrower) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, saplingGrower, true, true);
    }

    protected BWGWoodSet(String name, MapColor mapColor, @Nullable Supplier<AbstractTreeGrower> saplingGrower, boolean glowLeaves) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, saplingGrower, true, true, glowLeaves);
    }

    public String name() {
        return name;
    }

    public WoodType woodType() {
        return woodType;
    }

    public Block planks() {
        return planks.get();
    }

    public SlabBlock slab() {
        return slab.get();
    }

    public StairBlock stairs() {
        return stairs.get();
    }

    public RotatedPillarBlock logstem() {
        return logstem.get();
    }

    public RotatedPillarBlock strippedLogStem() {
        return strippedLogStem.get();
    }

    public TagKey<Block> logBlockTag() {
        return logBlockTag;
    }

    public TagKey<Item> logItemTag() {
        return logItemTag;
    }

    public RotatedPillarBlock wood() {
        return wood.get();
    }

    public RotatedPillarBlock strippedWood() {
        return strippedWood.get();
    }

    public StandingSignBlock sign() {
        return sign.get();
    }

    public WallSignBlock wallSign() {
        return wallSign.get();
    }
    public SignItem signItem() {
        return signItem.get();
    }

    public CeilingHangingSignBlock hangingSign() {
        return hangingSign.get();
    }

    public WallHangingSignBlock wallHangingSign() {
        return wallHangingSign.get();
    }

    public HangingSignItem hangingSignItem() {
        return hangingSignItem.get();
    }

    public PressurePlateBlock pressurePlate() {
        return pressurePlate.get();
    }

    public TrapDoorBlock trapdoor() {
        return trapdoor.get();
    }

    public ButtonBlock button() {
        return button.get();
    }

    public FenceGateBlock fenceGate() {
        return fenceGate.get();
    }

    public FenceBlock fence() {
        return fence.get();
    }

    public DoorBlock door() {
        return door.get();
    }
    public Block bookshelf() {
        return bookshelf.get();
    }

    public CraftingTableBlock craftingTable() {
        return craftingTable.get();
    }

    public @Nullable PottedBlock sapling() {
        if (sapling != null) return sapling;
        return null;
    }

    public @Nullable LeavesBlock leaves() {
        if (leaves != null)
            return leaves.get();
        return null;
    }

    public @Nullable Supplier<Item> boatItem() {
        return boatItem;
    }

    public @Nullable Supplier<Item> chestBoatItem() {
        return chestBoatItem;
    }

    public ArrayList<ItemLike> itemList() {
        ArrayList<ItemLike> items = new ArrayList<>(List.of(planks.get(), slab.get(), stairs.get(), logstem.get(), strippedLogStem.get(), wood.get(), strippedWood.get(), sign.get(), hangingSign.get(), pressurePlate.get(), trapdoor.get(), button.get(), fenceGate.get(), fence.get(), door.get(), bookshelf.get(), craftingTable.get()));
        if (leaves != null) items.add(leaves.get());
        return items;
    }

    public void makeFamily() {
        this.family = BlockFamilies.familyBuilder(planks.get()).button(button.get()).fence(fence.get()).fenceGate(fenceGate.get()).pressurePlate(pressurePlate.get()).sign(sign.get(), wallSign.get()).slab(slab.get()).stairs(stairs.get()).door(door.get()).trapdoor(trapdoor.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    }

    public BlockFamily family() {
        return family;
    }

    public static ArrayList<BWGWoodSet> woodsets() {
        return woodSets;
    }

    public LogStem logStemEnum() {
        return logstemEnum;
    }

    public enum LogStem {
        LOG("log"),
        STEM("stem");

        private final String name;

        LogStem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
