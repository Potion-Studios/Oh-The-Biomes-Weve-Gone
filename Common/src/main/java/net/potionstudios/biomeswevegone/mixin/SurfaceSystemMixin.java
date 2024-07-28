package net.potionstudios.biomeswevegone.mixin;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules.BandsContext;
import net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules.BandsRuleSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(SurfaceSystem.class)
public abstract class SurfaceSystemMixin implements BandsContext {

    @Unique
    private final Map<BandsRuleSource, BlockState[]> bandsLookup = new Reference2ObjectOpenHashMap<>();

    @Shadow
    protected abstract void erodedBadlandsExtension(BlockColumn blockColumn, int x, int z, int height, LevelHeightAccessor level);

    @Shadow
    @Final
    public PositionalRandomFactory noiseRandom;

    @Shadow @Final private NormalNoise surfaceSecondaryNoise;

    @Inject(method = "buildSurface", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getHeight(Lnet/minecraft/world/level/levelgen/Heightmap$Types;II)I", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectShatteredGlacierExtension(RandomState randomState, BiomeManager biomeManager, Registry<Biome> biomes, boolean useLegacyRandomSource, WorldGenerationContext context, ChunkAccess chunk, NoiseChunk noiseChunk, SurfaceRules.RuleSource ruleSource, CallbackInfo ci, BlockPos.MutableBlockPos mutableBlockPos, ChunkPos chunkPos, int i, int j, BlockColumn blockColumn, SurfaceRules.Context context2, SurfaceRules.SurfaceRule surfaceRule, BlockPos.MutableBlockPos mutableBlockPos2, int k, int l, int m, int n, int o, Holder<Biome> holder) {
        if (holder.is(BWGBiomes.SHATTERED_GLACIER) || holder.is(BWGBiomes.ERODED_BOREALIS)) {
            this.erodedBadlandsExtension(blockColumn, m, n, o, chunk);
        }
    }

    @Override
    public BlockState getBandsState(BandsRuleSource bandsRuleSource, SimpleWeightedRandomList<BlockState> bandStates, IntProvider bandSizeProvider, IntProvider bandsCountProvider, int x, int y, int z, float frequency, int noiseScale) {
        BlockState[] blockStates = bandsLookup.computeIfAbsent(bandsRuleSource, key -> {
            List<BlockState> states = new ArrayList<>();
            RandomSource random = this.noiseRandom.at(BlockPos.ZERO);
            int bandsCount = bandsCountProvider.sample(random);

            for (int bandIdx = 0; bandIdx < bandsCount; bandIdx++) {
                int bandSize = bandSizeProvider.sample(random);
                BlockState state = bandStates.getRandomValue(random).orElseThrow();
                for (int size = 0; size < bandSize; size++) {
                    states.add(state);
                }
            }
            return states.toArray(new BlockState[0]);


        });

        double scaledNoise = this.surfaceSecondaryNoise.getValue(x * frequency, 0, z * frequency) * noiseScale;
        int stateIndex = Math.floorMod(y + Math.round(scaledNoise), blockStates.length - 1);
        return blockStates[stateIndex];
    }
}
