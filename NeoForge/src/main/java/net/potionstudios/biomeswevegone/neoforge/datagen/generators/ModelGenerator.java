package net.potionstudios.biomeswevegone.neoforge.datagen.generators;

import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.color.item.BorealisIceColorSource;
import net.potionstudios.biomeswevegone.client.color.item.FoliageColorSource;
import net.potionstudios.biomeswevegone.component.BWGDataComponents;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.BWGPlacementBushBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.HydrangeaHedgeBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.ShrubBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.WhitePuffballBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.cactus.CarvedBarrelCactusBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.cactus.LiquidType;
import net.potionstudios.biomeswevegone.world.level.block.plants.tree.fruit.BWGFruitBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.BoneMealGrassBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.FlatVegetationBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.GlowCaneBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.cattail.CattailPlantBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.cattail.CattailSproutBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.cattail.ColorProperty;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.cattail.FluorescentCattailPlantBlock;
import net.potionstudios.biomeswevegone.world.level.block.sand.BWGSandSet;
import net.potionstudios.biomeswevegone.world.level.block.set.BWGBlockSet;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;

import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class ModelGenerator extends ModelProvider {
    public ModelGenerator(PackOutput arg) {
        super(arg, BiomesWeveGone.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        BWGBlocks.cubeAllBlocks.forEach(block -> {
            blockModels.createTrivialCube(block.get());
            blockItemModel(blockModels, block.get());
        });

        BWGBlockSet.getBlockSets().stream().filter(set -> set.getBlockFamily().shouldGenerateModel()).forEach(set -> {
            blockModels.family(set.getBase()).generateFor(set.getBlockFamily());
            blockItemModel(blockModels, set.getBase());
        });

        BWGWoodSet.woodsets().forEach(woodSet -> {
            String folder = "block/" + woodSet.name() + "/";
            Material Planks = material(BiomesWeveGone.id(folder + "planks"));
            blockModels.createTrivialBlock(woodSet.planks(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, Planks)));
            blockItemModel(blockModels, woodSet.planks());

            TextureMapping planks = new TextureMapping().put(TextureSlot.ALL, Planks);

            createSlabAndStairs(blockModels, itemModels, woodSet.slab(), woodSet.stairs(), woodSet.planks(), planks);

            blockModels.blockStateOutput.accept(BlockModelGenerators.createButton(woodSet.button(), BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(woodSet.button(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(woodSet.planks(), planks, blockModels.modelOutput))));
            itemModels.itemModelOutput.accept(woodSet.button().asItem(), ItemModelUtils.plainModel(ModelTemplates.BUTTON_INVENTORY.create(woodSet.button().asItem(), planks, itemModels.modelOutput)));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createFence(woodSet.fence(), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(woodSet.fence(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_SIDE.create(woodSet.fence(), planks, blockModels.modelOutput))));
            itemModels.itemModelOutput.accept(woodSet.fence().asItem(), ItemModelUtils.plainModel(ModelTemplates.FENCE_INVENTORY.create(woodSet.fence().asItem(), planks, itemModels.modelOutput)));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createFenceGate(woodSet.fenceGate(), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), true));
            blockItemModel(blockModels, woodSet.fenceGate());

            TextureMapping door = new TextureMapping().put(TextureSlot.TOP, material(BiomesWeveGone.id(folder + "door_top"))).put(TextureSlot.BOTTOM, material(BiomesWeveGone.id(folder + "door_bottom")));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createDoor(woodSet.door(),
                    BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.create(woodSet.door(), door, blockModels.modelOutput)),
                            BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(woodSet.door(), door, blockModels.modelOutput)),
                                    BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.create(woodSet.door(), door, blockModels.modelOutput)),
                                            BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(woodSet.door(), door, blockModels.modelOutput)),
                                                    BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT.create(woodSet.door(), door, blockModels.modelOutput)),
                                                            BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.create(woodSet.door(), door, blockModels.modelOutput)),
                                                                    BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT.create(woodSet.door(), door, blockModels.modelOutput)),
                                                                            BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(woodSet.door(), door, blockModels.modelOutput))));

            TextureMapping trapdoor = new TextureMapping().put(TextureSlot.TEXTURE, material(BiomesWeveGone.id(folder + "trapdoor")));
            Identifier trapdoorBottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput);
            blockModels.blockStateOutput.accept(BlockModelGenerators.createOrientableTrapdoor(woodSet.trapdoor(),
                    BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput)),
                    BlockModelGenerators.plainVariant(trapdoorBottom),
                    BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput))));

            blockModels.itemModelOutput.accept(woodSet.trapdoor().asItem(), ItemModelUtils.plainModel(trapdoorBottom));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(woodSet.pressurePlate(),
                    BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(woodSet.pressurePlate(), planks, blockModels.modelOutput)),
                            BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(woodSet.pressurePlate(), planks, blockModels.modelOutput))));
            blockItemModel(blockModels, woodSet.pressurePlate());

            Identifier Log = BiomesWeveGone.id(folder + woodSet.logStemEnum().getName());
            Identifier LogTop = BiomesWeveGone.id(folder + woodSet.logStemEnum().getName() + "_top");

            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.logstem(),
                    BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.logstem(), new TextureMapping().put(TextureSlot.END, material(LogTop)).put(TextureSlot.SIDE, material(Log)), blockModels.modelOutput)),
                            BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.logstem(), new TextureMapping().put(TextureSlot.END, material(LogTop)).put(TextureSlot.SIDE, material(Log)), blockModels.modelOutput))));
            blockItemModel(blockModels, woodSet.logstem());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.wood(),
                    BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.wood(), new TextureMapping().put(TextureSlot.END, material(Log)).put(TextureSlot.SIDE, material(Log)), blockModels.modelOutput)),
                    BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.wood(), new TextureMapping().put(TextureSlot.END, material(Log)).put(TextureSlot.SIDE, material(Log)), blockModels.modelOutput))));
            blockItemModel(blockModels, woodSet.wood());

            Identifier StrippedLog = BiomesWeveGone.id(folder + "stripped_" + woodSet.logStemEnum().getName());
            Identifier StrippedLogTop = BiomesWeveGone.id(folder + "stripped_" + woodSet.logStemEnum().getName() + "_top");
            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.strippedLogStem(),
                    BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.strippedLogStem(), new TextureMapping().put(TextureSlot.END, material(StrippedLogTop)).put(TextureSlot.SIDE, material(StrippedLog)), blockModels.modelOutput)),
                            BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.strippedLogStem(), new TextureMapping().put(TextureSlot.END, material(StrippedLogTop)).put(TextureSlot.SIDE, material(StrippedLog)), blockModels.modelOutput))));
            blockItemModel(blockModels, woodSet.strippedLogStem());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.strippedWood(),
                    BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.strippedWood(), new TextureMapping().put(TextureSlot.END, material(StrippedLog)).put(TextureSlot.SIDE, material(StrippedLog)), blockModels.modelOutput)),
                            BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.strippedWood(), new TextureMapping().put(TextureSlot.END, material(StrippedLog)).put(TextureSlot.SIDE, material(StrippedLog)), blockModels.modelOutput))));
            blockItemModel(blockModels, woodSet.strippedWood());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.sign(), BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(woodSet.sign(), planks, blockModels.modelOutput))));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.wallSign(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(woodSet.sign()))));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.hangingSign(), BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(woodSet.hangingSign(), TextureMapping.particle(material(StrippedLog)), blockModels.modelOutput))));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.wallHangingSign(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(woodSet.hangingSign()))));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.bookshelf(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.bookshelf(), new TextureMapping().put(TextureSlot.END, Planks).put(TextureSlot.SIDE, material(BiomesWeveGone.id("block/" + woodSet.name() + "/bookshelf"))), blockModels.modelOutput))));
            blockItemModel(blockModels, woodSet.bookshelf());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.craftingTable(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(woodSet.craftingTable(), new TextureMapping()
                    .put(TextureSlot.DOWN, Planks)
                    .put(TextureSlot.UP, material(BiomesWeveGone.id(folder + "crafting_table_top")))
                    .put(TextureSlot.EAST, material(BiomesWeveGone.id(folder + "crafting_table_side")))
                    .put(TextureSlot.WEST, material(BiomesWeveGone.id(folder + "crafting_table_front")))
                    .put(TextureSlot.NORTH, material(BiomesWeveGone.id(folder + "crafting_table_front")))
                    .put(TextureSlot.SOUTH, material(BiomesWeveGone.id(folder + "crafting_table_side")))
                    .put(TextureSlot.PARTICLE, material(BiomesWeveGone.id(folder + "crafting_table_front"))), blockModels.modelOutput))));
            blockItemModel(blockModels, woodSet.craftingTable());

            if (woodSet.leaves() != null) {
                blockModels.createTrivialBlock(woodSet.leaves(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, material(BiomesWeveGone.id(folder + "leaves")))));
                if (woodSet.leaves() instanceof TintedParticleLeavesBlock)
                    itemModels.itemModelOutput.accept(woodSet.leaves().asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(woodSet.leaves()), new FoliageColorSource()));
                else blockItemModel(blockModels, woodSet.leaves());
            }
            if (woodSet.sapling() != null) {
                blockModels.createTrivialBlock(woodSet.sapling().getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, material(BiomesWeveGone.id(folder + "sapling")))));
                itemModels.itemModelOutput.accept(woodSet.sapling().getItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.sapling().getItem(), TextureMapping.layer0(material(BiomesWeveGone.id(folder + "sapling"))), itemModels.modelOutput)));
                blockModels.createTrivialBlock(woodSet.sapling().getPottedBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, material(BiomesWeveGone.id(folder + "sapling")))));
            }

            itemModels.itemModelOutput.accept(woodSet.signItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.signItem(), TextureMapping.layer0(material(BiomesWeveGone.id("item/" + woodSet.name() + "/sign"))), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.hangingSignItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.hangingSignItem(), TextureMapping.layer0(material(BiomesWeveGone.id("item/" + woodSet.name() + "/hanging_sign"))), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.door().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.door(), TextureMapping.layer0(material(BiomesWeveGone.id("item/" + woodSet.name() + "/door"))), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.boatItem().get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.boatItem().get(), TextureMapping.layer0(material(BiomesWeveGone.id("item/" + woodSet.name() + "/boat"))), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.chestBoatItem().get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.chestBoatItem().get(), TextureMapping.layer0(material(BiomesWeveGone.id("item/" + woodSet.name() + "/chest_boat"))), itemModels.modelOutput)));
        });

        blockModels.createTrivialBlock(BWGWood.WHITE_SAKURA_SAPLING.getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, material(BiomesWeveGone.id("block/sakura/white_sapling")))));
        itemModels.itemModelOutput.accept(BWGWood.WHITE_SAKURA_SAPLING.getItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGWood.WHITE_SAKURA_SAPLING.getItem(), TextureMapping.layer0(material(BiomesWeveGone.id("block/sakura/white_sapling"))), itemModels.modelOutput)));
        blockModels.createTrivialBlock(BWGWood.WHITE_SAKURA_SAPLING.getPottedBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, material(BiomesWeveGone.id("block/sakura/white_sapling")))));


        blockModels.createTrivialBlock(BWGWood.YELLOW_SAKURA_SAPLING.getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, material(BiomesWeveGone.id("block/sakura/yellow_sapling")))));
        itemModels.itemModelOutput.accept(BWGWood.YELLOW_SAKURA_SAPLING.getItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGWood.YELLOW_SAKURA_SAPLING.getItem(), TextureMapping.layer0(material(BiomesWeveGone.id("block/sakura/yellow_sapling"))), itemModels.modelOutput)));
        blockModels.createTrivialBlock(BWGWood.YELLOW_SAKURA_SAPLING.getPottedBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, material(BiomesWeveGone.id("block/sakura/yellow_sapling")))));


        blockModels.createTrivialBlock(BWGWood.IMBUED_BLUE_ENCHANTED_WOOD.get(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, material(BiomesWeveGone.id("block/blue_enchanted/imbued_wood")))));
        blockItemModel(blockModels, BWGWood.IMBUED_BLUE_ENCHANTED_WOOD.get());
        blockModels.createTrivialBlock(BWGWood.IMBUED_GREEN_ENCHANTED_WOOD.get(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, material(BiomesWeveGone.id("block/green_enchanted/imbued_wood")))));
        blockItemModel(blockModels, BWGWood.IMBUED_GREEN_ENCHANTED_WOOD.get());

        BWGWood.NONSET_WOOD.forEach(block -> {
            Block b = block.get();
            if (b instanceof LeavesBlock leavesBlock) {
                if (TextureMapping.getBlockTexture(leavesBlock).toString().contains("yucca")) {
                    blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(leavesBlock, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(leavesBlock))));
                    itemModels.itemModelOutput.accept(leavesBlock.asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(leavesBlock), new FoliageColorSource()));
                }
                else {
                    blockModels.createTrivialBlock(leavesBlock, TexturedModel.LEAVES);
                    blockItemModel(blockModels, leavesBlock);
                }
            } else if (b instanceof SaplingBlock sapling) {
                blockModels.createTrivialBlock(sapling, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS));
                blockModels.registerSimpleFlatItemModel(sapling);
            } else if (b instanceof FlowerPotBlock flowerPotBlock) {
                blockModels.createTrivialBlock(flowerPotBlock, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, material(ModelLocationUtils.getModelLocation(flowerPotBlock.getPotted())))));
            }
        });

        Material Log = material(BiomesWeveGone.id("block/palo_verde/log"));
        Material LogTop = material(BiomesWeveGone.id("block/palo_verde/log_top"));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.PALO_VERDE_LOG.get(),
                BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(BWGWood.PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput)),
                        BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGWood.PALO_VERDE_LOG.get());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.PALO_VERDE_WOOD.get(),
                BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(BWGWood.PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput)),
                        BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGWood.PALO_VERDE_WOOD.get());

        Material StrippedLog = material(BiomesWeveGone.id("block/palo_verde/stripped_log"));
        Material StrippedLogTop = material(BiomesWeveGone.id("block/palo_verde/stripped_log_top"));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.STRIPPED_PALO_VERDE_LOG.get(),
                BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(BWGWood.STRIPPED_PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)),
                        BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.STRIPPED_PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGWood.STRIPPED_PALO_VERDE_LOG.get());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.STRIPPED_PALO_VERDE_WOOD.get(),
                BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(BWGWood.STRIPPED_PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)),
                        BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.STRIPPED_PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGWood.STRIPPED_PALO_VERDE_WOOD.get());

        blockModels.createTrivialBlock(BWGWood.PALO_VERDE_SAPLING.getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, material(BiomesWeveGone.id( "block/palo_verde/sapling")))));
        itemModels.itemModelOutput.accept(BWGWood.PALO_VERDE_SAPLING.getBlock().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGWood.PALO_VERDE_SAPLING.getBlock().asItem(), TextureMapping.layer0(material(BiomesWeveGone.id("block/palo_verde/sapling"))), itemModels.modelOutput)));
        blockModels.createTrivialBlock(BWGWood.PALO_VERDE_SAPLING.getPottedBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, material(BiomesWeveGone.id("block/palo_verde/sapling")))));


        blockModels.createTrivialBlock(BWGWood.PALO_VERDE_LEAVES.get(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, material(BiomesWeveGone.id("block/palo_verde/leaves")))));
        blockItemModel(blockModels, BWGWood.PALO_VERDE_LEAVES.get());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.FORAGERS_TABLE.get(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(BWGBlocks.FORAGERS_TABLE.get(), new TextureMapping()
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(Blocks.BEEHIVE, "_end"))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_top"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_side"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_side"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_front"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_front"))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_top")), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGBlocks.FORAGERS_TABLE.get());

        BWGBlocks.BLOCKS.forEach(block -> {
            Block b = block.get();
            if (b instanceof StemBlock)
            blockModels.blockStateOutput
                    .accept(
                            MultiVariantGenerator.dispatch(b)
                                    .with(
                                            PropertyDispatch.initial(BlockStateProperties.AGE_7)
                                                    .generate(
                                                            integer -> BlockModelGenerators.plainVariant(ModelTemplates.STEMS[integer].create(b, TextureMapping.stem(Blocks.PUMPKIN_STEM), blockModels.modelOutput))
                                                    )
                                    )
                    );
            else if (b instanceof AttachedStemBlock)
                blockModels.blockStateOutput
                        .accept(
                                MultiVariantGenerator.dispatch(b, BlockModelGenerators.plainVariant(ModelTemplates.ATTACHED_STEM.create(b, TextureMapping.attachedStem(Blocks.PUMPKIN_STEM, Blocks.ATTACHED_PUMPKIN_STEM), blockModels.modelOutput)))
                                        .with(
                                                PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
                                                        .select(Direction.WEST, BlockModelGenerators.NOP)
                                                        .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_270)
                                                        .select(Direction.NORTH, BlockModelGenerators.Y_ROT_90)
                                                        .select(Direction.EAST, BlockModelGenerators.Y_ROT_180)
                                        )
                        );
            else if (b instanceof LanternBlock) {
                blockModels.createLantern(b);
            } else if (b instanceof GlowCaneBlock) {
                blockModels.createTrivialBlock(b, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.TINTED_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, TextureMapping.getBlockTexture(b))));
            } else if (b instanceof FluorescentCattailPlantBlock) {
                blockModels.blockStateOutput.accept(
                        MultiVariantGenerator.dispatch(b)
                                .with(
                                        PropertyDispatch.initial(BlockStateProperties.DOUBLE_BLOCK_HALF, FluorescentCattailPlantBlock.COLOR)
                                                .generate((half, color) -> {
                                                    if (half == DoubleBlockHalf.UPPER) {
                                                        if (color == ColorProperty.NO_COLOR) return BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty())
                                                                .create(b, new TextureMapping().putForced(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.FLUORESCENT_CATTAIL_SPROUT.get())), blockModels.modelOutput)));
                                                        else return BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty())
                                                                .create(ModelLocationUtils.getModelLocation(b, "_" + color.getSerializedName()), new TextureMapping().putForced(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.FLUORESCENT_CATTAIL_SPROUT.get(), "_" + color.getSerializedName())), blockModels.modelOutput)));
                                                    }
                                                    else return BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.CATTAIL.get(), "_bottom")));
                                                })
                                )
                );
            } else if (b instanceof CattailPlantBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(b).with(
                                PropertyDispatch.initial(BlockStateProperties.DOUBLE_BLOCK_HALF)
                                        .select(DoubleBlockHalf.LOWER, BlockModelGenerators.createRotatedVariants(blockModels.plainModel(ModelLocationUtils.getModelLocation(b, "_bottom"))))
                                        .select(DoubleBlockHalf.UPPER, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty())
                                                .create(b, new TextureMapping().putForced(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_SPROUT.get())), blockModels.modelOutput)))))
                );
            } else if (b instanceof DoublePlantBlock) {
                blockModels.createDoublePlant(b, BlockModelGenerators.PlantType.NOT_TINTED);
                if (!(b.builtInRegistryHolder().key().identifier().toLanguageKey().contains("pitcher_plant")))
                    blockModels.registerSimpleFlatItemModel(b, "_top");
                else basicItem(itemModels, b.asItem());
            } else if (b instanceof WhitePuffballBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(b)
                        .with(PropertyDispatch.initial(BlockStateProperties.AGE_3)
                                .generate(
                                        integer -> BlockModelGenerators.plainVariant(BiomesWeveGone.id("block/white_puffball_stage" + integer))
                                )));
            } else if (b instanceof BWGFruitBlock || b instanceof SweetBerryBushBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(b)
                        .with(
                                PropertyDispatch.initial(BlockStateProperties.AGE_3)
                                        .generate(
                                                integer -> BlockModelGenerators.plainVariant(blockModels.createSuffixedVariant(b, "_stage" + integer, ModelTemplates.CROSS, TextureMapping::cross))
                                        )
                        ));
            } else if (b instanceof HydrangeaHedgeBlock) {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(b, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(b))));
                blockItemModel(blockModels, b);
            } else if (b instanceof FlowerBlock || b instanceof BoneMealGrassBlock || b instanceof MushroomBlock || b instanceof ShrubBlock || b instanceof BWGPlacementBushBlock) {
                blockModels.createTrivialBlock(b, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, TextureMapping.getBlockTexture(b))));
                itemModels.itemModelOutput.accept(b.asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(b.asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(b)), itemModels.modelOutput)));
            } else if (b instanceof FlowerPotBlock flowerPotBlock) {
                if (flowerPotBlock == BWGBlocks.WHITE_PUFFBALL.getPottedBlock())
                    blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(flowerPotBlock, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(b))));
                else blockModels.createTrivialBlock(flowerPotBlock, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, material(ModelLocationUtils.getModelLocation(flowerPotBlock.getPotted())))));
            } else if (b instanceof LilyPadBlock || b instanceof CattailSproutBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(b, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(b)))));
            } else if (b instanceof DirtPathBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(b, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(TexturedModel.createDefault(TextureMapping::cubeBottomTop, ModelTemplates.CUBE_BOTTOM_TOP)
                        .updateTexture(textureMapping -> textureMapping
                                .putForced(TextureSlot.PARTICLE, material(BiomesWeveGone.id("block/" + BuiltInRegistries.BLOCK.getKey(b).getPath().replace("_path", ""))))
                                .put(TextureSlot.BOTTOM, material(BiomesWeveGone.id("block/" + BuiltInRegistries.BLOCK.getKey(b).getPath().replace("_path", ""))))
                                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(b, "_side"))
                                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.DIRT_PATH, "_top")))
                                .updateTemplate(modelTemplate -> modelTemplate.extend().parent(mcLocation("block/dirt_path")).build()).create(b, blockModels.modelOutput)))));
                blockItemModel(blockModels, b);
            } else if (b instanceof HugeMushroomBlock) {
                blockModels.createMushroomBlock(b);
            } else if (b instanceof RotatedPillarBlock) {
                blockModels.createTrivialBlock(b, TexturedModel.COLUMN.updateTexture(textureMapping -> textureMapping.put(TextureSlot.END, TextureMapping.getBlockTexture(b, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(b))));
                blockItemModel(blockModels, b);
            } else if (b  instanceof BWGFarmLandBlock farmLandBlock) {
                createFarmland(blockModels, farmLandBlock);
                blockItemModel(blockModels, b);
            } else if (b instanceof FlowerBedBlock)
                createFlowerBed(blockModels, b);
            else if (b instanceof BushBlock && (!(b instanceof FlatVegetationBlock))) {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(b, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(b))));
                itemModels.itemModelOutput.accept(b.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(b)));
            }
        });

        blockModels.createTrivialBlock(BWGBlocks.BOREALIS_ICE.get(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, TextureMapping.getBlockTexture(BWGBlocks.BOREALIS_ICE.get()))));
        itemModels.itemModelOutput.accept(BWGBlocks.BOREALIS_ICE.get().asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(BWGBlocks.BOREALIS_ICE.get()), new BorealisIceColorSource(-1)));

        blockModels.createTrivialBlock(BWGBlocks.PACKED_BOREALIS_ICE.get(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, TextureMapping.getBlockTexture(BWGBlocks.PACKED_BOREALIS_ICE.get()))));
        itemModels.itemModelOutput.accept(BWGBlocks.PACKED_BOREALIS_ICE.get().asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(BWGBlocks.PACKED_BOREALIS_ICE.get()), new BorealisIceColorSource(-1)));

        createSlabAndStairs(blockModels, itemModels, BWGBlocks.CATTAIL_THATCH_SLAB.get(), BWGBlocks.CATTAIL_THATCH_STAIRS.get(), BWGBlocks.CATTAIL_THATCH.get(), new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_THATCH.get())));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.CATTAIL_THATCH_CARPET.get(), BlockModelGenerators.plainVariant(ModelTemplates.CARPET.create(BWGBlocks.CATTAIL_THATCH_CARPET.get(), new TextureMapping().put(TextureSlot.WOOL, TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_THATCH.get(), "_top")), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGBlocks.CATTAIL_THATCH_CARPET.get());

        TextureMapping textureMapping = TextureMapping.column(BWGBlocks.PALE_PUMPKIN.get());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.PALE_PUMPKIN.get(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGBlocks.PALE_PUMPKIN.get()))));
        blockModels.createPumpkinVariant(BWGBlocks.CARVED_PALE_PUMPKIN.get(), textureMapping);
        blockModels.createPumpkinVariant(BWGBlocks.PALE_JACK_O_LANTERN.get(), textureMapping);
        blockItemModel(blockModels, BWGBlocks.PALE_PUMPKIN.get());
        blockItemModel(blockModels, BWGBlocks.CARVED_PALE_PUMPKIN.get());
        blockItemModel(blockModels, BWGBlocks.PALE_JACK_O_LANTERN.get());

        // Define texture mappings
        Identifier unoccupiedModel = TexturedModel.createDefault(TextureMapping::defaultTexture, ModelTemplates.create(TextureSlot.FRONT)).updateTexture(textureMapping1 ->  textureMapping1.put(TextureSlot.FRONT, textureMapping1.getBlockTexture(BWGBlocks.PUMPKIN_BURROW.get()))).updateTemplate(modelTemplate -> modelTemplate.extend().parent(mcLocation("block/carved_pumpkin")).build()).create(BWGBlocks.PUMPKIN_BURROW.get(), blockModels.modelOutput);
        Identifier occupiedModel = TexturedModel.createDefault(TextureMapping::defaultTexture, ModelTemplates.create(TextureSlot.FRONT)).updateTexture(textureMapping1 ->  textureMapping1.put(TextureSlot.FRONT, textureMapping1.getBlockTexture(BWGBlocks.PUMPKIN_BURROW.get(), "_occupied"))).updateTemplate(modelTemplate -> modelTemplate.extend().parent(mcLocation("block/carved_pumpkin")).build()).createWithSuffix(BWGBlocks.PUMPKIN_BURROW.get(), "_occupied", blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BWGBlocks.PUMPKIN_BURROW.get())
                .with(PropertyDispatch.initial(PumpkinBurrowBlock.OCCUPIED).select(false, BlockModelGenerators.plainVariant(unoccupiedModel)).select(true, BlockModelGenerators.plainVariant(occupiedModel))).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.WITCH_HAZEL_BLOSSOM.get(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGBlocks.WITCH_HAZEL_BLOSSOM.get()))));
        blockItemModel(blockModels, BWGBlocks.WITCH_HAZEL_BLOSSOM.get());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGWood.SPIRIT_ROOTS.get(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGWood.SPIRIT_ROOTS.get()))));
        blockItemModel(blockModels, BWGWood.SPIRIT_ROOTS.get());

        BWGSandSet.getSandSets().forEach(bwgSandSet -> {
            Material sandStoneTop = TextureMapping.getBlockTexture(bwgSandSet.getSandstone(), "_top");

            blockModels.createTrivialBlock(bwgSandSet.getSandstone(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(textureMapping1 -> {
                textureMapping1.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(bwgSandSet.getSandstone(), "_bottom"));
                textureMapping1.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getSandstone()));
                textureMapping1.put(TextureSlot.TOP, sandStoneTop);
            }));
            blockItemModel(blockModels, bwgSandSet.getSandstone());

            TextureMapping sandstone = new TextureMapping()
                    .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(bwgSandSet.getSandstone(), "_bottom"))
                    .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getSandstone()))
                    .put(TextureSlot.TOP, sandStoneTop);
            createSlabAndStairs(blockModels, itemModels, bwgSandSet.getSandstoneSlab(), bwgSandSet.getSandstoneStairs(), bwgSandSet.getSandstone(), sandstone);
            createWall(blockModels, itemModels, bwgSandSet.getSandstoneWall(), bwgSandSet.getSandstone());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(bwgSandSet.getChiseledSandstone(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(bwgSandSet.getChiseledSandstone(), new TextureMapping()
                    .put(TextureSlot.END, sandStoneTop)
                    .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getChiseledSandstone())), blockModels.modelOutput))));
            blockItemModel(blockModels, bwgSandSet.getChiseledSandstone());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(bwgSandSet.getSmoothSandstone(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ALL.create(bwgSandSet.getSmoothSandstone(), new TextureMapping().put(TextureSlot.ALL, sandStoneTop), blockModels.modelOutput))));
            blockItemModel(blockModels, bwgSandSet.getSmoothSandstone());

            createSlabAndStairs(blockModels, itemModels, bwgSandSet.getSmoothSandstoneSlab(), bwgSandSet.getSmoothSandstoneStairs(), bwgSandSet.getSmoothSandstone(), new TextureMapping().put(TextureSlot.ALL, sandStoneTop));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(bwgSandSet.getCutSandstone(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(bwgSandSet.getCutSandstone(), new TextureMapping()
                    .put(TextureSlot.END, sandStoneTop)
                    .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getCutSandstone())), blockModels.modelOutput))));
            blockItemModel(blockModels, bwgSandSet.getCutSandstone());
            createSlab(blockModels, itemModels, bwgSandSet.getCutSandstoneSlab(), bwgSandSet.getCutSandstone(), new TextureMapping()
                    .put(TextureSlot.BOTTOM, sandStoneTop)
                    .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getCutSandstone()))
                    .put(TextureSlot.TOP, sandStoneTop));
        });

        createGrassBlockModel(blockModels, BWGBlocks.LUSH_GRASS_BLOCK.get(), BWGBlocks.LUSH_DIRT.get());
        createGrassBlockModel(blockModels, BWGBlocks.OVERGROWN_DACITE.get(), BWGBlocks.DACITE_SET.getBase());
        createGrassBlockModel(blockModels, BWGBlocks.WHITE_OVERGROWN_DACITE.get(), BWGBlocks.WHITE_DACITE_SET.getBase());
        createGrassBlockModel(blockModels, BWGBlocks.OVERGROWN_STONE.get(), Blocks.STONE);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.PODZOL_DACITE.get(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(BWGBlocks.PODZOL_DACITE.get(),
            new TextureMapping().put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(BWGBlocks.DACITE_SET.getBase()))
            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(BWGBlocks.PODZOL_DACITE.get()))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.PODZOL, "_top")), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGBlocks.PODZOL_DACITE.get());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.WHITE_PODZOL_DACITE.get(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(BWGBlocks.WHITE_PODZOL_DACITE.get(),
                new TextureMapping().put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(BWGBlocks.WHITE_DACITE_SET.getBase()))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(BWGBlocks.WHITE_PODZOL_DACITE.get()))
                        .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.PODZOL, "_top")), blockModels.modelOutput))));
        blockItemModel(blockModels, BWGBlocks.WHITE_PODZOL_DACITE.get());

        blockModels.registerSimpleTintedItemModel(BWGBlocks.POISON_IVY.get(), ModelTemplates.FLAT_ITEM.create(BWGBlocks.POISON_IVY.get(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.POISON_IVY.get())), itemModels.modelOutput), new FoliageColorSource());
        basicItem(itemModels, BWGBlocks.SKYRIS_VINE.get().asItem());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.BARREL_CACTUS.get(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGBlocks.BARREL_CACTUS.get()))));
        itemModels.itemModelOutput.accept(BWGBlocks.BARREL_CACTUS.get().asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.BARREL_CACTUS.get().asItem())));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.FLOWERING_BARREL_CACTUS.get(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGBlocks.FLOWERING_BARREL_CACTUS.get()))));
        itemModels.itemModelOutput.accept(BWGBlocks.FLOWERING_BARREL_CACTUS.get().asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.FLOWERING_BARREL_CACTUS.get().asItem())));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BWGBlocks.CARVED_BARREL_CACTUS.get())
                .with(PropertyDispatch.initial(CarvedBarrelCactusBlock.LIQUID)
                        .select(LiquidType.EMPTY, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGBlocks.CARVED_BARREL_CACTUS.get())))
                        .select(LiquidType.WATER, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGBlocks.CARVED_BARREL_CACTUS.get(), "_water")))
                        .select(LiquidType.HONEY, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(BWGBlocks.CARVED_BARREL_CACTUS.get(), "_honey")))));
        blockItemModel(blockModels, BWGBlocks.CARVED_BARREL_CACTUS.get());

        Identifier witchHazelBranch = ModelTemplates.CORAL_WALL_FAN.create(BWGBlocks.WITCH_HAZEL_BRANCH.get(), new TextureMapping().put(TextureSlot.FAN, TextureMapping.getBlockTexture(BWGBlocks.WITCH_HAZEL_BRANCH.get())), blockModels.modelOutput);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BWGBlocks.WITCH_HAZEL_BRANCH.get(), BlockModelGenerators.plainVariant(witchHazelBranch)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));

        itemModels.itemModelOutput.accept(BWGBlocks.WITCH_HAZEL_BRANCH.get().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.WITCH_HAZEL_BRANCH.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.WITCH_HAZEL_BRANCH.get())), itemModels.modelOutput)));

        multiVariantRotatiableBlock(blockModels, BWGBlocks.CLOVER_PATCH.get(), 4);

        itemModels.itemModelOutput.accept(BWGBlocks.CLOVER_PATCH.get().asItem(), ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.CLOVER_PATCH.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.CLOVER_PATCH.get())), itemModels.modelOutput), new GrassColorSource()));

        multiVariantRotatiableBlock(blockModels, BWGBlocks.FLOWER_PATCH.get(), 3);

        itemModels.itemModelOutput.accept(BWGBlocks.FLOWER_PATCH.get().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.FLOWER_PATCH.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.FLOWER_PATCH.get())), itemModels.modelOutput)));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BWGBlocks.LEAF_PILE.get(), BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.LEAF_PILE.get())))));
        itemModels.itemModelOutput.accept(BWGBlocks.LEAF_PILE.get().asItem(), ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.LEAF_PILE.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.LEAF_PILE.get())), itemModels.modelOutput), new FoliageColorSource()));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BWGBlocks.SHELF_FUNGI.get())
                .with(
                        PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING)
                                .select(Direction.NORTH, BlockModelGenerators.variants(blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get())), blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get(), "2"))))
                                .select(Direction.SOUTH, BlockModelGenerators.variants(blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get())), blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get(), "2"))).with(blockModels.Y_ROT_180))
                                .select(Direction.EAST, BlockModelGenerators.variants(blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get())), blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get(), "2"))).with(blockModels.Y_ROT_90))
                                .select(Direction.WEST, BlockModelGenerators.variants(blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get())), blockModels.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get(), "2"))).with(blockModels.Y_ROT_270))
                ));

        itemModels.itemModelOutput.accept(BWGBlocks.SHELF_FUNGI.get().asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get().asItem())));

        //blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.CATTAIL_SPROUT.get(), new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty()).create(BWGBlocks.CATTAIL_SPROUT.get(), new TextureMapping().put(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_SPROUT.get())), blockModels.modelOutput)));

        BWGItems.SIMPLE_ITEMS.forEach(item -> basicItem(itemModels, item.get()));

        basicItem(itemModels, BWGItems.BWG_LOGO.get());

        //TODO: SpawnEggs These now Require there own custom .png file

        itemModels.itemModelOutput.accept(BWGItems.CATTAIL_SPROUT.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.CATTAIL_SPROUT.get(), TextureMapping.layer0(material(BiomesWeveGone.id("item/cattails"))), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.FLUORESCENT_CATTAIL_SPROUT.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.FLUORESCENT_CATTAIL_SPROUT.get(), TextureMapping.layer0(material(BiomesWeveGone.id("item/fluorescent_cattails"))), itemModels.modelOutput)));

        itemModels.itemModelOutput.accept(BWGItems.TINY_LILY_PADS.get(), ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(BWGItems.TINY_LILY_PADS.get()), TextureMapping.layer0(BWGBlocks.TINY_LILY_PADS.get()), itemModels.modelOutput), new GrassColorSource()));
        itemModels.itemModelOutput.accept(BWGItems.FLOWERING_TINY_LILY_PADS.get(), ItemModelUtils.tintedModel(ModelTemplates.TWO_LAYERED_ITEM.create(ModelLocationUtils.getModelLocation(BWGItems.FLOWERING_TINY_LILY_PADS.get()), TextureMapping.layered(TextureMapping.getBlockTexture(BWGBlocks.TINY_LILY_PADS.get()), TextureMapping.getBlockTexture(BWGBlocks.TINY_LILY_PADS.get(), "_flower_overlay")), blockModels.modelOutput), new GrassColorSource()));
        blockModels.createFlatItemModelWithBlockTexture(BWGItems.WATER_SILK.get(), BWGBlocks.WATER_SILK.get());
        itemModels.declareCustomModelItem(BWGItems.WATER_SILK.get());

        itemModels.generateFlatItem(BWGItems.MUSIC_DISC_PIXIE_CLUB.get(), ModelTemplates.MUSIC_DISC);
        itemModels.generateFlatItem(BWGItems.MUSIC_DISC_BETTER_DAYS.get(), ModelTemplates.MUSIC_DISC);

        ItemModel.Unbaked occupiedItem = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.PUMPKIN_BURROW.get(), "_occupied"));
        ItemModel.Unbaked unoccupiedItem = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.PUMPKIN_BURROW.get()));

        itemModels.itemModelOutput.accept(BWGItems.PUMPKIN_BURROW.get(), ItemModelUtils.conditional(
                ItemModelUtils.hasComponent(BWGDataComponents.PUMPKIN_WARDEN.get()),
                occupiedItem,
                unoccupiedItem
        ));

        itemModels.itemModelOutput.accept(BWGItems.WREATH.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.WREATH.get(), TextureMapping.layer0(material(BiomesWeveGone.id("block/default_wreath"))), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.HOLLY_WREATH.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.HOLLY_WREATH.get(), TextureMapping.layer0(material(BiomesWeveGone.id("block/holly_wreath"))), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.MUSHROOM_WREATH.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.MUSHROOM_WREATH.get(), TextureMapping.layer0(material(BiomesWeveGone.id("block/mushroom_wreath"))), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.ODDION_WREATH.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.ODDION_WREATH.get(), TextureMapping.layer0(material(BiomesWeveGone.id("block/oddion_wreath"))), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.PETAL_WREATH.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.PETAL_WREATH.get(), TextureMapping.layer0(material(BiomesWeveGone.id("block/petal_wreath"))), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.ROSY_WREATH.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.ROSY_WREATH.get(), TextureMapping.layer0(material(BiomesWeveGone.id("block/rosy_wreath"))), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.WINTER_ROSY_WREATH.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.WINTER_ROSY_WREATH.get(), TextureMapping.layer0(material(BiomesWeveGone.id("block/winter_rosy_wreath"))), itemModels.modelOutput)));
    }

    private void blockItemModel(BlockModelGenerators blockModels, Block block) {
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    private void basicItem(ItemModelGenerators itemModels, Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void createFarmland(BlockModelGenerators blockModelGenerators, BWGFarmLandBlock block) {
        TextureMapping texturemapping = new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(block.getDirt())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block));
        TextureMapping texturemapping1 = new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(block.getDirt())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_moist"));
        Identifier Identifier = ModelTemplates.FARMLAND.create(block, texturemapping, blockModelGenerators.modelOutput);
        Identifier Identifier1 = ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(block, "_moist").sprite(), texturemapping1, blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, BlockModelGenerators.plainVariant(Identifier1), BlockModelGenerators.plainVariant(Identifier))));
    }

    private static void createSlab(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block baseBlock, TextureMapping baseTexture) {
        Identifier slabBottom = ModelTemplates.SLAB_BOTTOM.create(slab, baseTexture, blockModels.modelOutput);
        Identifier slabTop = ModelTemplates.SLAB_TOP.create(slab, baseTexture, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, BlockModelGenerators.plainVariant(slabBottom), BlockModelGenerators.plainVariant(slabTop), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(baseBlock))));
        itemModels.itemModelOutput.accept(slab.asItem(), ItemModelUtils.plainModel(slabBottom));
    }

    private static void createStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block stairs, TextureMapping baseTexture) {
        Identifier stairsStraight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, baseTexture, blockModels.modelOutput);
        Identifier stairsInner = ModelTemplates.STAIRS_INNER.create(stairs, baseTexture, blockModels.modelOutput);
        Identifier stairsOuter = ModelTemplates.STAIRS_OUTER.create(stairs, baseTexture, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, BlockModelGenerators.plainVariant(stairsInner), BlockModelGenerators.plainVariant(stairsStraight), BlockModelGenerators.plainVariant(stairsOuter)));
        itemModels.itemModelOutput.accept(stairs.asItem(), ItemModelUtils.plainModel(stairsStraight));
    }

    private static void createSlabAndStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block stairs, Block baseBlock, TextureMapping baseTexture) {
        createSlab(blockModels, itemModels, slab, baseBlock, baseTexture);
        createStairs(blockModels, itemModels, stairs, baseTexture);
    }

    private static void createWall(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block wall, Block baseBlock) {
        TextureMapping base = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(baseBlock));
        Identifier wallPost = ModelTemplates.WALL_POST.create(wall, base, blockModels.modelOutput);
        Identifier wallSide = ModelTemplates.WALL_LOW_SIDE.create(wall, base, blockModels.modelOutput);
        Identifier wallSideTall = ModelTemplates.WALL_TALL_SIDE.create(wall, base, blockModels.modelOutput);
        Identifier wallInventory = ModelTemplates.WALL_INVENTORY.create(wall, base, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createWall(wall, BlockModelGenerators.plainVariant(wallPost), BlockModelGenerators.plainVariant(wallSide), BlockModelGenerators.plainVariant(wallSideTall)));
        itemModels.itemModelOutput.accept(wall.asItem(), ItemModelUtils.plainModel(wallInventory));
    }

    private void createGrassBlockModel(BlockModelGenerators blockModels, Block grassBlock, Block dirtBlock) {
        Identifier model = new ModelTemplate(Optional.of(mcLocation("block/grass_block")), Optional.empty())
                .create(grassBlock, new TextureMapping()
                        .putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(dirtBlock))
                        .putForced(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirtBlock))
                        .putForced(TextureSlot.TOP, TextureMapping.getBlockTexture(grassBlock, "_top"))
                        .putForced(TextureSlot.SIDE, TextureMapping.getBlockTexture(grassBlock, "_side"))
                        .putForced(TextureSlot.create("overlay"), TextureMapping.getBlockTexture(grassBlock, "_side_overlay")), blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(grassBlock)
                .with(PropertyDispatch.initial(BlockStateProperties.SNOWY)
                        .select(false,
                                BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(model)))
                        .select(true, BlockModelGenerators.plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(
                                ModelLocationUtils.getModelLocation(grassBlock, "_snow"),
                                new TextureMapping()
                                        .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirtBlock))
                                        .putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(dirtBlock))
                                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(grassBlock, "_snow_side"))
                                        .put(TextureSlot.TOP, TextureMapping.getBlockTexture(grassBlock, "_top")),
                                blockModels.modelOutput)))
                ));

        blockModels.registerSimpleTintedItemModel(grassBlock, ModelLocationUtils.getModelLocation(grassBlock), new GrassColorSource());
    }

    private void createFlowerBed(BlockModelGenerators blockModel, Block flowerBedBlock) {
        blockModel.registerSimpleFlatItemModel(flowerBedBlock.asItem());
        MultiVariant Identifier = BlockModelGenerators.plainVariant(TexturedModel.FLOWERBED_1.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput));
        MultiVariant Identifier1 = BlockModelGenerators.plainVariant(TexturedModel.FLOWERBED_2.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput));
        MultiVariant Identifier2 = BlockModelGenerators.plainVariant(TexturedModel.FLOWERBED_3.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput));
        MultiVariant Identifier3 = BlockModelGenerators.plainVariant(TexturedModel.FLOWERBED_4.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput));

        blockModel.createSegmentedBlock(flowerBedBlock, Identifier, BlockModelGenerators.FLOWER_BED_MODEL_1_SEGMENT_CONDITION,
                Identifier1, BlockModelGenerators.FLOWER_BED_MODEL_2_SEGMENT_CONDITION,
                Identifier2, BlockModelGenerators.FLOWER_BED_MODEL_3_SEGMENT_CONDITION,
                Identifier3, BlockModelGenerators.FLOWER_BED_MODEL_4_SEGMENT_CONDITION);
    }

    private void multiVariantRotatiableBlock(BlockModelGenerators blockModelGenerators, Block block, int variantCount) {
        Variant[] variants = new Variant[variantCount * 4];
        for (int i = 1; i <= variantCount; i++) {
            String suffix = (i == 1) ? "" : String.valueOf(i);
            Identifier modelLocation = ModelLocationUtils.getModelLocation(block, suffix);
            variants[(i - 1) * 4] = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(modelLocation)).variants().unwrap().getFirst().value();
            variants[((i - 1) * 4) + 1] = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(modelLocation)).variants().unwrap().get(1).value();
            variants[((i - 1)* 4) + 2] = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(modelLocation)).variants().unwrap().get(2).value();
            variants[((i - 1) * 4) + 3] = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(modelLocation)).variants().unwrap().get(3).value();
        }

        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, blockModelGenerators.variants(variants)));
    }

    private static Material material(Identifier identifier) {
        return new Material(identifier);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.empty();
    }
}
