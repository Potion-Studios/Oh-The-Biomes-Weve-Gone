package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.BWGCustomStructureProcessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlantProcessor extends StructureProcessor {

	public static final MapCodec<PlantProcessor> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ground").forGetter(processor -> processor.ground),
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("plant").forGetter(processor -> processor.plant),
					Codec.FLOAT.fieldOf("chance").forGetter(processor -> processor.chance))
					.apply(instance, PlantProcessor::new));

	private final Block ground;
	private final Block plant;
	private final IntegerProperty ageProperty;
	private final float chance;

	public PlantProcessor(Block ground, Block plant, float chance) {
		this(ground, plant, (IntegerProperty) plant.defaultBlockState().getProperties().stream().filter(property -> property.getName().equals("age")).findFirst().get(), chance);
	}

	private PlantProcessor(Block ground, Block plant, IntegerProperty AgeProperty, float chance) {
		this.ground = ground;
		this.plant = plant;
		this.ageProperty = AgeProperty;
		this.chance = chance;
	}

	@Override
	public @NotNull List<StructureTemplate.StructureBlockInfo> finalizeProcessing(@NotNull ServerLevelAccessor serverLevel, @NotNull BlockPos offset, @NotNull BlockPos pos, @NotNull List<StructureTemplate.StructureBlockInfo> originalBlockInfos, @NotNull List<StructureTemplate.StructureBlockInfo> processedBlockInfos, @NotNull StructurePlaceSettings settings) {
		List<StructureTemplate.StructureBlockInfo> newInfo = new java.util.ArrayList<>(List.copyOf(processedBlockInfos));

		processedBlockInfos.stream().filter(structureBlockInfo -> structureBlockInfo.state().is(ground)).forEach(block -> {
			BlockPos belowPos = block.pos();
			newInfo.stream().filter(structureBlockInfo -> structureBlockInfo.pos().equals(belowPos.above())).findFirst().ifPresent(spot -> {
				if (spot.state().isAir() && serverLevel.getRandom().nextFloat() < chance) {
					newInfo.remove(spot);
					newInfo.add(new StructureTemplate.StructureBlockInfo(spot.pos(), plant.defaultBlockState().setValue(ageProperty, settings.getRandom(spot.pos()).nextInt(ageProperty.getPossibleValues().size())), spot.nbt()));
				}
			});
		});

		return newInfo;
	}

	@Override
	protected @NotNull StructureProcessorType<?> getType() {
		return BWGCustomStructureProcessors.PLANT_PROCESSOR.get();
	}
}
