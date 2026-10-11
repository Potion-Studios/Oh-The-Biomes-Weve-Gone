package net.potionstudios.biomeswevegone.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import org.spongepowered.asm.mixin.Unique;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Noises;
import net.potionstudios.biomeswevegone.world.level.levelgen.customterrain.BasaltBarreraExtension;
import net.potionstudios.biomeswevegone.world.level.levelgen.customterrain.CragGardenExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ChunkStatus.class)
public abstract class ChunkStatusMixin {

    @Inject(method = "method_16569", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void injectCragTerrain(ChunkStatus chunkStatus, ServerLevel serverLevel, ChunkGenerator chunkGenerator, List<?> list, ChunkAccess chunkAccess, CallbackInfo ci, WorldGenRegion worldGenRegion) {
        if (biomeswevegone$mayContainBiome(worldGenRegion, chunkAccess, BWGBiomes.CRAG_GARDENS)) {
            CragGardenExtension.runCragGardenExtension(worldGenRegion::getBiome, chunkAccess, serverLevel.getSeed(), worldGenRegion.registryAccess().registryOrThrow(Registries.NOISE).getOrThrow(Noises.SURFACE), worldGenRegion.registryAccess().registryOrThrow(Registries.NOISE).getOrThrow(Noises.SURFACE_SECONDARY));
        }
        if (biomeswevegone$mayContainBiome(worldGenRegion, chunkAccess, BWGBiomes.BASALT_BARRERA)) {
            BasaltBarreraExtension.runBasaltBarreraExtension(chunkAccess, worldGenRegion, chunkGenerator);
        }
    }
    @Unique
    private static boolean biomeswevegone$mayContainBiome(WorldGenRegion region, ChunkAccess chunk, ResourceKey<Biome> biome) {
        ChunkPos center = chunk.getPos();
        if (!region.getCenter().equals(center)) {
            return true;
        }
        // Fuzzed block biome lookups may reach one quart into any neighboring chunk.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkAccess neighbor = region.getChunk(center.x + dx, center.z + dz, ChunkStatus.BIOMES, false);
                if (neighbor == null || (dx == 0 && dz == 0 && neighbor != chunk)) {
                    return true;
                }
                for (LevelChunkSection section : neighbor.getSections()) {
                    if (section.getBiomes().maybeHas(holder -> holder.is(biome))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
