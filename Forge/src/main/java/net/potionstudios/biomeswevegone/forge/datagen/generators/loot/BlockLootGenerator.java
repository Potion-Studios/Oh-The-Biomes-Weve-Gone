package net.potionstudios.biomeswevegone.forge.datagen.generators.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.BWGBerryBush;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.OddionCrop;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.WhitePuffballBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.tree.fruit.BWGFruitBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.FlatVegetationBlock;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;
import net.potionstudios.biomeswevegone.world.level.block.wood.ImbuedBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class BlockLootGenerator extends BlockLootSubProvider {

    private final List<Block> knownBlocks = new ArrayList<>();

    public BlockLootGenerator() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        BiomesWeveGone.LOGGER.info("Generating loot tables for " + BiomesWeveGone.MOD_ID + "...");
        BWGWoodSet.woodsets().forEach(set -> {
	        BiomesWeveGone.LOGGER.info("Generating loot tables for {}...", set.name());
            dropSelf(set.planks());
            add(set.slab(), createSlabItemTable(set.slab()));
            dropSelf(set.stairs());
            dropSelf(set.button());
            dropSelf(set.pressurePlate());
            dropSelf(set.trapdoor());
            add(set.door(), createDoorTable(set.door()));
            dropSelf(set.fence());
            dropSelf(set.fenceGate());
            dropSelf(set.sign());
            dropSelf(set.hangingSign());
            add(set.bookshelf(), arg -> this.createSingleItemTableWithSilkTouch(arg, Items.BOOK, ConstantValue.exactly(3.0F)));
            dropSelf(set.craftingTable());
            dropSelf(set.logstem());
            dropSelf(set.strippedLogStem());
            dropSelf(set.wood());
            dropSelf(set.strippedWood());
            if (set.sapling() != null && set.leaves() != null) {
                add(set.leaves(), createLeavesDrops(set.leaves(), set.sapling().getBlock(), 0.05F));
                dropSelf(set.sapling().getBlock());
                dropPottedContents(set.sapling().getPottedBlock());
            }
        });

        BWGWood.WOOD.forEach(block -> {
            if (block.get() instanceof SaplingBlock) dropSelf(block.get());
            else if (block.get() instanceof FlowerPotBlock) dropPottedContents(block.get());
            else if (block.get() instanceof ImbuedBlock) dropSelf(block.get());
        });


        dropSelf(BWGWood.IMBUED_BLUE_ENCHANTED_WOOD.get());
        dropSelf(BWGWood.IMBUED_GREEN_ENCHANTED_WOOD.get());
        dropSelf(BWGWood.PALO_VERDE_LOG.get());
        dropSelf(BWGWood.PALO_VERDE_WOOD.get());
        dropSelf(BWGWood.STRIPPED_PALO_VERDE_LOG.get());
        dropSelf(BWGWood.STRIPPED_PALO_VERDE_WOOD.get());

        BWGBlocks.BLOCKS.forEach(entry -> {
            Block block = entry.get();
            if (block instanceof SlabBlock)
                add(block, createSlabItemTable(block));
            else if (block.defaultBlockState().getSoundType() == SoundType.GLASS)
                dropWhenSilkTouch(block);
            else if (Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().contains("_grass"))
                if (block instanceof DoublePlantBlock) add(block, createDoublePlantWithSeedDrops(block, block));
                else add(block, createGrassDrops(block));
            else if (block instanceof DoublePlantBlock)
                add(block, createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
            else if (block instanceof CampfireBlock)
                add(block, arg -> createSilkTouchDispatchTable(arg, this.applyExplosionCondition(arg, LootItem.lootTableItem(Items.CHARCOAL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
            else if (block instanceof BWGFruitBlock)
                add(block, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BWGFruitBlock.AGE, BWGFruitBlock.MAX_AGE))).add(LootItem.lootTableItem(((BWGFruitBlock) block).getFruit()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
            else if (block instanceof FlatVegetationBlock)
                add(block, createShearsDispatchTable(block, LootItem.lootTableItem(block.asItem())));
            else if (block instanceof VineBlock)
                add(block, createShearsDispatchTable(block, LootItem.lootTableItem(block)));
            else if (block instanceof GlassBlock || block instanceof StainedGlassPaneBlock)
                dropWhenSilkTouch(block);
            else if (block instanceof FlowerPotBlock)
                dropPottedContents(block);
            else if (block instanceof PinkPetalsBlock)
                add(block, createPetalsDrops(block));
            else if (block instanceof LeavesBlock) {
                BWGWoodSet.woodsets().forEach(set -> {
                    if (ForgeRegistries.BLOCKS.getKey(block).getPath().contains(set.name().toLowerCase() + "_"))
                        if (set.sapling() != null) add(block, createLeavesDrops(block, set.sapling().getBlock(), 0.05F));
                });
            } else dropSelf(block);
        });

        add(BWGWood.PALO_VERDE_LEAVES.get(), createLeavesDrops(BWGWood.PALO_VERDE_LEAVES.get(), BWGWood.PALO_VERDE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.FLOWERING_PALO_VERDE_LEAVES.get(), createLeavesDrops(BWGBlocks.FLOWERING_PALO_VERDE_LEAVES.get(), BWGWood.PALO_VERDE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.FLOWERING_BAOBAB_LEAVES.get(), createLeavesDrops(BWGBlocks.FLOWERING_BAOBAB_LEAVES.get(), BWGWood.BAOBAB.sapling().getBlock(), 0.05F));
        add(BWGBlocks.RIPE_BAOBAB_LEAVES.get(), createFruitLeavesDrops(BWGBlocks.RIPE_BAOBAB_LEAVES.get(), BWGWood.BAOBAB.sapling().getBlock(), BWGItems.BAOBAB_FRUIT.get()));
        add(BWGBlocks.WHITE_SAKURA_LEAVES.get(), createLeavesDrops(BWGBlocks.WHITE_SAKURA_LEAVES.get(), BWGWood.WHITE_SAKURA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.YELLOW_SAKURA_LEAVES.get(), createLeavesDrops(BWGBlocks.YELLOW_SAKURA_LEAVES.get(), BWGWood.YELLOW_SAKURA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.ARAUCARIA_LEAVES.get(), createLeavesDrops(BWGWood.ARAUCARIA_SAPLING.getBlock(), BWGWood.ARAUCARIA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.BLUE_SPRUCE_LEAVES.get(), createLeavesDrops(BWGWood.BLUE_SPRUCE_SAPLING.getBlock(), BWGWood.BLUE_SPRUCE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.BROWN_BIRCH_LEAVES.get(), createLeavesDrops(BWGWood.BROWN_BIRCH_SAPLING.getBlock(), BWGWood.BROWN_BIRCH_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.BROWN_OAK_LEAVES.get(), createLeavesDrops(BWGWood.BROWN_OAK_SAPLING.getBlock(), BWGWood.BROWN_OAK_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.BROWN_ZELKOVA_LEAVES.get(), createLeavesDrops(BWGWood.BROWN_ZELKOVA_SAPLING.getBlock(), BWGWood.BROWN_ZELKOVA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.BROWN_ZELKOVA_LEAVES.get(), createLeavesDrops(BWGWood.BROWN_ZELKOVA_SAPLING.getBlock(), BWGWood.BROWN_ZELKOVA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.FLOWERING_JACARANDA_LEAVES.get(), createLeavesDrops(BWGBlocks.FLOWERING_JACARANDA_LEAVES.get(), BWGWood.JACARANDA.sapling().getBlock(), 0.05F));
        add(BWGBlocks.INDIGO_JACARANDA_LEAVES.get(), createLeavesDrops(BWGWood.INDIGO_JACARANDA_SAPLING.getBlock(), BWGWood.INDIGO_JACARANDA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.FLOWERING_INDIGO_JACARANDA_LEAVES.get(), createLeavesDrops(BWGWood.INDIGO_JACARANDA_SAPLING.getBlock(), BWGWood.INDIGO_JACARANDA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.ORANGE_BIRCH_LEAVES.get(), createLeavesDrops(BWGWood.ORANGE_BIRCH_SAPLING.getBlock(), BWGWood.ORANGE_BIRCH_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.ORANGE_OAK_LEAVES.get(), createLeavesDrops(BWGWood.ORANGE_OAK_SAPLING.getBlock(), BWGWood.ORANGE_OAK_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.ORANGE_SPRUCE_LEAVES.get(), createLeavesDrops(BWGWood.ORANGE_SPRUCE_SAPLING.getBlock(), BWGWood.ORANGE_SPRUCE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.ORCHARD_LEAVES.get(), createLeavesDrops(BWGWood.ORCHARD_SAPLING.getBlock(), BWGWood.ORCHARD_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.FLOWERING_ORCHARD_LEAVES.get(), createLeavesDrops(BWGWood.ORCHARD_SAPLING.getBlock(), BWGWood.ORCHARD_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.RIPE_ORCHARD_LEAVES.get(), createFruitLeavesDrops(BWGBlocks.RIPE_ORCHARD_LEAVES.get(), BWGWood.ORCHARD_SAPLING.getBlock(), Items.APPLE));
        add(BWGBlocks.RED_BIRCH_LEAVES.get(), createLeavesDrops(BWGWood.RED_BIRCH_SAPLING.getBlock(), BWGWood.RED_BIRCH_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.RED_MAPLE_LEAVES.get(), createLeavesDrops(BWGWood.RED_MAPLE_SAPLING.getBlock(), BWGWood.RED_MAPLE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.RED_OAK_LEAVES.get(), createLeavesDrops(BWGWood.RED_OAK_SAPLING.getBlock(), BWGWood.RED_OAK_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.RED_SPRUCE_LEAVES.get(), createLeavesDrops(BWGWood.RED_SPRUCE_SAPLING.getBlock(), BWGWood.RED_SPRUCE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.SILVER_MAPLE_LEAVES.get(), createLeavesDrops(BWGWood.SILVER_MAPLE_SAPLING.getBlock(), BWGWood.SILVER_MAPLE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.YELLOW_BIRCH_LEAVES.get(), createLeavesDrops(BWGWood.YELLOW_BIRCH_SAPLING.getBlock(), BWGWood.YELLOW_BIRCH_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.YELLOW_SPRUCE_LEAVES.get(), createLeavesDrops(BWGWood.YELLOW_SPRUCE_SAPLING.getBlock(), BWGWood.YELLOW_SPRUCE_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.YUCCA_LEAVES.get(), createLeavesDrops(BWGWood.YUCCA_SAPLING.getBlock(), BWGWood.YUCCA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.FLOWERING_YUCCA_LEAVES.get(), createLeavesDrops(BWGWood.YUCCA_SAPLING.getBlock(), BWGWood.YUCCA_SAPLING.getBlock(), 0.05F));
        add(BWGBlocks.RIPE_YUCCA_LEAVES.get(), createFruitLeavesDrops(BWGBlocks.RIPE_YUCCA_LEAVES.get(), BWGWood.YUCCA_SAPLING.getBlock(), BWGItems.YUCCA_FRUIT.get()));
        add(BWGBlocks.BLOOMING_WITCH_HAZEL_LEAVES.get(), createLeavesDrops(BWGBlocks.BLOOMING_WITCH_HAZEL_LEAVES.get(), BWGWood.WITCH_HAZEL.sapling().getBlock(), 0.05F));
        add(BWGBlocks.FLOWERING_IRONWOOD_LEAVES.get(), createLeavesDrops(BWGBlocks.FLOWERING_IRONWOOD_LEAVES.get(), BWGWood.IRONWOOD.sapling().getBlock(), 0.05F));
        add(BWGBlocks.FIRECRACKER_LEAVES.get(), createLeavesDrops(BWGBlocks.FIRECRACKER_LEAVES.get(), BWGBlocks.FIRECRACKER_LEAVES.get()));
        add(BWGBlocks.FLOWERING_SKYRIS_LEAVES.get(), createLeavesDrops(BWGBlocks.FLOWERING_SKYRIS_LEAVES.get(), BWGWood.SKYRIS.sapling().getBlock(), 0.05F));
        add(BWGBlocks.SKYRIS_LEAVES_GREEN_APPLE.get(), createFruitLeavesDrops(BWGBlocks.SKYRIS_LEAVES_GREEN_APPLE.get(), BWGWood.SKYRIS.sapling().getBlock(), BWGItems.GREEN_APPLE.get()));


        add(BWGBlocks.LUSH_GRASS_BLOCK.get(), block -> createSingleItemTableWithSilkTouch(block, BWGBlocks.LUSH_DIRT.get()));
        dropOther(BWGBlocks.LUSH_FARMLAND.get(), BWGBlocks.LUSH_DIRT.get());
        dropOther(BWGBlocks.LUSH_DIRT_PATH.get(), BWGBlocks.LUSH_DIRT.get());
        dropOther(BWGBlocks.SANDY_FARMLAND.get(), BWGBlocks.SANDY_DIRT.get());
        dropOther(BWGBlocks.SANDY_DIRT_PATH.get(), BWGBlocks.SANDY_DIRT.get());

        add(BWGBlocks.DACITE_SET.getBase(), createSingleItemTableWithSilkTouch(BWGBlocks.DACITE_SET.getBase(), BWGBlocks.DACITE_COBBLESTONE_SET.getBase()));
        //add(BWGBlocks.ETHER_STONE_SET.getBase(), createSingleItemTableWithSilkTouch(BWGBlocks.ETHER_STONE_SET.getBase(), BWGBlocks.COBBLED_ETHER_STONE_SET.getBase()));

        add(BWGBlocks.GREEN_MUSHROOM_BLOCK.get(), createMushroomBlockDrop(BWGBlocks.GREEN_MUSHROOM_BLOCK.get(), BWGBlocks.GREEN_MUSHROOM.get()));
        add(BWGBlocks.WEEPING_MILKCAP_MUSHROOM_BLOCK.get(), createMushroomBlockDrop(BWGBlocks.WEEPING_MILKCAP_MUSHROOM_BLOCK.get(), BWGBlocks.WEEPING_MILKCAP.get()));
        add(BWGBlocks.WOOD_BLEWIT_MUSHROOM_BLOCK.get(), createMushroomBlockDrop(BWGBlocks.WOOD_BLEWIT_MUSHROOM_BLOCK.get(), BWGBlocks.WOOD_BLEWIT.get()));

        fruitBush(BWGBlocks.BLUEBERRY_BUSH.get(), BWGItems.BLUEBERRIES.get());
        //fruitBush(BWGBlocks.NIGHTSHADE_BERRY_BUSH.get(), BWGItems.NIGHTSHADE_BERRIES.get());

        LootItemCondition.Builder holder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BWGBlocks.ODDION_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OddionCrop.AGE, 3));

        this.add(
                BWGBlocks.ODDION_CROP.get(),
                this.applyExplosionDecay(
                        BWGBlocks.ODDION_CROP.get(),
                        LootTable.lootTable()
                                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(BWGItems.ODDION_BULB.get())))
                                .withPool(
                                        LootPool.lootPool()
                                                .when(holder)
                                                .add(LootItem.lootTableItem(BWGItems.ODDION_BULB.get()).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,  0.7714286F, 1)))
                                )
                )
        );

        LootItemCondition.Builder holder2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BWGBlocks.WHITE_PUFFBALL.getBlock())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WhitePuffballBlock.AGE, 3));

        this.add(BWGBlocks.WHITE_PUFFBALL.getBlock(), this.applyExplosionDecay(
                BWGBlocks.WHITE_PUFFBALL.getBlock(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(BWGItems.WHITE_PUFFBALL_SPORES.get())))
                        .withPool(
                                LootPool.lootPool()
                                        .when(holder2)
                                        .add(LootItem.lootTableItem(BWGItems.WHITE_PUFFBALL_CAP.get()))
                                        .add(LootItem.lootTableItem(BWGItems.WHITE_PUFFBALL_SPORES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 3f)))
                        )
        )));

        this.add(BWGBlocks.BLOOMING_ALOE_VERA.get(), this.createDoublePlantWithSeedDrops(BWGBlocks.BLOOMING_ALOE_VERA.get(), BWGBlocks.ALOE_VERA.get()));
    }

    private LootTable.Builder createFruitLeavesDrops(LeavesBlock leaves, Block saplingBlock, Item fruit) {
        return this.createLeavesDrops(leaves, saplingBlock)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(HAS_SHEARS.or(HAS_SILK_TOUCH).invert())
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(leaves, LootItem.lootTableItem(fruit)))
                                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))
                                )
                );
    }

    private void fruitBush(BWGBerryBush block, Item fruit) {
        add(
                block,
                arg -> this.applyExplosionDecay(
                        arg,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                                )
                                                .add(LootItem.lootTableItem(fruit))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                                )
                                                .add(LootItem.lootTableItem(fruit))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                                )
                )
        );
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder lootTableBuilder) {
        knownBlocks.add(block);
        super.add(block, lootTableBuilder);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
