package net.potionstudios.biomeswevegone.util;

import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;

/**
 * @author CorgiTaco
 */
public interface GeneratorHeightGetter {


    int getHeight(ChunkGenerator generator, Heightmap.Types heightmapType, int worldX, int worldZ, RandomState state, boolean sampleRaw);
}
