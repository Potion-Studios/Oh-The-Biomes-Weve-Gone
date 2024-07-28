package net.potionstudios.biomeswevegone.world.level.levelgen.structure.arch;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.world.level.levelgen.CheckedBlockPlacement;

import java.util.function.Consumer;

public record ArchConfig(
        IntProvider length,
        IntProvider height,
        CheckedBlockPlacement checkedBlockPlacement,
        ArchGeneratorConfig archGeneratorConfig
) {


    public static final Codec<ArchConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    IntProvider.POSITIVE_CODEC.fieldOf("length").forGetter(ArchConfig::length),
                    IntProvider.POSITIVE_CODEC.fieldOf("height").forGetter(ArchConfig::height),
                    CheckedBlockPlacement.CODEC.fieldOf("block_placement").forGetter(ArchConfig::checkedBlockPlacement),
                    ArchGeneratorConfig.CODEC.fieldOf("arch_generator").forGetter(ArchConfig::archGeneratorConfig)
            ).apply(instance, ArchConfig::new)
    );

    public record ArchGeneratorConfig(
            int xzStepDistance,
            int xyzStepDistance,
            SimpleWeightedRandomList<GenerationConfig> generationConfigs,
            SimpleWeightedRandomList<BlendingFunction> blendFunction
    ) {
        public static final Codec<ArchGeneratorConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.INT.fieldOf("xz_step_distance").forGetter(ArchGeneratorConfig::xzStepDistance),
                        Codec.INT.fieldOf("xyz_step_distance").forGetter(ArchGeneratorConfig::xyzStepDistance),
                        SimpleWeightedRandomList.wrappedCodec(GenerationConfig.CODEC).fieldOf("generation_configs").forGetter(ArchGeneratorConfig::generationConfigs),
                        SimpleWeightedRandomList.wrappedCodec(BlendingFunction.CODEC).fieldOf("blending_function_picker").forGetter(ArchGeneratorConfig::blendFunction)
                ).apply(instance, ArchGeneratorConfig::new)
        );


        public void generate(long seed, int height, Vec3 start, Vec3 origin, Vec3 end, BoundingBox box, Consumer<BlockPos> action) {
            XoroshiroRandomSource xoroshiroRandomSource = new XoroshiroRandomSource(seed);
            ArchStructure.between(origin, start, this.xzStepDistance, height, this.xyzStepDistance, this.blendFunction.getRandomValue(xoroshiroRandomSource).orElseThrow(), stepOrigin ->
                    this.generationConfigs.getRandomValue(xoroshiroRandomSource).orElseThrow().generate(seed, stepOrigin, box, action)
            );
            ArchStructure.between(origin, end, this.xzStepDistance, height, this.xyzStepDistance, this.blendFunction.getRandomValue(xoroshiroRandomSource).orElseThrow(), stepOrigin ->
                    this.generationConfigs.getRandomValue(xoroshiroRandomSource).orElseThrow().generate(seed, stepOrigin, box, action)
            );
        }
    }

    public record GenerationConfig(
            IntProvider thickness,
            FloatProvider noiseFreq
    ) {
        public static final Codec<GenerationConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        IntProvider.POSITIVE_CODEC.fieldOf("thickness").forGetter(GenerationConfig::thickness),
                        FloatProvider.CODEC.fieldOf("noise_frequency").forGetter(GenerationConfig::noiseFreq)
                ).apply(instance, GenerationConfig::new)
        );


        public void generate(long seed, BlockPos stepOrigin, BoundingBox box, Consumer<BlockPos> action) {
            XoroshiroRandomSource randomSource = new XoroshiroRandomSource(seed + stepOrigin.asLong());
            ArchStructure.generate(seed, this.thickness.sample(randomSource), this.noiseFreq.sample(randomSource), stepOrigin, box, action);
        }
    }
}
