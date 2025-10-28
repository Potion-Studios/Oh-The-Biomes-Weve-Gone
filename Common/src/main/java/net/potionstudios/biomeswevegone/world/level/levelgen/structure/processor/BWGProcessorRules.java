package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.WhitePuffballBlock;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;

class BWGProcessorRules {

	protected static final ProcessorRule[] FORGOTTEN_ROCKY_STONE = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.ROCKY_STONE_SET.getBase(), 0.25F, BWGBlocks.MOSSY_STONE_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.ROCKY_STONE_SET.getBase(), 0.25F, Blocks.GRAVEL),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.ROCKY_STONE_SET.getBase(), 0.25F, Blocks.STONE)
	};

	protected static final ProcessorRule FORGOTTEN_GRAVEL = createAlwaysTrueRandomBlockMatchTest(Blocks.GRAVEL, 0.35F, Blocks.SUSPICIOUS_GRAVEL);

	protected static final ProcessorRule FORGOTTEN_LUSH_GRASS_BLOCK = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.LUSH_GRASS_BLOCK.get(), 0.45F, Blocks.MOSS_BLOCK);

	protected static final ProcessorRule[] FORGOTTEN_MOSSY_STONE_BRICKS = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.MOSSY_STONE_BRICKS, 0.1F, Blocks.STONE_BRICKS),
			createAlwaysTrueRandomBlockMatchTest(Blocks.MOSSY_STONE_BRICKS, 0.2F, Blocks.CRACKED_STONE_BRICKS),
			createAlwaysTrueRandomBlockMatchTest(Blocks.MOSSY_STONE_BRICKS, 0.1F, Blocks.TUFF)
	};

	protected static final ProcessorRule[] DIRT_RANDOM_GRASS_COARSE_DIRT_PODZOL = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.DIRT, 0.25F, Blocks.GRASS_BLOCK),
			createAlwaysTrueRandomBlockMatchTest(Blocks.DIRT, 0.25F, Blocks.COARSE_DIRT),
			createAlwaysTrueRandomBlockMatchTest(Blocks.DIRT, 0.25F, Blocks.PODZOL)
	};

	protected static final ProcessorRule[] GRASS_RANDOM_DIRT_PODZOL_COARSE_DIRT = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.GRASS_BLOCK, 0.25F, Blocks.DIRT),
			createAlwaysTrueRandomBlockMatchTest(Blocks.GRASS_BLOCK, 0.25F, Blocks.PODZOL),
			createAlwaysTrueRandomBlockMatchTest(Blocks.GRASS_BLOCK, 0.25F, Blocks.COARSE_DIRT)
	};

	protected static final ProcessorRule[] STONE_RANDOM_COBBLESTONE_MOSSY = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE, 0.15F, Blocks.COBBLESTONE),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE, 0.15F, Blocks.MOSSY_COBBLESTONE),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE, 0.15F, BWGBlocks.MOSSY_STONE_SET.getBase())
	};

	protected static final ProcessorRule[] MOSSIFY_70_PERCENT = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE, 0.7F, BWGBlocks.MOSSY_STONE_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.COBBLESTONE, 0.7F, Blocks.MOSSY_COBBLESTONE),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_BRICKS, 0.7F, Blocks.MOSSY_STONE_BRICKS),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_STAIRS, 0.7F, BWGBlocks.MOSSY_STONE_SET.getStairs()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.COBBLESTONE_STAIRS, 0.7F, Blocks.MOSSY_COBBLESTONE_STAIRS)
	};

	protected static final ProcessorRule[] SWEETBERRY_BLUEBERRY_50_PERCENT_RANDOM_AGE = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.SWEET_BERRY_BUSH, 0.125F, BWGBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, 0)),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SWEET_BERRY_BUSH, 0.125F, BWGBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, 1)),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SWEET_BERRY_BUSH, 0.125F, BWGBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, 2)),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SWEET_BERRY_BUSH, 0.125F, BWGBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3)),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.BLUEBERRY_BUSH.get(), 0.125F, Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 0)),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.BLUEBERRY_BUSH.get(), 0.125F, Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 1)),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.BLUEBERRY_BUSH.get(), 0.125F, Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 2)),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.BLUEBERRY_BUSH.get(), 0.125F, Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3))
	};

	protected static final ProcessorRule MOSSIFY_10_PERCENT = createAlwaysTrueRandomBlockMatchTest(Blocks.COBBLESTONE, 0.1F, Blocks.MOSSY_COBBLESTONE);

	protected static final ProcessorRule[] WHITE_PUFFBALL_RANDOM_AGE = createEvenChanceAgeRules(BWGBlocks.WHITE_PUFFBALL.getBlock(), WhitePuffballBlock.AGE, WhitePuffballBlock.MAX_AGE);

	protected static final ProcessorRule[] PUMPKIN_RANDOM_AGE = createEvenChanceAgeRules(Blocks.PUMPKIN_STEM, StemBlock.AGE, StemBlock.MAX_AGE);

	protected static ProcessorRule[] createEvenChanceAgeRules(Block block, IntegerProperty ageProperty, int maxAge) {
		ProcessorRule[] rules = new ProcessorRule[maxAge - 1];
		for (int i = 1; i <= maxAge - 1; i++) {
			float chance = 1.0f / (maxAge - i + 1); // 1/(N - i + 1), with N = maxAge
			rules[i - 1] = createAlwaysTrueRandomBlockMatchTest(
					block,
					chance,
					block.defaultBlockState().setValue(ageProperty, i)
			);
		}
		return rules;
	}

	protected static ProcessorRule burrowToBlock(float chance, Block newBlock) {
		return createAlwaysTrueRandomBlockMatchTest(BWGBlocks.PUMPKIN_BURROW.get(), chance, newBlock);
	}

	protected static ProcessorRule lushPathToBlock(float chance, Block newBlock) {
		return createAlwaysTrueRandomBlockMatchTest(BWGBlocks.LUSH_DIRT_PATH.get(), chance, newBlock);
	}

	protected static ProcessorRule lushGrassToBlock(float chance, Block newBlock) {
		return createAlwaysTrueRandomBlockMatchTest(BWGBlocks.LUSH_GRASS_BLOCK.get(), chance, newBlock);
	}

	protected static ProcessorRule tuffToBlock(float chance, Block newBlock) {
		return createAlwaysTrueRandomBlockMatchTest(Blocks.TUFF, chance, newBlock);
	}

	protected static ProcessorRule tuffToBlockState(float chance, BlockState newBlock) {
		return createAlwaysTrueRandomBlockMatchTest(Blocks.TUFF, chance, newBlock);
	}

	protected static ProcessorRule grassBlockToBlock(float chance, Block newBlock) {
		return createAlwaysTrueRandomBlockMatchTest(Blocks.GRASS_BLOCK, chance, newBlock);
	}

	protected static ProcessorRule chestToBlock(float chance, Block newBlock) {
		return createAlwaysTrueRandomBlockMatchTest(Blocks.CHEST, chance, newBlock);
	}

	protected static final ProcessorRule STRIPPED_DARK_OAK_WOOD_35_PERCENT_DARK_OAK_PLANKS = createAlwaysTrueRandomBlockMatchTest(Blocks.DARK_OAK_PLANKS, 0.35F, Blocks.STRIPPED_DARK_OAK_WOOD);

	protected static final ProcessorRule STRIPPED_OAK_LOG_60_PERCENT_OAK_PLANKS = createAlwaysTrueRandomBlockMatchTest(Blocks.STRIPPED_OAK_LOG, 0.6F, Blocks.OAK_PLANKS);

	protected static ProcessorRule lushDirtToCoarseDirt(float chance) {
		return createAlwaysTrueRandomBlockMatchTest(BWGBlocks.LUSH_DIRT_PATH.get(), chance, Blocks.COARSE_DIRT);
	}

	protected static ProcessorRule lushFarmLandToBlock( Block block, float chance) {
		return createAlwaysTrueRandomBlockMatchTest(BWGBlocks.LUSH_FARMLAND.get(), chance, block);
	}

	protected static final ProcessorRule[] POLISHED_ANDESITE_RANDOM_ANDESITE_GRAVEL = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.POLISHED_ANDESITE, 0.3F, Blocks.ANDESITE),
			createAlwaysTrueRandomBlockMatchTest(Blocks.POLISHED_ANDESITE, 0.3F, Blocks.GRAVEL)
	};

	protected static final ProcessorRule[] STONE_SLAB_RANDOM_ANDESITE_ROCKY_SLAB = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_SLAB, 0.33F, Blocks.ANDESITE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM)),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_SLAB, 0.33F, BWGBlocks.ROCKY_STONE_SET.getSlab().defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM))
	};

	protected static final ProcessorRule[] STONE_SLAB_RANDOM_ANDESITE_COBBLE_SLAB = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_SLAB, 0.33F, Blocks.ANDESITE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM)),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_SLAB, 0.33F, Blocks.COBBLESTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM))
	};

	protected static final ProcessorRule[] LUSH_DIRT_PATH_PEAT_COARSE_DIRT = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.LUSH_DIRT_PATH.get(), 0.2F, BWGBlocks.PEAT.get()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.LUSH_DIRT_PATH.get(), 0.2F, Blocks.COARSE_DIRT)
	};

	protected static final ProcessorRule[] FARM_TAIGA = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.WHEAT, 0.3F, Blocks.PUMPKIN_STEM),
			createAlwaysTrueRandomBlockMatchTest(Blocks.WHEAT, 0.2F, Blocks.POTATOES)
	};

	protected static final ProcessorRule[] POTATO = createEvenChanceAgeRules(Blocks.POTATOES, PotatoBlock.AGE, PotatoBlock.MAX_AGE);
	protected static final ProcessorRule[] CARROT = createEvenChanceAgeRules(Blocks.CARROTS, CarrotBlock.AGE, CarrotBlock.MAX_AGE);
	protected static final ProcessorRule[] BEETROOT = createEvenChanceAgeRules(Blocks.BEETROOTS, BeetrootBlock.AGE, BeetrootBlock.MAX_AGE);
	protected static final ProcessorRule[] WHEAT = createEvenChanceAgeRules(Blocks.WHEAT, CropBlock.AGE, CropBlock.MAX_AGE);

	protected static final ProcessorRule[] STONE_RANDOM_ROCKY_ANDESITE = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE, 0.33F, Blocks.ANDESITE),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE, 0.33F, BWGBlocks.ROCKY_STONE_SET.getBase())
	};

	protected static final ProcessorRule[] ORANGE_TERRACOTTA_TO_RED_ROCK_CRACKED_RED_ROCK_BRICKS = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.ORANGE_TERRACOTTA, 0.33F, BWGBlocks.RED_ROCK_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.ORANGE_TERRACOTTA, 0.33F, BWGBlocks.CRACKED_RED_ROCK_BRICKS_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.ORANGE_TERRACOTTA, 0.1F, BWGBlocks.RED_ROCK_BRICKS_SET.getBase())
	};

	protected static final ProcessorRule RED_ROCK_BRICKS_TO_ORANGE_TERRACOTTA = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.RED_ROCK_BRICKS_SET.getBase(), 0.33F, Blocks.ORANGE_TERRACOTTA);

	protected static final ProcessorRule RED_ROCK_CRACKED_BRICKS_50_PERCENT_MOSSY_RED_ROCK_BRICKS = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.CRACKED_RED_ROCK_BRICKS_SET.getBase(), 0.5F, BWGBlocks.MOSSY_RED_ROCK_BRICKS_SET.getBase());

	protected static final ProcessorRule[] RED_ROCK_25_PERCENT_SPLIT = new ProcessorRule[]{
		createAlwaysTrueRandomBlockMatchTest(BWGBlocks.RED_ROCK_SET.getBase(), 0.3F, Blocks.TERRACOTTA),
		createAlwaysTrueRandomBlockMatchTest(BWGBlocks.RED_ROCK_SET.getBase(),	0.3F, Blocks.ORANGE_TERRACOTTA),
		createAlwaysTrueRandomBlockMatchTest(BWGBlocks.RED_ROCK_SET.getBase(), 0.3F, Blocks.RED_SAND)
	};

	protected static final ProcessorRule STRIPPED_BAOBAB_WOOD_50_PERCENT_BAOBAB_PLANKS = createAlwaysTrueRandomBlockMatchTest(BWGWood.BAOBAB.strippedWood(), 0.5F, BWGWood.BAOBAB.planks());

	protected static final ProcessorRule RED_ROCK_25_PERCENT_RED_ROCK_BRICKS = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.RED_ROCK_SET.getBase(), 0.25F, BWGBlocks.RED_ROCK_BRICKS_SET.getBase());

	protected static final ProcessorRule RED_ROCK_25_PERCENT_CRACKED_RED_ROCK_BRICKS = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.RED_ROCK_SET.getBase(), 0.25F, BWGBlocks.CRACKED_RED_ROCK_BRICKS_SET.getBase());

	protected static final ProcessorRule RED_ROCK_BRICKS_35_PERCENT_CRACKED_RED_ROCK_BRICKS = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.RED_ROCK_BRICKS_SET.getBase(), 0.35F, BWGBlocks.CRACKED_RED_ROCK_BRICKS_SET.getBase());

	protected static final ProcessorRule[] RANDOM_DESERT_POTTED_PLANT = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.POTTED_CACTUS, 0.1f, Blocks.POTTED_DEAD_BUSH),
			createAlwaysTrueRandomBlockMatchTest(Blocks.POTTED_CACTUS, 0.2f, BWGBlocks.PRICKLY_PEAR_CACTUS.getPottedBlock()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.POTTED_CACTUS, 0.2f, BWGBlocks.MINI_CACTUS.getPottedBlock()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.POTTED_CACTUS, 0.2f, BWGBlocks.GOLDEN_SPINED_CACTUS.getPottedBlock())
	};

	protected static final ProcessorRule WHITE_DACITE_10_PERCENT_COBBLED_WHITE_DACITE = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.WHITE_DACITE_SET.getBase(), 0.1f, BWGBlocks.WHITE_DACITE_COBBLESTONE_SET.getBase());

	protected static final ProcessorRule[] WHITE_DACITE_VARIANTS = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.WHITE_DACITE_SET.getBase(), 0.3f, BWGBlocks.WHITE_DACITE_COBBLESTONE_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.WHITE_DACITE_SET.getBase(), 0.3f, BWGBlocks.WHITE_DACITE_BRICKS_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.WHITE_DACITE_SET.getBase(), 0.1f, BWGBlocks.CRACKED_WHITE_DACITE_BRICKS_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.WHITE_DACITE_SET.getBase(), 0.1f, BWGBlocks.MOSSY_WHITE_DACITE_BRICKS_SET.getBase())
	};

	protected static final ProcessorRule[] DACITE_COBBLESTONE_VARIANTS = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.DACITE_COBBLESTONE_SET.getBase(), 0.1f, BWGBlocks.DACITE_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.DACITE_COBBLESTONE_SET.getBase(), 0.25f, BWGBlocks.DACITE_BRICKS_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.DACITE_COBBLESTONE_SET.getBase(), 0.1f, BWGBlocks.CRACKED_DACITE_BRICKS_SET.getBase()),
			createAlwaysTrueRandomBlockMatchTest(BWGBlocks.DACITE_COBBLESTONE_SET.getBase(), 0.1f, BWGBlocks.MOSSY_DACITE_BRICKS_SET.getBase())
	};

	protected static final ProcessorRule[] SHORT_GRASS_TO_FLOWER_PUMPKIN_PATCH = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.15f, BWGBlocks.LEAF_PILE.get()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.15f, BWGBlocks.CLOVER_PATCH.get()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.1f, BWGBlocks.WHITE_ANEMONE.getBlock()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.1f, BWGBlocks.CROCUS.getBlock())
	};

	protected static final ProcessorRule[] SHORT_GRASS_TO_FLOWER_SWAMP = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.35f, Blocks.AIR),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.15f, BWGBlocks.PEACH_LEATHER_FLOWER.getBlock()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.15f, BWGBlocks.CLOVER_PATCH.get()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.1f, BWGBlocks.VIOLET_LEATHER_FLOWER.getBlock()),
			createAlwaysTrueRandomBlockMatchTest(Blocks.SHORT_GRASS, 0.1f, Blocks.MOSS_CARPET)
	};

	protected static final ProcessorRule[] STONEBRICKS_VARIANTS = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_BRICKS, 0.3f, Blocks.MOSSY_STONE_BRICKS),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_BRICKS, 0.3f, Blocks.CRACKED_STONE_BRICKS),
			createAlwaysTrueRandomBlockMatchTest(Blocks.STONE_BRICKS, 0.3f, BWGBlocks.MOSSY_STONE_SET.getBase())
	};

	protected static final ProcessorRule PODZOL_25_PERCENT_COARSE_DIRT = createAlwaysTrueRandomBlockMatchTest(Blocks.PODZOL, 0.25f, Blocks.COARSE_DIRT);

	protected static final ProcessorRule PODZOL_10_PERCENT_GRAVEL = createAlwaysTrueRandomBlockMatchTest(Blocks.PODZOL, 0.1f, Blocks.GRAVEL);

	protected static final ProcessorRule[] PODZOL_10_PERCENT_ANDESITE = new ProcessorRule[]{
			createAlwaysTrueRandomBlockMatchTest(Blocks.PODZOL, 0.1f, Blocks.ANDESITE),
			createAlwaysTrueRandomBlockMatchTest(Blocks.PODZOL, 0.1f, Blocks.POLISHED_ANDESITE)
	};

	protected static final ProcessorRule PODZOL_50_PERCENT_LUSH_GRASS = createAlwaysTrueRandomBlockMatchTest(Blocks.PODZOL, 0.5f, BWGBlocks.LUSH_GRASS_BLOCK.get());

	protected static final ProcessorRule BOG_TRIAL_FLOOR = createAlwaysTrueRandomBlockMatchTest(BWGBlocks.PALE_MUD_BRICKS_SET.getBase(), 0.5f, BWGBlocks.PACKED_PALE_MUD.get());

	protected static ProcessorRule streetWater(Block path, Block waterReplacement) {
		return new ProcessorRule(new BlockMatchTest(path), new BlockMatchTest(Blocks.WATER), waterReplacement.defaultBlockState());
	}

	protected static ProcessorRule skyrisLeavesToGreenAppleLeaves(float chance) {
		return createAlwaysTrueRandomBlockMatchTest(BWGWood.SKYRIS.leaves(), chance, BWGWood.SKYRIS_LEAVES_GREEN_APPLE.get());
	}

	protected static ProcessorRule skyrisLeavesToFlowering(float chance) {
		return createAlwaysTrueRandomBlockMatchTest(BWGWood.SKYRIS.leaves(), chance, BWGWood.FLOWERING_SKYRIS_LEAVES.get());
	}

	protected static ProcessorRule spiritLeavesToFlowering(float chance) {
		return createAlwaysTrueRandomBlockMatchTest(BWGWood.SPIRIT_LEAVES.get(), chance, BWGWood.FLOWERING_SPIRIT_LEAVES.get().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
	}

	protected static ProcessorRule airToBlock(Block block, float chance) {
		return createAlwaysTrueRandomBlockMatchTest(Blocks.AIR, chance, block);
	}

	private static ProcessorRule createAlwaysTrueRandomBlockMatchTest(Block start, float chance, Block newBlock) {
		return createProcessorRule(createRandomBlockMatchTest(start, chance), AlwaysTrueTest.INSTANCE, newBlock.defaultBlockState());
	}

	private static ProcessorRule createAlwaysTrueRandomBlockMatchTest(Block start, float chance, BlockState newBlock) {
		return createProcessorRule(createRandomBlockMatchTest(start, chance), AlwaysTrueTest.INSTANCE, newBlock);
	}

	private static ProcessorRule createAlwaysTrueBlockMatchTest(Block start, BlockState newBlock) {
		return createProcessorRule(createBlockMatchTest(start), AlwaysTrueTest.INSTANCE, newBlock);
	}

	private static BlockMatchTest createBlockMatchTest(Block start) {
		return new BlockMatchTest(start);
	}

	private static RandomBlockMatchTest createRandomBlockMatchTest(Block block, float chance) {
		return new RandomBlockMatchTest(block, chance);
	}

	private static ProcessorRule createProcessorRule(RuleTest test, AlwaysTrueTest alwaysTrueTest, BlockState blockState) {
		return new ProcessorRule(test, alwaysTrueTest, blockState);
	}
}
