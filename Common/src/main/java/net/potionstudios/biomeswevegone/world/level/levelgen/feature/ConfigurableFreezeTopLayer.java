package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ConfigurableFreezeTopLayer extends Feature<ConfigurableFreezeTopLayer.Config> {


    public ConfigurableFreezeTopLayer(Codec<ConfigurableFreezeTopLayer.Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ConfigurableFreezeTopLayer.Config> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();

        Config config = context.config();

        BlockStateProvider ice = config.ice();
        BlockStateProvider snow = config.snow();

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int k = blockPos.getX() + i;
                int l = blockPos.getZ() + j;
                int m = worldGenLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
                mutableBlockPos.set(k, m, l);
                mutableBlockPos2.set(mutableBlockPos).move(Direction.DOWN, 1);
                Biome biome = worldGenLevel.getBiome(mutableBlockPos).value();
                if (biome.shouldFreeze(worldGenLevel, mutableBlockPos2, false)) {
                    worldGenLevel.setBlock(mutableBlockPos2, ice.getState(context.random(), mutableBlockPos2), 2);
                }

                if (biome.shouldSnow(worldGenLevel, mutableBlockPos)) {
                    worldGenLevel.setBlock(mutableBlockPos, Blocks.SNOW.defaultBlockState(), 2);
                    BlockState blockState = worldGenLevel.getBlockState(mutableBlockPos2);
                    if (blockState.hasProperty(SnowyDirtBlock.SNOWY)) {
                        worldGenLevel.setBlock(mutableBlockPos2, blockState.setValue(SnowyDirtBlock.SNOWY, true), 2);
                    }
                }
            }
        }

        return true;
    }

    public record Config(BlockStateProvider ice, BlockStateProvider snow) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        BlockStateProvider.CODEC.fieldOf("ice").forGetter(Config::ice),
                        BlockStateProvider.CODEC.fieldOf("snow").forGetter(Config::snow)
                ).apply(instance, Config::new)
        );
    }
}
