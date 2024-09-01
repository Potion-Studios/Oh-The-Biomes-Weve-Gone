package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors.FruitBlockProcessor;

public class BWGCustomStructureProcessors {

    public static final StructureProcessorType<FruitBlockProcessor> FRUIT_BLOCK_PROCESSOR = register("fruit_block_processor", FruitBlockProcessor.CODEC);

    /**
     * Utility method for registering custom structure processor types.
     */
    private static <P extends StructureProcessor> StructureProcessorType<P> register(String id, Codec<P> codec) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, BiomesWeveGone.id(id), () -> codec);
    }

    public static void init() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Custom Structure Processors");
    }
}
