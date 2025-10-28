package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors.FruitBlockProcessor;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors.PlantProcessor;

import java.util.*;

public class BWGStructureProcessorLists {

    public static final Map<ResourceKey<StructureProcessorList>, StructureProcessorListFactory> STRUCTURE_PROCESSOR_LIST_FACTORIES = new Reference2ObjectOpenHashMap<>();

    public static final ResourceKey<StructureProcessorList> PRAIRIE_HOUSE = register("prairie_house", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(
                            BWGProcessorRules.GRASS_RANDOM_DIRT_PODZOL_COARSE_DIRT,
                            BWGProcessorRules.STONE_RANDOM_COBBLESTONE_MOSSY)
                    )
            )
    );

    public static final ResourceKey<StructureProcessorList> ABANDONED_PRAIRIE_HOUSE = register("abandoned_prairie_house", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(createRuleProcessor(
                    BWGProcessorRules.DIRT_RANDOM_GRASS_COARSE_DIRT_PODZOL,
                    BWGProcessorRules.STONE_RANDOM_COBBLESTONE_MOSSY,
                    BWGProcessorRules.MOSSIFY_70_PERCENT)
            )
    ));

    public static final ResourceKey<StructureProcessorList> ASPEN_MANOR = register("aspen_manor", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(BWGProcessorRules.SWEETBERRY_BLUEBERRY_50_PERCENT_RANDOM_AGE)
            )
    ));

    public static final ResourceKey<StructureProcessorList> FORGOTTEN_VILLAGE_BLOCKS = register("forgotten_village_blocks", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(BWGProcessorRules.FORGOTTEN_ROCKY_STONE, BWGProcessorRules.FORGOTTEN_MOSSY_STONE_BRICKS, new ProcessorRule[]{BWGProcessorRules.FORGOTTEN_GRAVEL, BWGProcessorRules.FORGOTTEN_LUSH_GRASS_BLOCK})
            )
    ));

    public static final ResourceKey<StructureProcessorList> SKYRIS_STREETS = register("skyris_streets", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.streetWater(Blocks.POLISHED_ANDESITE, BWGWood.SKYRIS.planks())}, BWGProcessorRules.POLISHED_ANDESITE_RANDOM_ANDESITE_GRAVEL)
            )
    ));

    public static final ResourceKey<StructureProcessorList> SKYRIS_TEMPLE = register("skyris_temple", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(BWGProcessorRules.POLISHED_ANDESITE_RANDOM_ANDESITE_GRAVEL,
                    BWGProcessorRules.STONEBRICKS_VARIANTS, new ProcessorRule[]{BWGProcessorRules.WHITE_DACITE_10_PERCENT_COBBLED_WHITE_DACITE})
            )
    ));

    public static final ResourceKey<StructureProcessorList> SKYRIS_LIBRARY = register("skyris_library", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(BWGProcessorRules.POLISHED_ANDESITE_RANDOM_ANDESITE_GRAVEL,
                    BWGProcessorRules.WHITE_DACITE_VARIANTS)
            )
    ));

    public static final ResourceKey<StructureProcessorList> SKYRIS_FLETCHER_HOUSE = register("skyris_fletcher_house", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                createRuleProcessor(BWGProcessorRules.PODZOL_10_PERCENT_ANDESITE, new ProcessorRule[]{BWGProcessorRules.PODZOL_25_PERCENT_COARSE_DIRT, BWGProcessorRules.PODZOL_10_PERCENT_GRAVEL})
            )
    ));

    public static final ResourceKey<StructureProcessorList> SKYRIS_FORAGER_HOUSE = register("skyris_forager_house", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(
                BWGProcessorRules.POLISHED_ANDESITE_RANDOM_ANDESITE_GRAVEL, new ProcessorRule[] {
                BWGProcessorRules.PODZOL_25_PERCENT_COARSE_DIRT,
                BWGProcessorRules.PODZOL_50_PERCENT_LUSH_GRASS,
                BWGProcessorRules.skyrisLeavesToGreenAppleLeaves(0.15f),
                BWGProcessorRules.skyrisLeavesToFlowering(0.2f)}),
                new FruitBlockProcessor(BWGBlocks.GREEN_APPLE_FRUIT_BLOCK.get())
            )
    ));

    public static final ResourceKey<StructureProcessorList> SKYRIS_TOWN_CENTERS = register("skyris_town_centers", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(
                    BWGProcessorRules.POLISHED_ANDESITE_RANDOM_ANDESITE_GRAVEL, new ProcessorRule[] {
                    BWGProcessorRules.skyrisLeavesToGreenAppleLeaves(0.15f),
                    BWGProcessorRules.skyrisLeavesToFlowering(0.2f)}),
                    new FruitBlockProcessor(BWGBlocks.GREEN_APPLE_FRUIT_BLOCK.get())
            )
    ));

    public static final ResourceKey<StructureProcessorList> SALEM_TOWN_CENTER = register("salem_town_center", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(
                            BWGProcessorRules.STONE_SLAB_RANDOM_ANDESITE_ROCKY_SLAB,
                            BWGProcessorRules.STONE_RANDOM_ROCKY_ANDESITE, new ProcessorRule[] {
                    BWGProcessorRules.lushDirtToCoarseDirt(.4f)}
            )
    )));

    public static final ResourceKey<StructureProcessorList> SALEM_STREETS = register("salem_streets", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(createRuleProcessor(BWGProcessorRules.streetWater(BWGBlocks.LUSH_DIRT_PATH.get(), BWGWood.WITCH_HAZEL.planks()), BWGProcessorRules.lushDirtToCoarseDirt(.3f)))
    ));

    public static final ResourceKey<StructureProcessorList> SALEM_HOUSES = register("salem_houses", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.STRIPPED_OAK_LOG_60_PERCENT_OAK_PLANKS},
                    BWGProcessorRules.STONE_RANDOM_COBBLESTONE_MOSSY
            )
    )));

	public static final ResourceKey<StructureProcessorList> SALEM_LARGE_FARM = register("salem_large_farm", structureProcessorListHolderGetter -> new StructureProcessorList(
			ImmutableList.of(
					createRuleProcessor(
							BWGProcessorRules.STONE_SLAB_RANDOM_ANDESITE_COBBLE_SLAB,
							BWGProcessorRules.LUSH_DIRT_PATH_PEAT_COARSE_DIRT,
							BWGProcessorRules.POTATO, BWGProcessorRules.CARROT, BWGProcessorRules.BEETROOT
					)
			)
	));

	public static final ResourceKey<StructureProcessorList> SALEM_SMALL_FARM = register("salem_small_farm", structureProcessorListHolderGetter -> new StructureProcessorList(
			ImmutableList.of(
					createRuleProcessor(
							BWGProcessorRules.FARM_TAIGA,
							BWGProcessorRules.STONE_SLAB_RANDOM_ANDESITE_COBBLE_SLAB,
							BWGProcessorRules.LUSH_DIRT_PATH_PEAT_COARSE_DIRT,
							BWGProcessorRules.POTATO, BWGProcessorRules.PUMPKIN_RANDOM_AGE, BWGProcessorRules.WHEAT
					)
			)
	));

    public static final ResourceKey<StructureProcessorList> RED_ROCK_STREETS = register("red_rock_streets", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.streetWater(Blocks.ORANGE_TERRACOTTA, BWGWood.PINE.planks())}, BWGProcessorRules.ORANGE_TERRACOTTA_TO_RED_ROCK_CRACKED_RED_ROCK_BRICKS))
    ));

    public static final ResourceKey<StructureProcessorList> RED_ROCK_MEETING_POINT_1 = register("red_rock_meeting_point_1", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(BWGProcessorRules.RED_ROCK_BRICKS_TO_ORANGE_TERRACOTTA,
                    BWGProcessorRules.RED_ROCK_CRACKED_BRICKS_50_PERCENT_MOSSY_RED_ROCK_BRICKS)
            )
    ));

	public static final ResourceKey<StructureProcessorList> RED_ROCK_MEETING_POINT_2 = register("red_rock_meeting_point_2", structureProcessorListHolderGetter -> new StructureProcessorList(
			ImmutableList.of(
					createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.RED_ROCK_CRACKED_BRICKS_50_PERCENT_MOSSY_RED_ROCK_BRICKS},
							BWGProcessorRules.RED_ROCK_25_PERCENT_SPLIT)
			)
	));

    public static final ResourceKey<StructureProcessorList> RED_ROCK_MEETING_POINT_3 = register("red_rock_meeting_point_3", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
		            createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.STRIPPED_BAOBAB_WOOD_50_PERCENT_BAOBAB_PLANKS, BWGProcessorRules.RED_ROCK_BRICKS_35_PERCENT_CRACKED_RED_ROCK_BRICKS, BWGProcessorRules.RED_ROCK_25_PERCENT_RED_ROCK_BRICKS, BWGProcessorRules.RED_ROCK_BRICKS_35_PERCENT_CRACKED_RED_ROCK_BRICKS},
                    BWGProcessorRules.RED_ROCK_25_PERCENT_SPLIT)
            )
    ));

    public static final ResourceKey<StructureProcessorList> RED_ROCK_TO_BRICKS = register("red_rock_to_bricks", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(
                    BWGProcessorRules.RED_ROCK_25_PERCENT_CRACKED_RED_ROCK_BRICKS,
                    BWGProcessorRules.RED_ROCK_25_PERCENT_RED_ROCK_BRICKS)
            )
    ));

    public static final ResourceKey<StructureProcessorList> RED_ROCK_HOUSE = register("red_rock_house", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.RED_ROCK_BRICKS_35_PERCENT_CRACKED_RED_ROCK_BRICKS},
                    BWGProcessorRules.RANDOM_DESERT_POTTED_PLANT))
            )
    );

    public static final ResourceKey<StructureProcessorList> PUMPKIN_PATCH_STREETS = register("pumpkin_patch_streets", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(
                        BWGProcessorRules.streetWater(BWGBlocks.LUSH_DIRT_PATH.get(), Blocks.DARK_OAK_PLANKS),
                        BWGProcessorRules.lushPathToBlock(0.15f, Blocks.PACKED_MUD),
                        BWGProcessorRules.lushPathToBlock(0.4f, BWGBlocks.PEAT.get()),
                        BWGProcessorRules.lushPathToBlock(0.4f, Blocks.COARSE_DIRT)
            )
    )));

    public static final ResourceKey<StructureProcessorList> PUMPKIN_PATCH_HOUSE = register("pumpkin_patch_house", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(new ProcessorRule[]{
                    BWGProcessorRules.lushPathToBlock(0.15f, BWGBlocks.LUSH_GRASS_BLOCK.get()),
                    BWGProcessorRules.lushPathToBlock(0.45f, BWGBlocks.PEAT.get()),
                    BWGProcessorRules.lushPathToBlock(0.4f, Blocks.COARSE_DIRT),
                    BWGProcessorRules.burrowToBlock(0.5f, Blocks.PUMPKIN),
                    BWGProcessorRules.STRIPPED_DARK_OAK_WOOD_35_PERCENT_DARK_OAK_PLANKS},
                    BWGProcessorRules.DACITE_COBBLESTONE_VARIANTS,
                    BWGProcessorRules.SHORT_GRASS_TO_FLOWER_PUMPKIN_PATCH,
                    BWGProcessorRules.WHITE_PUFFBALL_RANDOM_AGE,
                    BWGProcessorRules.PUMPKIN_RANDOM_AGE)
            )
    ));

    public static final ResourceKey<StructureProcessorList> PUMPKIN_PATCH_FARM = register("pumpkin_patch_farm", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(BWGProcessorRules.lushFarmLandToBlock(BWGBlocks.LUSH_GRASS_BLOCK.get(), 0.5f), BWGProcessorRules.lushFarmLandToBlock(Blocks.WATER, 0.23f)),
                    new PlantProcessor(BWGBlocks.LUSH_FARMLAND.get(), Blocks.PUMPKIN_STEM, 0.5f)
            )));

    public static final ResourceKey<StructureProcessorList> PUMPKIN_PATCH_MEETING_POINT = register("pumpkin_patch_meeting_point", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.lushFarmLandToBlock(BWGBlocks.LUSH_GRASS_BLOCK.get(), 0.5f),
                            BWGProcessorRules.lushFarmLandToBlock(Blocks.WATER, 0.23f),
                            BWGProcessorRules.lushPathToBlock(0.35f, BWGBlocks.LUSH_GRASS_BLOCK.get()),
                            BWGProcessorRules.grassBlockToBlock(0.4f, BWGBlocks.LUSH_DIRT_PATH.get())}, BWGProcessorRules.DACITE_COBBLESTONE_VARIANTS),
                    new PlantProcessor(BWGBlocks.LUSH_FARMLAND.get(), Blocks.PUMPKIN_STEM, 0.5f)
            )));

    public static final ResourceKey<StructureProcessorList> SWAMP_STREETS = register("swamp_streets", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(BWGProcessorRules.tuffToBlock(0.15f, Blocks.MOSSY_COBBLESTONE),
                            BWGProcessorRules.tuffToBlock(0.5f, Blocks.POLISHED_TUFF),
                            BWGProcessorRules.tuffToBlock(0.5f, Blocks.TUFF_BRICKS))
            )
    ));

    public static final ResourceKey<StructureProcessorList> SWAMP_HOUSE = register("swamp_house", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(
                            new ProcessorRule[]{BWGProcessorRules.tuffToBlock(0.15f, Blocks.MOSSY_COBBLESTONE),
                                    BWGProcessorRules.tuffToBlock(0.45f, Blocks.POLISHED_TUFF),
                                    BWGProcessorRules.tuffToBlock(0.4f, Blocks.TUFF_BRICKS),
                                    BWGProcessorRules.grassBlockToBlock(0.1f, Blocks.MOSS_BLOCK),
                                    BWGProcessorRules.chestToBlock(0.2f, Blocks.AIR)},
                            BWGProcessorRules.SHORT_GRASS_TO_FLOWER_SWAMP)
            )
    ));

	public static final ResourceKey<StructureProcessorList> SWAMP_MEETING_POINT = register("swamp_meeting_point", structureProcessorListHolderGetter -> new StructureProcessorList(
			ImmutableList.of(
					createRuleProcessor(
							new ProcessorRule[]{BWGProcessorRules.tuffToBlock(0.15f, Blocks.MOSSY_COBBLESTONE),
									BWGProcessorRules.tuffToBlock(0.45f, Blocks.POLISHED_TUFF),
									BWGProcessorRules.tuffToBlock(0.4f, Blocks.TUFF_BRICKS),
									BWGProcessorRules.tuffToBlockState(0.02f, Blocks.TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST)),
									BWGProcessorRules.tuffToBlockState(0.025f, Blocks.TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST)),
									BWGProcessorRules.tuffToBlockState(0.02f, Blocks.MOSSY_COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST)),
									BWGProcessorRules.tuffToBlockState(0.025f, Blocks.MOSSY_COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.MOSSY_COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.MOSSY_COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST)),
									BWGProcessorRules.tuffToBlockState(0.02f, Blocks.TUFF_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST)),
									BWGProcessorRules.tuffToBlockState(0.025f, Blocks.TUFF_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.TUFF_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.TUFF_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST)),
									BWGProcessorRules.tuffToBlockState(0.02f, Blocks.POLISHED_TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST)),
									BWGProcessorRules.tuffToBlockState(0.025f, Blocks.POLISHED_TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.POLISHED_TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH)),
									BWGProcessorRules.tuffToBlockState(0.03f, Blocks.POLISHED_TUFF_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST)),
									BWGProcessorRules.grassBlockToBlock(0.1f, Blocks.MOSS_BLOCK),
									BWGProcessorRules.grassBlockToBlock(0.3f, Blocks.PODZOL),
									BWGProcessorRules.grassBlockToBlock(0.15f, Blocks.MUD),
									BWGProcessorRules.grassBlockToBlock(0.25f, BWGBlocks.PEAT.get())},
							BWGProcessorRules.SHORT_GRASS_TO_FLOWER_SWAMP)
			)
	));

    public static final ResourceKey<StructureProcessorList> MOSSIFY_10_PERCENT_WHITE_PUFFBALL = register("mossify_10_percent_white_puffball", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.MOSSIFY_10_PERCENT}, BWGProcessorRules.WHITE_PUFFBALL_RANDOM_AGE)
            )
    ));

    public static final ResourceKey<StructureProcessorList> BOG_TRIAL = register("bog_trial", structureProcessorListHolderGetter -> new StructureProcessorList(
            ImmutableList.of(
                    createRuleProcessor(new ProcessorRule[]{BWGProcessorRules.spiritLeavesToFlowering(0.5f), BWGProcessorRules.BOG_TRIAL_FLOOR},BWGProcessorRules.WHITE_PUFFBALL_RANDOM_AGE))
    ));

    private static RuleProcessor createRuleProcessor(ProcessorRule... rules) {
        return new RuleProcessor(ImmutableList.copyOf(rules));
    }

    private static RuleProcessor createRuleProcessor(ProcessorRule[]... ruleGroups) {
        List<ProcessorRule> combined = new ArrayList<>();
        for (ProcessorRule[] group : ruleGroups) Collections.addAll(combined, group);
        return new RuleProcessor(ImmutableList.copyOf(combined));
    }

    private static ResourceKey<StructureProcessorList> register(String id, StructureProcessorListFactory factory) {
        ResourceKey<StructureProcessorList> structureProcessorListResourceKey = BiomesWeveGone.key(Registries.PROCESSOR_LIST, id);
        STRUCTURE_PROCESSOR_LIST_FACTORIES.put(structureProcessorListResourceKey, factory);
        return structureProcessorListResourceKey;
    }

    @FunctionalInterface
    public interface StructureProcessorListFactory  {
        StructureProcessorList generate(HolderGetter<StructureProcessorList> structureProcessorListHolderGetter);
    }
}
