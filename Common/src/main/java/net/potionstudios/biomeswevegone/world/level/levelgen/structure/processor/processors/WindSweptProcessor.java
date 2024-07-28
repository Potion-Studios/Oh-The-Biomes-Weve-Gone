package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.sand.BWGSandSet;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.BWGCustomStructureProcessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WindSweptProcessor extends StructureProcessor {
    public static final WindSweptProcessor INSTANCE = new WindSweptProcessor();
    public static final Codec<WindSweptProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings) {
        if (level.getBiome(pos).is(BWGBiomes.WINDSWEPT_DESERT)) {
            Block block = blockInfo.state().getBlock();
            BWGSandSet sandSet = BWGBlocks.WINDSWEPT_SAND_SET;
            if (block == Blocks.SAND)
                return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), sandSet.getSand().defaultBlockState(), blockInfo.nbt());
            else if (block == Blocks.SANDSTONE)
                return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), sandSet.getSandstone().defaultBlockState(), blockInfo.nbt());
            else if (block == Blocks.CUT_SANDSTONE)
                return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), sandSet.getCutSandstone().defaultBlockState(), blockInfo.nbt());
            else if (block == Blocks.CHISELED_SANDSTONE)
                return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), sandSet.getChiseledSandstone().defaultBlockState(), blockInfo.nbt());
            else if (block == Blocks.SMOOTH_SANDSTONE)
                return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), sandSet.getSmoothSandstone().defaultBlockState(), blockInfo.nbt());
        }
        return super.processBlock(level, offset, pos, blockInfo, relativeBlockInfo, settings);
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return BWGCustomStructureProcessors.WINDSWEPT_PROCESSOR;
    }
}
