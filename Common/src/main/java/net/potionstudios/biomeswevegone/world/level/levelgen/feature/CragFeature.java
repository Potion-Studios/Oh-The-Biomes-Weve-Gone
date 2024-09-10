package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.potionstudios.biomeswevegone.util.MathUtil;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import org.joml.Vector4d;

public class CragFeature extends Feature<NoneFeatureConfiguration> {
    public CragFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {

        if (true) {
            return true;
        }
        WorldGenLevel level = context.level();
        ImprovedNoise noise = new ImprovedNoise(new XoroshiroRandomSource(level.getSeed()));

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                mutableBlockPos.setWithOffset(context.origin(), x, 0, z);
                double noiseFreq = 0.01;
                Vector4d vector4d = MathUtil.calcHexInfo(new Vector2d(mutableBlockPos.getX(), mutableBlockPos.getZ()), 30);

                Vector2d hexCenter = new Vector2d(mutableBlockPos.getX() - vector4d.x, mutableBlockPos.getZ() - vector4d.y);

                int centerY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) hexCenter.x, (int) hexCenter.y);

                BlockPos centerPos = new BlockPos((int) hexCenter.x, centerY, (int) hexCenter.y);
                if (!level.getBiome(centerPos).is(BWGBiomes.CRAG_GARDENS)) {
                    continue;
                }

                double height = getHeight(mutableBlockPos, level, noise, noiseFreq);

                BlockState state = Blocks.STONE.defaultBlockState();




                for (int y = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, mutableBlockPos.getX(), mutableBlockPos.getZ()) - 10; y < height; y++) {
                    mutableBlockPos.setY(y);
                    level.setBlock(mutableBlockPos, state, 2);
                }
            }
        }

        return true;
    }

    public static double getHeight(BlockPos pos, WorldGenLevel worldGenLevel, ImprovedNoise noise, double noiseFreq) {
        int radius = 3;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        double centerHeight = getHexHeight(noise, noiseFreq, mutableBlockPos);


        double lowestHexHeight = Double.MAX_VALUE;
        double highestHexHeight = Double.MAX_VALUE;
        BlockPos toBlend = null;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x == 0 && z == 0 ) {
                    continue;
                }

                mutableBlockPos.setWithOffset(pos, x, 0, z);
                double hexHeight = getHexHeight(noise, noiseFreq, mutableBlockPos);
                if (hexHeight < lowestHexHeight) {
                    lowestHexHeight = hexHeight;
                    toBlend = mutableBlockPos.immutable();
                }
            }
        }

        return Mth.clampedLerp(centerHeight, lowestHexHeight, Mth.square(radius) / pos.distSqr(toBlend)) / 2;
    }

    private static double getHexHeight(ImprovedNoise noise, double noiseFreq, BlockPos.MutableBlockPos mutableBlockPos) {
        Vector4d vector4d = MathUtil.calcHexInfo(new Vector2d(mutableBlockPos.getX(), mutableBlockPos.getZ()), 30);

        Vector2d hexCenter = new Vector2d(mutableBlockPos.getX() - vector4d.x, mutableBlockPos.getZ() - vector4d.y);

        double heightDelta = (noise.noise(hexCenter.x * noiseFreq, 0, hexCenter.y * noiseFreq) + 1) * 0.5F;
        return BlendingFunction.EaseInOutCirc.INSTANCE.apply(heightDelta, 150, 250);
    }


    public static double random(Vector2d co) {
        return (Math.sin(co.dot(new Vector2d(12.9898, 78.233))) * 43758.5453f % 1.0);
    }
}
