package net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class BandsRuleSource implements SurfaceRules.RuleSource {

    public static final KeyDispatchDataCodec<BandsRuleSource> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC).fieldOf("band_states").forGetter(bandsRuleSource -> bandsRuleSource.bandStates),
                    IntProvider.POSITIVE_CODEC.fieldOf("band_size").forGetter(bandsRuleSource -> bandsRuleSource.bandSizeProvider),
                    IntProvider.POSITIVE_CODEC.fieldOf("bands_count").forGetter(bandsRuleSource -> bandsRuleSource.bandsCountProvider),
                    Codec.FLOAT.fieldOf("frequency").forGetter(bandsRuleSource -> bandsRuleSource.frequency),
                    Codec.INT.fieldOf("noise_scale").forGetter(bandsRuleSource -> bandsRuleSource.noiseScale)
            ).apply(builder, BandsRuleSource::new))
    );


    private final SimpleWeightedRandomList<BlockState> bandStates;
    private final IntProvider bandSizeProvider;
    private final IntProvider bandsCountProvider;
    private final float frequency;
    private final int noiseScale;


    public BandsRuleSource(SimpleWeightedRandomList<BlockState> bandStates, IntProvider bandSizeProvider, IntProvider bandsCountProvider, float frequency, int noiseScale) {
        this.bandStates = bandStates;
        this.bandSizeProvider = bandSizeProvider;
        this.bandsCountProvider = bandsCountProvider;
        this.frequency = frequency;
        this.noiseScale = noiseScale;
    }


    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
        return (x, y, z) -> {
            if (context.system instanceof BandsContext bandsContext) {
                return bandsContext.getBandsState(this, this.bandStates, this.bandSizeProvider, this.bandsCountProvider, x, y, z, this.frequency, this.noiseScale);
            }
            return Blocks.STONE.defaultBlockState();
        };
    }
}
