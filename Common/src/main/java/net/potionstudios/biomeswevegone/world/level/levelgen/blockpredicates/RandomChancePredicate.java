package net.potionstudios.biomeswevegone.world.level.levelgen.blockpredicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import org.jetbrains.annotations.NotNull;

public class RandomChancePredicate implements BlockPredicate {

    public static final MapCodec<RandomChancePredicate> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    FloatProvider.CODEC.fieldOf("chance").forGetter(randomChancePredicate -> randomChancePredicate.chance)
            ).apply(instance, RandomChancePredicate::new)
    );

    private final FloatProvider chance;

    public RandomChancePredicate(FloatProvider chance) {
        this.chance = chance;
    }

    @Override
    public @NotNull BlockPredicateType<?> type() {
        return BWGBlockPredicateTypes.RANDOM_CHANCE.get();
    }

    @Override
    public boolean test(WorldGenLevel level, BlockPos blockPos) {
        XoroshiroRandomSource xoroshiroRandomSource = new XoroshiroRandomSource(blockPos.asLong() + level.getSeed());
        return xoroshiroRandomSource.nextDouble() < this.chance.sample(xoroshiroRandomSource);
    }
}
