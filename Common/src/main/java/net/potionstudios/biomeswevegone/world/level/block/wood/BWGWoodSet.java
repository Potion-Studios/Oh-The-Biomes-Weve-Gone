package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
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
    private final LogStem logstemEnum;
    private final Supplier<RotatedPillarBlock> logstem;
    private final Supplier<RotatedPillarBlock> wood;
    private final Supplier<RotatedPillarBlock> strippedLogStem;
    private final Supplier<RotatedPillarBlock> strippedWood;
    private final Supplier<Block> planks;
    private final Supplier<StairBlock> stairs;
    private final Supplier<SlabBlock> slab;
    private final Supplier<FenceBlock> fence;
    private final Supplier<FenceGateBlock> fenceGate;
    private final Supplier<DoorBlock> door;
    private final Supplier<TrapDoorBlock> trapdoor;
    private final Supplier<PressurePlateBlock> pressurePlate;
    private final Supplier<ButtonBlock> button;
    private final Supplier<Block> bookshelf;
    private final Supplier<CraftingTableBlock> craftingTable;
    private @Nullable Supplier<LeavesBlock> leaves = null;
    private @Nullable PottedBlock sapling = null;
    private final Supplier<StandingSignBlock> sign;
    private final Supplier<WallSignBlock> wallSign;
    private final Supplier<SignItem> signItem;
    private final Supplier<CeilingHangingSignBlock> hangingSign;
    private final Supplier<WallHangingSignBlock> wallHangingSign;
    private final Supplier<HangingSignItem> hangingSignItem;
    private final Supplier<Item> boatItem;
    private final Supplier<Item> chestBoatItem;
    private final TagKey<Block> logBlockTag;
    private final TagKey<Item> logItemTag;

    private final Supplier<EntityType<Boat>> boat;
    private final Supplier<EntityType<ChestBoat>> chestBoat;

    private BlockFamily family = null;

    /**
     * Creates a new wood set
     *
     * @param blockSetType       The wood type
     * @param mapColor           The map color
     * @param saplingGrower      Whether to include a sapling
     * @param glowLeaves         Whether leaves should glow
     * @param saplingPlantAbleOn The tag for what the sapling can be planted on
     * @param leafTint           The tint for the leaves, if applicable
     */
    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, LogStem logstem, @Nullable Supplier<TreeGrower> saplingGrower, boolean leaves, boolean glowLeaves, @Nullable TagKey<Block> saplingPlantAbleOn, int leafTint) {
        this.woodType = PlatformHandler.PLATFORM_HANDLER.createWoodType(blockSetType.name(), blockSetType);
        this.name = blockSetType.name().replace(BiomesWeveGone.MOD_ID + ":", "");
        this.logstemEnum = logstem;
        this.logstem = BWGWood.registerBlockItem(name + "_" + logstem.getName(), RotatedPillarBlock::new, Blocks.logProperties(mapColor, mapColor, SoundType.WOOD));
        this.wood = BWGWood.registerBlockItem(name + "_wood", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
        this.strippedLogStem = BWGWood.registerBlockItem("stripped_" + name + "_" + logstem.getName(), RotatedPillarBlock::new, Blocks.logProperties(mapColor, mapColor, SoundType.WOOD));
        this.strippedWood = BWGWood.registerBlockItem("stripped_" + name + "_wood", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
        this.planks = BWGWood.registerBlockItem(name + "_planks", Block::new, BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());
        this.stairs = BWGWood.registerBlockItem(name + "_stairs", properties -> new StairBlock(planks.get().defaultBlockState(), properties), BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());
        this.slab = BWGWood.registerBlockItem(name + "_slab", SlabBlock::new, BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());
        this.fence = BWGWood.registerBlockItem(name + "_fence", FenceBlock::new, BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD));
        this.fenceGate = BWGWood.registerBlockItem(name + "_fence_gate", properties -> new FenceGateBlock(woodType, properties), BlockBehaviour.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava());
        this.door = BWGWood.registerBlockItem(name + "_door", properties -> new DoorBlock(woodType.setType(), properties), BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY));
        this.trapdoor = BWGWood.registerBlockItem(name + "_trapdoor", properties -> new TrapDoorBlock(woodType.setType(), properties), BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never).ignitedByLava());
        this.pressurePlate = BWGWood.registerBlockItem(name + "_pressure_plate", properties -> new PressurePlateBlock(woodType.setType(), properties), BlockBehaviour.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY));
        this.button = BWGWood.registerBlockItem(name + "_button", properties -> new ButtonBlock(woodType.setType(), 30, properties), Blocks.buttonProperties());
        this.bookshelf = BWGWood.registerBlockItem(name + "_bookshelf", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BOOKSHELF).mapColor(mapColor));
        this.craftingTable = BWGWood.registerBlockItem(name + "_crafting_table", BWGCraftingTable::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).mapColor(mapColor));
        if (leaves)
			if (leafTint == 0) {
				if (glowLeaves) this.leaves = BWGWood.registerBlockItem(name + "_leaves", properties -> new TintedParticleLeavesBlock(0.01F, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).lightLevel(level -> 8));
				else this.leaves = BWGWood.registerBlockItem(name + "_leaves", properties -> new TintedParticleLeavesBlock(0.01F, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
			} else {
				if (glowLeaves) this.leaves = BWGWood.registerBlockItem(name + "_leaves", properties -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, leafTint),properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).lightLevel(level -> 8));
				else this.leaves = BWGWood.registerBlockItem(name + "_leaves", properties -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, leafTint), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
			}
        if (saplingGrower != null) this.sapling = BWGWood.createSapling(name, saplingGrower, saplingPlantAbleOn);
        this.sign = BWGWood.register(name + "_sign", properties ->  new BWGStandingSignBlock(properties, woodType), BlockBehaviour.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());
        this.wallSign = BWGWood.register(name + "_wall_sign", () -> new BWGWallSignBlock(Blocks.wallVariant(sign.get(), true).mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava().setId(key(name + "_wall_sign")), this.woodType));
        this.signItem = BWGItems.register(name + "_sign", properties -> new SignItem(sign.get(), wallSign.get(), properties), new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());
        BWGWood.WOOD_BLOCK_ITEMS.add(signItem);
        this.hangingSign = BWGWood.register(name + "_hanging_sign", properties -> new BWGCeilingHangingSignBlock(properties, woodType), BlockBehaviour.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());
        this.wallHangingSign = BWGWood.register(name + "_wall_hanging_sign", () -> new BWGWallHangingSignBlock(Blocks.wallVariant(hangingSign(), true).mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava().setId(key(name + "_wall_hanging_sign")), woodType));
        this.hangingSignItem = BWGItems.register(name + "_hanging_sign", properties -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), properties), new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());
        BWGWood.WOOD_BLOCK_ITEMS.add(hangingSignItem);

        this.boat = BWGEntityType.createEntity(name + "_boat", (type, level) -> new Boat(type, level, boatItem()), MobCategory.MISC, EntityType.OAK_BOAT.getWidth(), EntityType.OAK_BOAT.getHeight(), 0.5625F, 10);
        this.chestBoat = BWGEntityType.createEntity(name + "_chest_boat", (type, level) -> new ChestBoat(type, level, chestBoatItem()), MobCategory.MISC, EntityType.OAK_CHEST_BOAT.getWidth(), EntityType.OAK_CHEST_BOAT.getHeight(), 0.5625F, 10);

        this.boatItem = BWGWood.registerItem(name + "_boat", properties -> new BoatItem(boat.get(), properties), new Item.Properties().stacksTo(1));
        this.chestBoatItem = BWGWood.registerItem(name + "_chest_boat", properties -> new BoatItem(chestBoat.get(), properties), new Item.Properties().stacksTo(1));

        this.logBlockTag = TagKey.create(Registries.BLOCK, BiomesWeveGone.id(name + "_logs"));
        this.logItemTag = TagKey.create(Registries.ITEM, BiomesWeveGone.id(name + "_logs"));

        woodSets.add(this);
    }

    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, @Nullable Supplier<TreeGrower> saplingGrower, boolean leaves, int leafTint) {
        this(blockSetType, mapColor, LogStem.LOG, saplingGrower, leaves, false, BlockTags.DIRT, leafTint);
    }

    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, @Nullable Supplier<TreeGrower> saplingGrower, boolean leaves, TagKey<Block> saplingPlantAbleOn, int leafTint) {
        this(blockSetType, mapColor, LogStem.LOG, saplingGrower, leaves, false, saplingPlantAbleOn, leafTint);
    }

    protected BWGWoodSet(BlockSetType blockSetType, MapColor mapColor, @Nullable Supplier<TreeGrower> saplingGrower, boolean leaves, boolean glowLeaves, int leafTint) {
        this(blockSetType, mapColor, LogStem.LOG, saplingGrower, leaves, glowLeaves, BlockTags.DIRT, leafTint);
    }

    protected BWGWoodSet(String name, MapColor mapColor, @Nullable Supplier<TreeGrower> saplingGrower, int leafTint) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, saplingGrower, true, leafTint);
    }

    protected BWGWoodSet(String name, MapColor mapColor, @Nullable Supplier<TreeGrower> saplingGrower, TagKey<Block> saplingPlantAbleOn, int leafTint) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, saplingGrower, true, saplingPlantAbleOn, leafTint);
    }

    protected BWGWoodSet(String name, MapColor mapColor, @Nullable Supplier<TreeGrower> saplingGrower, boolean glowLeaves, int leafTint) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, saplingGrower, true, glowLeaves, leafTint);
    }

    public String name() {
        return name;
    }

    public WoodType woodType() {
        return woodType;
    }

    public RotatedPillarBlock logstem() {
        return logstem.get();
    }

    public RotatedPillarBlock wood() {
        return wood.get();
    }

    public RotatedPillarBlock strippedLogStem() {
        return strippedLogStem.get();
    }

    public RotatedPillarBlock strippedWood() {
        return strippedWood.get();
    }

    public Block planks() {
        return planks.get();
    }

    public StairBlock stairs() {
        return stairs.get();
    }

    public SlabBlock slab() {
        return slab.get();
    }

    public TagKey<Block> logBlockTag() {
        return logBlockTag;
    }

    public TagKey<Item> logItemTag() {
        return logItemTag;
    }
    public FenceBlock fence() {
        return fence.get();
    }

    public FenceGateBlock fenceGate() {
        return fenceGate.get();
    }

    public DoorBlock door() {
        return door.get();
    }

    public TrapDoorBlock trapdoor() {
        return trapdoor.get();
    }

    public PressurePlateBlock pressurePlate() {
        return pressurePlate.get();
    }

    public ButtonBlock button() {
        return button.get();
    }

    public Block bookshelf() {
        return bookshelf.get();
    }

    public CraftingTableBlock craftingTable() {
        return craftingTable.get();
    }

    public @Nullable LeavesBlock leaves() {
        if (leaves != null)
            return leaves.get();
        return null;
    }

    public @Nullable PottedBlock sapling() {
        if (sapling != null) return sapling;
        return null;
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
    public Supplier<Item> boatItem() {
        return boatItem;
    }

    public Supplier<Item> chestBoatItem() {
        return chestBoatItem;
    }

    public Supplier<EntityType<Boat>> boat() {
        return boat;
    }

    public Supplier<EntityType<ChestBoat>> chestBoat() {
        return chestBoat;
    }

    public ModelLayerLocation boatModelLayer() {
        return new ModelLayerLocation(BiomesWeveGone.id("boat/" + name), "main");
    }

    public ModelLayerLocation chestBoatModelLayer() {
        return new ModelLayerLocation(BiomesWeveGone.id("chest_boat/" + name), "main");
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

    private static ResourceKey<Block> key(String id) {
        return BiomesWeveGone.key(Registries.BLOCK, id);
    }
}
