package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.canyon.CanyonPiece;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.canyon.CanyonStructure;

public class CanyonFeature extends Feature<NoneFeatureConfiguration> {
    public CanyonFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        long seed = level.getSeed();
        BlockPos origin = context.origin();
        LongArrayList randomPositions = CanyonStructure.getRandomPositions(seed, 2, origin);

        BoundingBox area = new BoundingBox(origin).inflatedBy(512);

        LongLinkedOpenHashSet riverPositions = CanyonStructure.getRiverPositions(seed, randomPositions, area);

        CanyonPiece.generate(level, context.chunkGenerator(), new ChunkPos(origin), riverPositions.toLongArray());

        return true;
    }
}
