package net.potionstudios.biomeswevegone.world.level.levelgen.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Function;

public class BlendUtil {

    public static double blendBiomeEdge(Holder<Biome> currentBiome, Function<BlockPos, Holder<Biome>> biomeGetter, BlockPos origin, int blendRadius, int blendStep) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();


        int size = Math.floorDiv(blendRadius, blendStep);

        for (BlockPos.MutableBlockPos blockPos : BlockPos.spiralAround(BlockPos.ZERO, size, Direction.EAST, Direction.SOUTH)) {
            int x = origin.getX() + blockPos.getX() * blendStep;
            int z = origin.getZ() + blockPos.getZ() * blendStep;
            mutableBlockPos.set(x, origin.getY(), z);
            Holder<Biome> nearbyBiome = biomeGetter.apply(mutableBlockPos);
            if (nearbyBiome != currentBiome) {
                double distToLowCornerSqr = mutableBlockPos.distToLowCornerSqr(origin.getX(), origin.getY(), origin.getZ());

                if (distToLowCornerSqr < Mth.square(blendRadius)) {

                    return  distToLowCornerSqr / Mth.square(blendRadius);
                }

            }
        }
        return 1;
    }

}
