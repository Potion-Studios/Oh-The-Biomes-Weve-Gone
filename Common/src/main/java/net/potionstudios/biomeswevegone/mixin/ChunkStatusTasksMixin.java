package net.potionstudios.biomeswevegone.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatusTasks;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.levelgen.Noises;
import net.potionstudios.biomeswevegone.world.level.levelgen.customterrain.BasaltBarreraExtension;
import net.potionstudios.biomeswevegone.world.level.levelgen.customterrain.CragGardenExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(ChunkStatusTasks.class)
public class ChunkStatusTasksMixin {

    @Inject(method = "generateSurface", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkGenerator;buildSurface(Lnet/minecraft/server/level/WorldGenRegion;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;)V"))
    private static void injectExtensions(WorldGenContext worldGenContext, ChunkStep step, StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk, CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir, @Local ServerLevel serverLevel, @Local WorldGenRegion worldGenRegion) {
        BiomeManager biomeManager = worldGenRegion.getBiomeManager().withDifferentSource((x, y, z) -> worldGenContext.generator().getBiomeSource().getNoiseBiome(x, y, z, worldGenContext.level().getChunkSource().randomState().sampler()));

        CragGardenExtension.runCragGardenExtension(biomeManager::getBiome, chunk, serverLevel.getSeed(), worldGenRegion.registryAccess().registryOrThrow(Registries.NOISE).getOrThrow(Noises.SURFACE), worldGenRegion.registryAccess().registryOrThrow(Registries.NOISE).getOrThrow(Noises.SURFACE_SECONDARY));
//        TropicalIslandExtension.runTropicalIslandExtension(worldGenRegion::getBiome, chunkAccess, serverLevel.getSeed());
        BasaltBarreraExtension.runBasaltBarreraExtension(biomeManager::getBiome, chunk, worldGenRegion, worldGenContext.generator());
    }
}