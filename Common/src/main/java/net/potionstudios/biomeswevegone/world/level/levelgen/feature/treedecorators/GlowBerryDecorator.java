package net.potionstudios.biomeswevegone.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class GlowBerryDecorator extends TreeDecorator {

    public static final MapCodec<GlowBerryDecorator> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    FloatProvider.CODEC.fieldOf("probability").forGetter(glowBerryDecorator -> glowBerryDecorator.probability),
                    IntProvider.CODEC.fieldOf("length").forGetter(glowBerryDecorator -> glowBerryDecorator.length),
                    FloatProvider.CODEC.fieldOf("berries_probability").forGetter(glowBerryDecorator -> glowBerryDecorator.berriesProbability)
            ).apply(instance, GlowBerryDecorator::new)
    );

    private final FloatProvider probability;
    private final IntProvider length;
    private final FloatProvider berriesProbability;

    public GlowBerryDecorator(FloatProvider probability, IntProvider length, FloatProvider berriesProbability) {
        this.probability = probability;
        this.length = length;
        this.berriesProbability = berriesProbability;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return BWGTreeDecorators.GLOW_BERRY_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

        float probability = this.probability.sample(random);
        int lengthSample = length.sample(random);
        float berriesProbability = this.berriesProbability.sample(random);

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (BlockPos log : context.logs()) {
            if (random.nextFloat() < probability) {

                mutable.set(log).move(Direction.DOWN);

                if (context.isAir(mutable) && context.isAir(mutable.move(Direction.DOWN))) {
                    mutable.move(Direction.UP);
                    boolean shouldBreak = false;
                    for (int i = 1; i <= lengthSample; i++) {
                        BlockState state;

                        if (context.isAir(mutable.offset(0, -1, 0)) && context.isAir(mutable.offset(0, -2, 0))) {
                            state = i == lengthSample ? Blocks.CAVE_VINES.defaultBlockState() : Blocks.CAVE_VINES_PLANT.defaultBlockState();
                        } else {
                            state = Blocks.CAVE_VINES.defaultBlockState();
                            shouldBreak = true;
                        }

                        float chance = random.nextFloat();
                        boolean value = chance < berriesProbability;
                        state = state.setValue(BlockStateProperties.BERRIES, value);
                        if (state.hasProperty(BlockStateProperties.AGE_25)) {
                            state = state.setValue(BlockStateProperties.AGE_25, Mth.randomBetweenInclusive(random, 0, 25));
                        }

                        context.setBlock(mutable, state);

                        if (shouldBreak) {
                            break;
                        }
                        mutable.move(Direction.DOWN);
                    }
                }
            }

        }
    }
}
