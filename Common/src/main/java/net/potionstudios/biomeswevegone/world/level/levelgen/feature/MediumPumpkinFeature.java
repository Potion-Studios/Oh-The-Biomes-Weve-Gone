package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import corgitaco.corgilib.world.level.RandomTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MediumPumpkinFeature extends Feature<NoneFeatureConfiguration> {
    public MediumPumpkinFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                mutable.setWithOffset(origin, x, -1, z);

                if (!level.getBlockState(mutable).canOcclude()) {
                    return false;
                }
            }
        }

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    mutable.setWithOffset(origin, x, y, z);
                    if(level.getBlockState(mutable).canOcclude()) {
                        return false;
                    }
                }
            }
        }


        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    mutable.setWithOffset(origin, x, y, z);
                    level.setBlock(mutable, Blocks.PUMPKIN.defaultBlockState(), 2);

                    if (y == 0) {
                        ChunkAccess chunk = level.getChunk(mutable);

                        if (chunk instanceof RandomTickScheduler randomTickScheduler) {
                            randomTickScheduler.scheduleRandomTick(mutable);
                        }
                    }
                }
            }
        }

        return true;
    }
}
