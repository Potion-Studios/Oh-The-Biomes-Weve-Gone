package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import corgitaco.corgilib.world.level.RandomTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LargePumpkinFeature extends Feature<NoneFeatureConfiguration> {
    public LargePumpkinFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        PillarFeature.DistanceTestType testType = PillarFeature.DistanceTestType.EUCLIDEAN;

        RandomSource random = context.random();

        int radius = BiasedToBottomInt.of(1, 3).sample(random);

        int diameter = radius + radius + 1;

        BlockPos featureOrigin = context.origin();
        BlockPos origin = featureOrigin.offset(0, diameter / 2, 0);


        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        WorldGenLevel level = context.level();

        int surfaceRadiusCheck = radius - 1;

        if (surfaceRadiusCheck == 0) {
            if(!level.getBlockState(mutableBlockPos.set(featureOrigin.getX(),origin.getY() - 1, featureOrigin.getZ())).canOcclude()) {
                return false;
            }
        } else {
            for (int x = -surfaceRadiusCheck; x <= surfaceRadiusCheck; x++) {
                for (int z = -surfaceRadiusCheck; z <= surfaceRadiusCheck; z++) {
                    mutableBlockPos.setWithOffset(featureOrigin, x, -1, z);
                    if (!level.getBlockState(mutableBlockPos).canOcclude()) {
                        return false;
                    }
                }
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    mutableBlockPos.setWithOffset(origin, x, y, z);
                    if (testType.getDistanceTester().withinDistance(origin, mutableBlockPos, radius + (radius * 0.66))) {
                        if(level.getBlockState(mutableBlockPos).canOcclude()) {
                            return false;
                        }
                    }
                }
            }
        }



        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    mutableBlockPos.setWithOffset(origin, x, y, z);
                    if (testType.getDistanceTester().withinDistance(origin, mutableBlockPos, radius + (radius * 0.66))) {
                        level.setBlock(mutableBlockPos, Blocks.PUMPKIN.defaultBlockState(), 2);
                        if (y == -radius) {
                            ChunkAccess chunk = level.getChunk(mutableBlockPos);
                            if (chunk instanceof RandomTickScheduler randomTickScheduler) {
                                randomTickScheduler.scheduleRandomTick(mutableBlockPos.move(Direction.DOWN));
                            }
                        }
                    }
                }
            }
        }

        int stemLength = radius + 1;

        for (int stemIdx = 0; stemIdx < stemLength; stemIdx++) {
            mutableBlockPos.set(origin.getX(), origin.getY() + radius + stemIdx, origin.getZ());
            level.setBlock(mutableBlockPos, Blocks.OAK_LOG.defaultBlockState(), 2);
        }

        mutableBlockPos.move(Direction.UP);
        mutableBlockPos.move(Direction.Plane.HORIZONTAL.getRandomDirection(random));

        level.setBlock(mutableBlockPos, Blocks.OAK_LOG.defaultBlockState(), 2);

        BlockPos.MutableBlockPos leavesMutable = new BlockPos.MutableBlockPos();

        for (Direction value : Direction.values()) {
            leavesMutable.setWithOffset(mutableBlockPos, value);

            if (level.getBlockState(leavesMutable).isAir()) {
                level.setBlock(leavesMutable, Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, false).setValue(LeavesBlock.DISTANCE, 1), 2);
            }
        }

        return true;
    }
}
