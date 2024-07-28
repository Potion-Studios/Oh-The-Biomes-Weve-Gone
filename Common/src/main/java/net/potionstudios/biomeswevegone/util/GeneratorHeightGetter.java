package net.potionstudios.biomeswevegone.util;

import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;

public interface GeneratorHeightGetter {


    int getHeight(ChunkGenerator generator, Heightmap.Types heightmapType, int worldX, int worldZ, RandomState state, boolean sampleRaw);
}
