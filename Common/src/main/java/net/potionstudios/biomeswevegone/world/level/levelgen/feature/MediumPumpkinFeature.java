package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import dev.corgitaco.ohthetreesyoullgrow.world.level.chunk.RandomTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MediumPumpkinFeature extends Feature<NoneFeatureConfiguration> {
    private static final ResourceKey<Feature<?>> AUTUMNITY_PUMPKIN_FEATURE = ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath("autumnity", "pumpkin_fields_pumpkin"));

    public MediumPumpkinFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext context) {
        WorldGenLevel level = context.level();

        Holder.Reference<Feature<?>> featureHolder = level.registryAccess().lookupOrThrow(Registries.FEATURE).get(AUTUMNITY_PUMPKIN_FEATURE).orElse(null);

        if (featureHolder != null)
            return featureHolder.value().place(context);

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        BlockPos origin = context.origin();

        if (level.getLevel().structureManager().hasAnyStructureAt(origin))
            return false;

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
