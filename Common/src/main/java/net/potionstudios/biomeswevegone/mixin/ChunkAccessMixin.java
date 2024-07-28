package net.potionstudios.biomeswevegone.mixin;

import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.potionstudios.biomeswevegone.util.GeneratorHeightGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ChunkAccess.class)
public abstract class ChunkAccessMixin implements GeneratorHeightGetter {

    @Shadow
    public abstract ChunkStatus getStatus();

    @Shadow
    public abstract int getHeight(Heightmap.Types type, int x, int z);

    @Unique
    int[] cachedGeneratorHeights = null;

    @Override
    public int getHeight(ChunkGenerator generator, Heightmap.Types heightmapType, int worldX, int worldZ, RandomState randomState, boolean sampleRaw) {
        if (!sampleRaw && getStatus().isOrAfter(ChunkStatus.NOISE)) {
            return getHeight(heightmapType, worldX, worldZ);
        } else {
            if (cachedGeneratorHeights == null) {
                cachedGeneratorHeights = new int[16 * 16];
                Arrays.fill(cachedGeneratorHeights, Integer.MIN_VALUE);
            }

            int localX = worldX & 15;
            int localZ = worldZ & 15;
            int index = localX + localZ * 16;
            if (cachedGeneratorHeights[index] == Integer.MIN_VALUE) {
                cachedGeneratorHeights[index] = generator.getBaseHeight(worldX, worldZ, heightmapType, (ChunkAccess) (Object) this, randomState);
            }

            return cachedGeneratorHeights[index];
        }
    }
}