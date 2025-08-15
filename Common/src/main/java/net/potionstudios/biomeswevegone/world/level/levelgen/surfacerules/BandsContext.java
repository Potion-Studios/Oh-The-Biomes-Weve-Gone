package net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules;

import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;

public interface BandsContext {
    BlockState getBandsState(BandsRuleSource source, WeightedList<BlockState> bandStates, IntProvider bandSizeProvider, IntProvider bandsCountProvider, int x, int y, int z, float frequency, int noiseScale);
}
