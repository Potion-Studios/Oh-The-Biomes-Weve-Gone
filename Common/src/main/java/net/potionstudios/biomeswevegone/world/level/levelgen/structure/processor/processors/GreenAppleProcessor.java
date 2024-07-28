package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.plants.tree.fruit.BWGFruitBlock;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.BWGCustomStructureProcessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GreenAppleProcessor extends StructureProcessor {

	public static final GreenAppleProcessor INSTANCE = new GreenAppleProcessor();
	public static final Codec<GreenAppleProcessor> CODEC = Codec.unit(() -> INSTANCE);

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo processBlock(@NotNull LevelReader level, @NotNull BlockPos offset, @NotNull BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.@NotNull StructureBlockInfo relativeBlockInfo, @NotNull StructurePlaceSettings settings) {
		if (blockInfo.state().getBlock() == BWGBlocks.GREEN_APPLE_FRUIT_BLOCK.get() && level.getBlockState(pos.below()).isAir()) {
			BiomesWeveGone.LOGGER.info("Green Apple Processor: Placing green apple fruit block at {}", pos.below());
			return new StructureTemplate.StructureBlockInfo(pos.below(), BWGBlocks.GREEN_APPLE_FRUIT_BLOCK.get().defaultBlockState().setValue(BWGFruitBlock.AGE, ((Level) level).getRandom().nextInt(3)), relativeBlockInfo.nbt());
		} return blockInfo;
	}

	@Override
	protected @NotNull StructureProcessorType<?> getType() {
		return BWGCustomStructureProcessors.GREEN_APPLE_PROCESSOR;
	}
}
