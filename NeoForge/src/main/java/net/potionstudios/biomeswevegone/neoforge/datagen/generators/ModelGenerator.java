package net.potionstudios.biomeswevegone.neoforge.datagen.generators;

import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.client.color.item.BorealisIceColorSource;
import net.potionstudios.biomeswevegone.client.color.item.FoliageColorSource;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.BWGPlacementBushBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.HydrangeaHedgeBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.ShrubBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.WhitePuffballBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.cactus.CarvedBarrelCactusBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.cactus.LiquidType;
import net.potionstudios.biomeswevegone.world.level.block.plants.tree.fruit.BWGFruitBlock;
import net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.AloeVeraBlock;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class ModelGenerator extends ModelProvider {
    public ModelGenerator(PackOutput arg) {
        super(arg, BiomesWeveGone.MOD_ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
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
            ResourceLocation Planks = BiomesWeveGone.id(folder + "planks");
            blockModels.createTrivialBlock(woodSet.planks(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, Planks)));
            blockItemModel(blockModels, woodSet.planks());

            TextureMapping planks = new TextureMapping().put(TextureSlot.ALL, Planks);

            createSlabAndStairs(blockModels, itemModels, woodSet.slab(), woodSet.stairs(), woodSet.planks(), planks);

            blockModels.blockStateOutput.accept(BlockModelGenerators.createButton(woodSet.button(), ModelTemplates.BUTTON.create(woodSet.button(), planks, blockModels.modelOutput), ModelTemplates.BUTTON_PRESSED.create(woodSet.planks(), planks, blockModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.button().asItem(), ItemModelUtils.plainModel(ModelTemplates.BUTTON_INVENTORY.create(woodSet.button().asItem(), planks, itemModels.modelOutput)));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createFence(woodSet.fence(), ModelTemplates.FENCE_POST.create(woodSet.fence(), planks, blockModels.modelOutput), ModelTemplates.FENCE_SIDE.create(woodSet.fence(), planks, blockModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.fence().asItem(), ItemModelUtils.plainModel(ModelTemplates.FENCE_INVENTORY.create(woodSet.fence().asItem(), planks, itemModels.modelOutput)));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createFenceGate(woodSet.fenceGate(), ModelTemplates.FENCE_GATE_OPEN.create(woodSet.fenceGate(), planks, blockModels.modelOutput), ModelTemplates.FENCE_GATE_CLOSED.create(woodSet.fenceGate(), planks, blockModels.modelOutput), ModelTemplates.FENCE_GATE_WALL_OPEN.create(woodSet.fenceGate(), planks, blockModels.modelOutput), ModelTemplates.FENCE_GATE_WALL_CLOSED.create(woodSet.fenceGate(), planks, blockModels.modelOutput), false));
            blockItemModel(blockModels, woodSet.fenceGate());

            TextureMapping door = new TextureMapping().put(TextureSlot.TOP, BiomesWeveGone.id(folder + "door_top")).put(TextureSlot.BOTTOM, BiomesWeveGone.id(folder + "door_bottom"));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createDoor(woodSet.door(), 
                    ModelTemplates.DOOR_BOTTOM_LEFT.create(woodSet.door(), door, blockModels.modelOutput),
                    ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(woodSet.door(), door, blockModels.modelOutput),
                    ModelTemplates.DOOR_BOTTOM_RIGHT.create(woodSet.door(), door, blockModels.modelOutput),
                    ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(woodSet.door(), door, blockModels.modelOutput),
                    ModelTemplates.DOOR_TOP_LEFT.create(woodSet.door(), door, blockModels.modelOutput),
                    ModelTemplates.DOOR_TOP_LEFT_OPEN.create(woodSet.door(), door, blockModels.modelOutput),
                    ModelTemplates.DOOR_TOP_RIGHT.create(woodSet.door(), door, blockModels.modelOutput),
                    ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(woodSet.door(), door, blockModels.modelOutput)));

            TextureMapping trapdoor = new TextureMapping().put(TextureSlot.TEXTURE, BiomesWeveGone.id(folder + "trapdoor"));
            ResourceLocation trapdoorBottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput);
            blockModels.blockStateOutput.accept(BlockModelGenerators.createOrientableTrapdoor(woodSet.trapdoor(),
                    ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput),
                    trapdoorBottom,
                    ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput)));

            blockModels.itemModelOutput.accept(woodSet.trapdoor().asItem(), ItemModelUtils.plainModel(trapdoorBottom));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(woodSet.pressurePlate(),
                    ModelTemplates.PRESSURE_PLATE_UP.create(woodSet.pressurePlate(), planks, blockModels.modelOutput),
                    ModelTemplates.PRESSURE_PLATE_DOWN.create(woodSet.pressurePlate(), planks, blockModels.modelOutput)));
            blockItemModel(blockModels, woodSet.pressurePlate());

            ResourceLocation Log = BiomesWeveGone.id(folder + woodSet.logStemEnum().getName());
            ResourceLocation LogTop = BiomesWeveGone.id(folder + woodSet.logStemEnum().getName() + "_top");

            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.logstem(),
                    ModelTemplates.CUBE_COLUMN.create(woodSet.logstem(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput),
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.logstem(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput)));
            blockItemModel(blockModels, woodSet.logstem());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.wood(),
                    ModelTemplates.CUBE_COLUMN.create(woodSet.wood(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput),
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.wood(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput)));
            blockItemModel(blockModels, woodSet.wood());

            ResourceLocation StrippedLog = BiomesWeveGone.id(folder + "stripped_" + woodSet.logStemEnum().getName());
            ResourceLocation StrippedLogTop = BiomesWeveGone.id(folder + "stripped_" + woodSet.logStemEnum().getName() + "_top");
            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.strippedLogStem(),
                    ModelTemplates.CUBE_COLUMN.create(woodSet.strippedLogStem(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput),
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.strippedLogStem(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)));
            blockItemModel(blockModels, woodSet.strippedLogStem());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.strippedWood(),
                    ModelTemplates.CUBE_COLUMN.create(woodSet.strippedWood(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput),
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.strippedWood(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)));
            blockItemModel(blockModels, woodSet.strippedWood());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.sign(), ModelTemplates.PARTICLE_ONLY.create(woodSet.sign(), planks, blockModels.modelOutput)));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.wallSign(), ModelLocationUtils.getModelLocation(woodSet.sign())));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.hangingSign(), ModelTemplates.PARTICLE_ONLY.create(woodSet.hangingSign(), TextureMapping.particle(StrippedLog), blockModels.modelOutput)));
            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.wallHangingSign(), ModelLocationUtils.getModelLocation(woodSet.hangingSign())));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.bookshelf(), ModelTemplates.CUBE_COLUMN.create(woodSet.bookshelf(), new TextureMapping().put(TextureSlot.END, Planks).put(TextureSlot.SIDE, BiomesWeveGone.id("block/" + woodSet.name() + "/bookshelf")), blockModels.modelOutput)));
            blockItemModel(blockModels, woodSet.bookshelf());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.craftingTable(), ModelTemplates.CUBE.create(woodSet.craftingTable(), new TextureMapping()
                    .put(TextureSlot.DOWN, Planks)
                    .put(TextureSlot.UP, BiomesWeveGone.id(folder + "crafting_table_top"))
                    .put(TextureSlot.EAST, BiomesWeveGone.id(folder + "crafting_table_side"))
                    .put(TextureSlot.WEST, BiomesWeveGone.id(folder + "crafting_table_front"))
                    .put(TextureSlot.NORTH, BiomesWeveGone.id(folder + "crafting_table_front"))
                    .put(TextureSlot.SOUTH, BiomesWeveGone.id(folder + "crafting_table_side"))
                    .put(TextureSlot.PARTICLE, BiomesWeveGone.id(folder + "crafting_table_front")), blockModels.modelOutput)));
            blockItemModel(blockModels, woodSet.craftingTable());

            if (woodSet.leaves() != null) {
                blockModels.createTrivialBlock(woodSet.leaves(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, BiomesWeveGone.id(folder + "leaves"))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout_mipped")).build()));
                if (woodSet.name() == "willow" || woodSet.name() == "maple" || woodSet.name() == "cypress")
                    itemModels.itemModelOutput.accept(woodSet.leaves().asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(woodSet.leaves()), new FoliageColorSource()));
                else blockItemModel(blockModels, woodSet.leaves());
            }
            if (woodSet.sapling() != null) {
                blockModels.createTrivialBlock(woodSet.sapling().getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, BiomesWeveGone.id(folder + "sapling"))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
                itemModels.itemModelOutput.accept(woodSet.sapling().getItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.sapling().getItem(), TextureMapping.layer0(BiomesWeveGone.id(folder + "sapling")), itemModels.modelOutput)));
            }

            itemModels.itemModelOutput.accept(woodSet.signItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.signItem(), TextureMapping.layer0(BiomesWeveGone.id("item/" + woodSet.name() + "/sign")), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.hangingSignItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.hangingSignItem(), TextureMapping.layer0(BiomesWeveGone.id("item/" + woodSet.name() + "/hanging_sign")), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.door().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.door(), TextureMapping.layer0(BiomesWeveGone.id("item/" + woodSet.name() + "/door")), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.boatItem().get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.boatItem().get(), TextureMapping.layer0(BiomesWeveGone.id("item/" + woodSet.name() + "/boat")), itemModels.modelOutput)));
            itemModels.itemModelOutput.accept(woodSet.chestBoatItem().get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.chestBoatItem().get(), TextureMapping.layer0(BiomesWeveGone.id("item/" + woodSet.name() + "/chest_boat")), itemModels.modelOutput)));
        });

        blockModels.createTrivialBlock(BWGWood.WHITE_SAKURA_SAPLING.getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, BiomesWeveGone.id("block/sakura/white_sapling"))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
        itemModels.itemModelOutput.accept(BWGWood.WHITE_SAKURA_SAPLING.getItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGWood.WHITE_SAKURA_SAPLING.getItem(), TextureMapping.layer0(BiomesWeveGone.id("block/sakura/white_sapling")), itemModels.modelOutput)));

        blockModels.createTrivialBlock(BWGWood.YELLOW_SAKURA_SAPLING.getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, BiomesWeveGone.id("block/sakura/yellow_sapling"))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
        itemModels.itemModelOutput.accept(BWGWood.YELLOW_SAKURA_SAPLING.getItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGWood.YELLOW_SAKURA_SAPLING.getItem(), TextureMapping.layer0(BiomesWeveGone.id("block/sakura/yellow_sapling")), itemModels.modelOutput)));

        blockModels.createTrivialBlock(BWGWood.IMBUED_BLUE_ENCHANTED_WOOD.get(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, BiomesWeveGone.id("block/blue_enchanted/imbued_wood"))));
        blockItemModel(blockModels, BWGWood.IMBUED_BLUE_ENCHANTED_WOOD.get());
        blockModels.createTrivialBlock(BWGWood.IMBUED_GREEN_ENCHANTED_WOOD.get(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, BiomesWeveGone.id("block/green_enchanted/imbued_wood"))));
        blockItemModel(blockModels, BWGWood.IMBUED_GREEN_ENCHANTED_WOOD.get());

        BWGWood.NONSET_WOOD.forEach(block -> {
            Block b = block.get();
            if (b instanceof LeavesBlock leavesBlock) {
                if (TextureMapping.getBlockTexture(leavesBlock).toString().contains("yucca")) {
                    blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(leavesBlock, ModelLocationUtils.getModelLocation(leavesBlock)));
                    itemModels.itemModelOutput.accept(leavesBlock.asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(leavesBlock), new FoliageColorSource()));
                }
                else {
                    blockModels.createTrivialBlock(leavesBlock, TexturedModel.LEAVES.updateTemplate(template -> template.extend().renderType(mcLocation("cutout_mipped")).build()));
                    blockItemModel(blockModels, leavesBlock);
                }
            } else if (b instanceof SaplingBlock sapling) {
                blockModels.createTrivialBlock(sapling, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
                blockModels.registerSimpleFlatItemModel(sapling);
            }
        });

        ResourceLocation Log = BiomesWeveGone.id("block/palo_verde/log");
        ResourceLocation LogTop = BiomesWeveGone.id("block/palo_verde/log_top");

        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.PALO_VERDE_LOG.get(),
                ModelTemplates.CUBE_COLUMN.create(BWGWood.PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput),
                ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput)));
        blockItemModel(blockModels, BWGWood.PALO_VERDE_LOG.get());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.PALO_VERDE_WOOD.get(),
                ModelTemplates.CUBE_COLUMN.create(BWGWood.PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput),
                ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput)));
        blockItemModel(blockModels, BWGWood.PALO_VERDE_WOOD.get());

        ResourceLocation StrippedLog = BiomesWeveGone.id("block/palo_verde/stripped_log");
        ResourceLocation StrippedLogTop = BiomesWeveGone.id("block/palo_verde/stripped_log_top");
        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.STRIPPED_PALO_VERDE_LOG.get(),
                ModelTemplates.CUBE_COLUMN.create(BWGWood.STRIPPED_PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput),
                ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.STRIPPED_PALO_VERDE_LOG.get(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)));
        blockItemModel(blockModels, BWGWood.STRIPPED_PALO_VERDE_LOG.get());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(BWGWood.STRIPPED_PALO_VERDE_WOOD.get(),
                ModelTemplates.CUBE_COLUMN.create(BWGWood.STRIPPED_PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput),
                ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(BWGWood.STRIPPED_PALO_VERDE_WOOD.get(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)));
        blockItemModel(blockModels, BWGWood.STRIPPED_PALO_VERDE_WOOD.get());

        blockModels.createTrivialBlock(BWGWood.PALO_VERDE_SAPLING.getBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, BiomesWeveGone.id( "block/palo_verde/sapling"))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
        itemModels.itemModelOutput.accept(BWGWood.PALO_VERDE_SAPLING.getBlock().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGWood.PALO_VERDE_SAPLING.getBlock().asItem(), TextureMapping.layer0(BiomesWeveGone.id("block/palo_verde/sapling")), itemModels.modelOutput)));

        blockModels.createTrivialBlock(BWGWood.PALO_VERDE_LEAVES.get(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, BiomesWeveGone.id("block/palo_verde/leaves"))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout_mipped")).build()));
        blockItemModel(blockModels, BWGWood.PALO_VERDE_LEAVES.get());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.FORAGERS_TABLE.get(), ModelTemplates.CUBE.create(BWGBlocks.FORAGERS_TABLE.get(), new TextureMapping()
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(Blocks.BEEHIVE, "_end"))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_top"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_side"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_side"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_front"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_front"))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(BWGBlocks.FORAGERS_TABLE.get(), "_top")), blockModels.modelOutput)));
        blockItemModel(blockModels, BWGBlocks.FORAGERS_TABLE.get());

        BWGBlocks.BLOCKS.forEach(block -> {
            Block b = block.get();
            if (b instanceof StemBlock)
                blockModels.blockStateOutput
                        .accept(
                                MultiVariantGenerator.multiVariant(b)
                                        .with(
                                                PropertyDispatch.property(BlockStateProperties.AGE_7)
                                                        .generate(
                                                                integer -> Variant.variant().with(VariantProperties.MODEL, ModelTemplates.STEMS[integer].create(b, TextureMapping.stem(Blocks.PUMPKIN_STEM), blockModels.modelOutput))
                                                        )
                                        )
                        );
            else if (b instanceof AttachedStemBlock)
                blockModels.blockStateOutput
                        .accept(
                                MultiVariantGenerator.multiVariant(b, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.ATTACHED_STEM.create(b, TextureMapping.attachedStem(Blocks.PUMPKIN_STEM, Blocks.ATTACHED_PUMPKIN_STEM), blockModels.modelOutput)))
                                        .with(
                                                PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                                                        .select(Direction.WEST, Variant.variant())
                                                        .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                                                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                                                        .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                                        )
                        );
            else if (b instanceof LanternBlock) {
                blockModels.createLantern(b);
            } else if (b instanceof GlowCaneBlock) {
                blockModels.createTrivialBlock(b, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.TINTED_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, TextureMapping.getBlockTexture(b))));
            } else if (b instanceof FluorescentCattailPlantBlock) {
                blockModels.blockStateOutput.accept(
                        MultiVariantGenerator.multiVariant(b)
                                .with(
                                        PropertyDispatch.properties(BlockStateProperties.DOUBLE_BLOCK_HALF, FluorescentCattailPlantBlock.COLOR)
                                                .generateList((half, color) -> {
                                                    if (half == DoubleBlockHalf.UPPER) {
                                                        if (color == ColorProperty.NO_COLOR) return List.of(BlockModelGenerators.createRotatedVariants(new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty())
                                                                .create(b, new TextureMapping().putForced(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.FLUORESCENT_CATTAIL_SPROUT.get())), blockModels.modelOutput)));

                                                        else return List.of(BlockModelGenerators.createRotatedVariants(new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty())
                                                                .create(ModelLocationUtils.getModelLocation(b, "_" + color.getSerializedName()), new TextureMapping().putForced(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.FLUORESCENT_CATTAIL_SPROUT.get(), "_" + color.getSerializedName())), blockModels.modelOutput)));
                                                    }
                                                    else return List.of(BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.CATTAIL.get(), "_bottom")));
                                                })
                                )
                );
            } else if (b instanceof CattailPlantBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(b).with(
                                PropertyDispatch.property(BlockStateProperties.DOUBLE_BLOCK_HALF)
                                        .select(DoubleBlockHalf.LOWER, List.of(BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(b, "_bottom"))))
                                        .select(DoubleBlockHalf.UPPER, List.of(BlockModelGenerators.createRotatedVariants(new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty())
                                                .create(b, new TextureMapping().putForced(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_SPROUT.get())), blockModels.modelOutput))))
                ));
            } else if (b instanceof DoublePlantBlock) {
                blockModels.createDoublePlant(b, BlockModelGenerators.PlantType.NOT_TINTED);
                if (!(b.builtInRegistryHolder().key().location().toLanguageKey().contains("pitcher_plant")))
                    blockModels.registerSimpleFlatItemModel(b, "_top");
                else basicItem(itemModels, b.asItem());
            } else if (b instanceof WhitePuffballBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(b)
                        .with(PropertyDispatch.property(BlockStateProperties.AGE_3)
                                .generate(
                                        integer -> Variant.variant().with(VariantProperties.MODEL, BiomesWeveGone.id("block/white_puffball_stage" + integer))
                                )));
            } else if (b instanceof BWGFruitBlock || b instanceof SweetBerryBushBlock) {
                blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(b)
                        .with(
                                PropertyDispatch.property(BlockStateProperties.AGE_3)
                                        .generate(
                                                integer -> Variant.variant().with(VariantProperties.MODEL, blockModels.createSuffixedVariant(b, "_stage" + integer, ModelTemplates.CROSS, TextureMapping::cross))
                                        )
                        ));
            } else if (b instanceof HydrangeaHedgeBlock) {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(b, ModelLocationUtils.getModelLocation(b)));
                blockItemModel(blockModels, b);
            } else if (b instanceof FlowerBlock || b instanceof BoneMealGrassBlock || b instanceof MushroomBlock  || b instanceof AloeVeraBlock || b instanceof ShrubBlock || b instanceof BWGPlacementBushBlock) {
                blockModels.createTrivialBlock(b, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, TextureMapping.getBlockTexture(b))));
                itemModels.itemModelOutput.accept(b.asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(b.asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(b)), itemModels.modelOutput)));
            } else if (b instanceof FlowerPotBlock flowerPotBlock) {
                if (flowerPotBlock == BWGBlocks.WHITE_PUFFBALL.getPottedBlock())
                    blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(flowerPotBlock, ModelLocationUtils.getModelLocation(b)));
                else blockModels.createTrivialBlock(flowerPotBlock, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, ModelLocationUtils.getModelLocation(flowerPotBlock.getPotted()))));
            } else if (b instanceof WaterlilyBlock || b instanceof CattailSproutBlock) {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(b, ModelLocationUtils.getModelLocation(b)));
            } else if (b instanceof DirtPathBlock) {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(b, TexturedModel.createDefault(TextureMapping::cubeBottomTop, ModelTemplates.CUBE_BOTTOM_TOP)
                        .updateTexture(textureMapping -> textureMapping
                                .putForced(TextureSlot.PARTICLE, BiomesWeveGone.id("block/" + BuiltInRegistries.BLOCK.getKey(b).getPath().replace("_path", "")))
                                .put(TextureSlot.BOTTOM, BiomesWeveGone.id("block/" + BuiltInRegistries.BLOCK.getKey(b).getPath().replace("_path", "")))
                                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(b, "_side"))
                                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.DIRT_PATH, "_top")))
                        .updateTemplate(modelTemplate -> modelTemplate.extend().parent(mcLocation("block/dirt_path")).build()).create(b, blockModels.modelOutput)));
                blockItemModel(blockModels, b);
            } else if (b instanceof HugeMushroomBlock) {
                blockModels.createMushroomBlock(b);
            } else if (b instanceof RotatedPillarBlock) {
                blockModels.createTrivialBlock(b, TexturedModel.COLUMN.updateTexture(textureMapping -> textureMapping.put(TextureSlot.END, TextureMapping.getBlockTexture(b, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(b))));
                blockItemModel(blockModels, b);
            } else if (b  instanceof BWGFarmLandBlock farmLandBlock) {
                createFarmland(blockModels, farmLandBlock);
                blockItemModel(blockModels, b);
            } else if (b instanceof PinkPetalsBlock)
                createFlowerBed(blockModels, b);
            else if (b instanceof BushBlock && (!(b instanceof FlatVegetationBlock))) {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(b, ModelLocationUtils.getModelLocation(b)));
                itemModels.itemModelOutput.accept(b.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(b)));
            }
        });

        blockModels.createTrivialBlock(BWGBlocks.BOREALIS_ICE.get(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, TextureMapping.getBlockTexture(BWGBlocks.BOREALIS_ICE.get()))).updateTemplate(modelTemplate -> modelTemplate.extend().renderType(mcLocation("translucent")).build()));
        itemModels.itemModelOutput.accept(BWGBlocks.BOREALIS_ICE.get().asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(BWGBlocks.BOREALIS_ICE.get()), new BorealisIceColorSource(-1)));

        blockModels.createTrivialBlock(BWGBlocks.PACKED_BOREALIS_ICE.get(), TexturedModel.LEAVES.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, TextureMapping.getBlockTexture(BWGBlocks.PACKED_BOREALIS_ICE.get()))).updateTemplate(modelTemplate -> modelTemplate.extend().renderType(mcLocation("translucent")).build()));
        itemModels.itemModelOutput.accept(BWGBlocks.PACKED_BOREALIS_ICE.get().asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(BWGBlocks.PACKED_BOREALIS_ICE.get()), new BorealisIceColorSource(-1)));

        createSlabAndStairs(blockModels, itemModels, BWGBlocks.CATTAIL_THATCH_SLAB.get(), BWGBlocks.CATTAIL_THATCH_STAIRS.get(), BWGBlocks.CATTAIL_THATCH.get(), new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_THATCH.get())));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.CATTAIL_THATCH_CARPET.get(), ModelTemplates.CARPET.create(BWGBlocks.CATTAIL_THATCH_CARPET.get(), new TextureMapping().put(TextureSlot.WOOL, TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_THATCH.get(), "_top")), blockModels.modelOutput)));
        blockItemModel(blockModels, BWGBlocks.CATTAIL_THATCH_CARPET.get());

        TextureMapping textureMapping = TextureMapping.column(BWGBlocks.PALE_PUMPKIN.get());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.PALE_PUMPKIN.get(), ModelLocationUtils.getModelLocation(BWGBlocks.PALE_PUMPKIN.get())));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BWGBlocks.CARVED_PALE_PUMPKIN.get(), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_ORIENTABLE.create(BWGBlocks.CARVED_PALE_PUMPKIN.get(), textureMapping.copyAndUpdate(TextureSlot.FRONT, TextureMapping.getBlockTexture(BWGBlocks.CARVED_PALE_PUMPKIN.get())), blockModels.modelOutput))).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BWGBlocks.PALE_JACK_O_LANTERN.get(), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_ORIENTABLE.create(BWGBlocks.PALE_JACK_O_LANTERN.get(), textureMapping.copyAndUpdate(TextureSlot.FRONT, TextureMapping.getBlockTexture(BWGBlocks.PALE_JACK_O_LANTERN.get())), blockModels.modelOutput))).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        blockItemModel(blockModels, BWGBlocks.PALE_PUMPKIN.get());
        blockItemModel(blockModels, BWGBlocks.CARVED_PALE_PUMPKIN.get());
        blockItemModel(blockModels, BWGBlocks.PALE_JACK_O_LANTERN.get());


        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.WITCH_HAZEL_BLOSSOM.get(), ModelLocationUtils.getModelLocation(BWGBlocks.WITCH_HAZEL_BLOSSOM.get())));
        blockItemModel(blockModels, BWGBlocks.WITCH_HAZEL_BLOSSOM.get());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGWood.SPIRIT_ROOTS.get(), ModelLocationUtils.getModelLocation(BWGWood.SPIRIT_ROOTS.get())));
        blockItemModel(blockModels, BWGWood.SPIRIT_ROOTS.get());

        BWGSandSet.getSandSets().forEach(bwgSandSet -> {
            ResourceLocation sandStoneTop = TextureMapping.getBlockTexture(bwgSandSet.getSandstone(), "_top");

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

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(bwgSandSet.getChiseledSandstone(), ModelTemplates.CUBE_COLUMN.create(bwgSandSet.getChiseledSandstone(), new TextureMapping()
                    .put(TextureSlot.END, sandStoneTop)
                    .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getChiseledSandstone())), blockModels.modelOutput)));
            blockItemModel(blockModels, bwgSandSet.getChiseledSandstone());

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(bwgSandSet.getSmoothSandstone(), ModelTemplates.CUBE_ALL.create(bwgSandSet.getSmoothSandstone(), new TextureMapping().put(TextureSlot.ALL, sandStoneTop), blockModels.modelOutput)));
            blockItemModel(blockModels, bwgSandSet.getSmoothSandstone());

            createSlabAndStairs(blockModels, itemModels, bwgSandSet.getSmoothSandstoneSlab(), bwgSandSet.getSmoothSandstoneStairs(), bwgSandSet.getSmoothSandstone(), new TextureMapping().put(TextureSlot.ALL, sandStoneTop));

            blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(bwgSandSet.getCutSandstone(), ModelTemplates.CUBE_COLUMN.create(bwgSandSet.getCutSandstone(), new TextureMapping()
                    .put(TextureSlot.END, sandStoneTop)
                    .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getCutSandstone())), blockModels.modelOutput)));
            blockItemModel(blockModels, bwgSandSet.getCutSandstone());
            createSlab(blockModels, itemModels, bwgSandSet.getCutSandstoneSlab(), bwgSandSet.getCutSandstone(), new TextureMapping()
                    .put(TextureSlot.BOTTOM, sandStoneTop)
                    .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(bwgSandSet.getCutSandstone()))
                    .put(TextureSlot.TOP, sandStoneTop));
        });

        createGrassBlockModel(blockModels, BWGBlocks.LUSH_GRASS_BLOCK.get(), BWGBlocks.LUSH_DIRT.get());
        createGrassBlockModel(blockModels, BWGBlocks.OVERGROWN_DACITE.get(), BWGBlocks.DACITE_SET.getBase());
        createGrassBlockModel(blockModels, BWGBlocks.OVERGROWN_STONE.get(), Blocks.STONE);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.PODZOL_DACITE.get(), ModelTemplates.CUBE_BOTTOM_TOP.create(BWGBlocks.PODZOL_DACITE.get(),
            new TextureMapping().put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(BWGBlocks.DACITE_SET.getBase()))
            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(BWGBlocks.PODZOL_DACITE.get()))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.PODZOL, "_top")), blockModels.modelOutput)));
        blockItemModel(blockModels, BWGBlocks.PODZOL_DACITE.get());

        blockModels.registerSimpleTintedItemModel(BWGBlocks.POISON_IVY.get(), ModelTemplates.FLAT_ITEM.create(BWGBlocks.POISON_IVY.get(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.POISON_IVY.get())), itemModels.modelOutput), new FoliageColorSource());
        basicItem(itemModels, BWGBlocks.SKYRIS_VINE.get().asItem());

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.BARREL_CACTUS.get(), ModelLocationUtils.getModelLocation(BWGBlocks.BARREL_CACTUS.get())));
        itemModels.itemModelOutput.accept(BWGBlocks.BARREL_CACTUS.get().asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.BARREL_CACTUS.get().asItem())));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.FLOWERING_BARREL_CACTUS.get(), ModelLocationUtils.getModelLocation(BWGBlocks.FLOWERING_BARREL_CACTUS.get())));
        itemModels.itemModelOutput.accept(BWGBlocks.FLOWERING_BARREL_CACTUS.get().asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.FLOWERING_BARREL_CACTUS.get().asItem())));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BWGBlocks.CARVED_BARREL_CACTUS.get())
                .with(PropertyDispatch.property(CarvedBarrelCactusBlock.LIQUID)
                        .select(LiquidType.EMPTY, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(BWGBlocks.CARVED_BARREL_CACTUS.get())))
                        .select(LiquidType.WATER, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(BWGBlocks.CARVED_BARREL_CACTUS.get(), "_water")))
                        .select(LiquidType.HONEY, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(BWGBlocks.CARVED_BARREL_CACTUS.get(), "_honey")))));
        blockItemModel(blockModels, BWGBlocks.CARVED_BARREL_CACTUS.get());

        ResourceLocation witchHazelBranch = ModelTemplates.CORAL_WALL_FAN.extend().renderType(mcLocation("cutout_mipped")).build().create(BWGBlocks.WITCH_HAZEL_BRANCH.get(), new TextureMapping().put(TextureSlot.FAN, TextureMapping.getBlockTexture(BWGBlocks.WITCH_HAZEL_BRANCH.get())), blockModels.modelOutput);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BWGBlocks.WITCH_HAZEL_BRANCH.get(), Variant.variant().with(VariantProperties.MODEL, witchHazelBranch)).with(BlockModelGenerators.createHorizontalFacingDispatch()));

        itemModels.itemModelOutput.accept(BWGBlocks.WITCH_HAZEL_BRANCH.get().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.WITCH_HAZEL_BRANCH.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.WITCH_HAZEL_BRANCH.get())), itemModels.modelOutput)));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BWGBlocks.CLOVER_PATCH.get(),
                Stream.of(
                        BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.CLOVER_PATCH.get())),
                        BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.CLOVER_PATCH.get(), "2")),
                        BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.CLOVER_PATCH.get(), "3")),
                        BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.CLOVER_PATCH.get(), "4"))).flatMap(Stream::of).toArray(Variant[]::new)));
        itemModels.itemModelOutput.accept(BWGBlocks.CLOVER_PATCH.get().asItem(), ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.CLOVER_PATCH.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.CLOVER_PATCH.get())), itemModels.modelOutput), new FoliageColorSource()));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BWGBlocks.FLOWER_PATCH.get(),
                Stream.of(
                        BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.FLOWER_PATCH.get())),
                        BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.FLOWER_PATCH.get(), "2")),
                        BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(BWGBlocks.FLOWER_PATCH.get(), "3"))).flatMap(Stream::of).toArray(Variant[]::new)));
        itemModels.itemModelOutput.accept(BWGBlocks.FLOWER_PATCH.get().asItem(), ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.FLOWER_PATCH.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.FLOWER_PATCH.get())), itemModels.modelOutput), new GrassColorSource()));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(BWGBlocks.LEAF_PILE.get(), ModelLocationUtils.getModelLocation(BWGBlocks.LEAF_PILE.get())));
        itemModels.itemModelOutput.accept(BWGBlocks.LEAF_PILE.get().asItem(), ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(BWGBlocks.LEAF_PILE.get().asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(BWGBlocks.LEAF_PILE.get())), itemModels.modelOutput), new FoliageColorSource()));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BWGBlocks.SHELF_FUNGI.get(),
                Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get())),
                Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get(), "2")))
                .with(BlockModelGenerators.createHorizontalFacingDispatch()));


        itemModels.itemModelOutput.accept(BWGBlocks.SHELF_FUNGI.get().asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BWGBlocks.SHELF_FUNGI.get().asItem())));

        //blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BWGBlocks.CATTAIL_SPROUT.get(), new ModelTemplate(Optional.of(BiomesWeveGone.id("block/template_cattail_top")), Optional.empty()).create(BWGBlocks.CATTAIL_SPROUT.get(), new TextureMapping().put(TextureSlot.create("sprout"), TextureMapping.getBlockTexture(BWGBlocks.CATTAIL_SPROUT.get())), blockModels.modelOutput)));

        BWGItems.SIMPLE_ITEMS.forEach(item -> basicItem(itemModels, item.get()));

        basicItem(itemModels, BWGItems.BWG_LOGO.get());

        itemModels.generateSpawnEgg(BWGItems.ODDION_SPAWN_EGG.get(), ARGB.color(199, 165, 104), ARGB.color(210, 166, 246));
        itemModels.generateSpawnEgg(BWGItems.MAN_O_WAR_SPAWN_EGG.get(), ARGB.color(210, 166, 246), ARGB.color(199, 165, 104));
        itemModels.generateSpawnEgg(BWGItems.PUMPKIN_WARDEN_SPAWN_EGG.get(), ARGB.color(79, 57, 46), ARGB.color(192, 106, 5));

        itemModels.itemModelOutput.accept(BWGItems.CATTAIL_SPROUT.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.CATTAIL_SPROUT.get(), TextureMapping.layer0(BiomesWeveGone.id("item/cattails")), itemModels.modelOutput)));
        itemModels.itemModelOutput.accept(BWGItems.FLUORESCENT_CATTAIL_SPROUT.get(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(BWGItems.FLUORESCENT_CATTAIL_SPROUT.get(), TextureMapping.layer0(BiomesWeveGone.id("item/fluorescent_cattails")), itemModels.modelOutput)));

        itemModels.itemModelOutput.accept(BWGItems.TINY_LILY_PADS.get(), ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(BWGItems.TINY_LILY_PADS.get()), TextureMapping.layer0(BWGBlocks.TINY_LILY_PADS.get()), itemModels.modelOutput), new GrassColorSource()));
        itemModels.itemModelOutput.accept(BWGItems.FLOWERING_TINY_LILY_PADS.get(), ItemModelUtils.tintedModel(ModelTemplates.TWO_LAYERED_ITEM.create(ModelLocationUtils.getModelLocation(BWGItems.FLOWERING_TINY_LILY_PADS.get()), TextureMapping.layered(TextureMapping.getBlockTexture(BWGBlocks.TINY_LILY_PADS.get()), TextureMapping.getBlockTexture(BWGBlocks.TINY_LILY_PADS.get(), "_flower_overlay")), blockModels.modelOutput), new GrassColorSource()));
        blockModels.createFlatItemModelWithBlockTexture(BWGItems.WATER_SILK.get(), BWGBlocks.WATER_SILK.get());
        itemModels.declareCustomModelItem(BWGItems.WATER_SILK.get());

        itemModels.generateFlatItem(BWGItems.MUSIC_DISC_PIXIE_CLUB.get(), ModelTemplates.MUSIC_DISC);
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
        ResourceLocation resourcelocation = ModelTemplates.FARMLAND.create(block, texturemapping, blockModelGenerators.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(block, "_moist"), texturemapping1, blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, resourcelocation1, resourcelocation)));
    }

    private static void createSlab(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block baseBlock, TextureMapping baseTexture) {
        ResourceLocation slabBottom = ModelTemplates.SLAB_BOTTOM.create(slab, baseTexture, blockModels.modelOutput);
        ResourceLocation slabTop = ModelTemplates.SLAB_TOP.create(slab, baseTexture, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, slabBottom, slabTop, ModelLocationUtils.getModelLocation(baseBlock)));
        itemModels.itemModelOutput.accept(slab.asItem(), ItemModelUtils.plainModel(slabBottom));
    }

    private static void createStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block stairs, TextureMapping baseTexture) {
        ResourceLocation stairsStraight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, baseTexture, blockModels.modelOutput);
        ResourceLocation stairsInner = ModelTemplates.STAIRS_INNER.create(stairs, baseTexture, blockModels.modelOutput);
        ResourceLocation stairsOuter = ModelTemplates.STAIRS_OUTER.create(stairs, baseTexture, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, stairsInner, stairsStraight, stairsOuter));
        itemModels.itemModelOutput.accept(stairs.asItem(), ItemModelUtils.plainModel(stairsStraight));
    }

    private static void createSlabAndStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block stairs, Block baseBlock, TextureMapping baseTexture) {
        createSlab(blockModels, itemModels, slab, baseBlock, baseTexture);
        createStairs(blockModels, itemModels, stairs, baseTexture);
    }

    private static void createWall(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block wall, Block baseBlock) {
        TextureMapping base = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(baseBlock));
        ResourceLocation wallPost = ModelTemplates.WALL_POST.create(wall, base, blockModels.modelOutput);
        ResourceLocation wallSide = ModelTemplates.WALL_LOW_SIDE.create(wall, base, blockModels.modelOutput);
        ResourceLocation wallSideTall = ModelTemplates.WALL_TALL_SIDE.create(wall, base, blockModels.modelOutput);
        ResourceLocation wallInventory = ModelTemplates.WALL_INVENTORY.create(wall, base, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createWall(wall, wallPost, wallSide, wallSideTall));
        itemModels.itemModelOutput.accept(wall.asItem(), ItemModelUtils.plainModel(wallInventory));
    }

    private void createGrassBlockModel(BlockModelGenerators blockModels, Block grassBlock, Block dirtBlock) {
        ResourceLocation model = new ModelTemplate(Optional.of(mcLocation("block/grass_block")), Optional.empty())
                .create(grassBlock, new TextureMapping()
                        .putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(dirtBlock))
                        .putForced(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirtBlock))
                        .putForced(TextureSlot.TOP, TextureMapping.getBlockTexture(grassBlock, "_top"))
                        .putForced(TextureSlot.SIDE, TextureMapping.getBlockTexture(grassBlock, "_side"))
                        .putForced(TextureSlot.create("overlay"), TextureMapping.getBlockTexture(grassBlock, "_side_overlay")), blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(grassBlock)
                .with(PropertyDispatch.property(BlockStateProperties.SNOWY)
                        .select(false, List.of(
                                Variant.variant().with(VariantProperties.MODEL, model),
                                Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                                Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                                Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.create(
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
        ResourceLocation resourcelocation = TexturedModel.FLOWERBED_1.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput);
        ResourceLocation resourcelocation1 = TexturedModel.FLOWERBED_2.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput);
        ResourceLocation resourcelocation2 = TexturedModel.FLOWERBED_3.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput);
        ResourceLocation resourcelocation3 = TexturedModel.FLOWERBED_4.updateTexture(textureMapping -> textureMapping.put(TextureSlot.STEM, TextureMapping.getBlockTexture(Blocks.PINK_PETALS, "_stem"))).create(flowerBedBlock, blockModel.modelOutput);
        blockModel.blockStateOutput
                .accept(
                        MultiPartGenerator.multiPart(flowerBedBlock)
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation1)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation2)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation3)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                                        Variant.variant().with(VariantProperties.MODEL, resourcelocation3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                );
    }

    @Override
    protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
    }

    @Override
    protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.empty();
    }
}
