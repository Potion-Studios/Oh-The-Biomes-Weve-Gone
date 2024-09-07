package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FlowerableBushBlock extends BushBlock implements BonemealableBlock {

    private final @Nullable Supplier<FloweringBushBlock> floweringBlock;
    private static final MapCodec<FlowerableBushBlock> CODEC = simpleCodec(FlowerableBushBlock::new);


    public FlowerableBushBlock(Properties properties, @Nullable Supplier<FloweringBushBlock> floweringBlock) {
        super(properties);
        this.floweringBlock = floweringBlock;
    }

    public FlowerableBushBlock(Properties properties) {
        this(properties, null);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (floweringBlock.get().defaultBlockState().canSurvive(level, pos))
            level.setBlock(pos, floweringBlock.get().defaultBlockState(), 1);
    }

    @Override
    protected @NotNull MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
