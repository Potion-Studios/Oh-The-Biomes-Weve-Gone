package net.potionstudios.biomeswevegone.world.level.levelgen;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record CheckedBlockPlacement(List<Pair<BlockPredicate, BlockStateProvider>> blockPlacement) {

    public static final Codec<CheckedBlockPlacement> CODEC = Codec.pair(BlockPredicate.CODEC.fieldOf("check").codec(), BlockStateProvider.CODEC.fieldOf("blockStateProvider").codec()).listOf().xmap(CheckedBlockPlacement::new, CheckedBlockPlacement::blockPlacement);
}
