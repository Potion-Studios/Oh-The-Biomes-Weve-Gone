package net.potionstudios.biomeswevegone.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGOverworldVegationPlacedFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Optional;

@Mixin(GrassBlock.class)
public abstract class PrairieGrassMixin extends SpreadingSnowyDirtBlock implements BonemealableBlock {

    protected PrairieGrassMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author Joseph T. McQuigg
     * @reason Used to generate Prairie grass when bonemeal is used on a grassblock in the Prairie biome.
     */
    @Overwrite
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.above();
        BlockState blockState = Blocks.GRASS.defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess()
                .registryOrThrow(Registries.PLACED_FEATURE)
                .getHolder(VegetationPlacements.GRASS_BONEMEAL);

        if (level.getBiome(pos).is(BWGBiomes.PRAIRIE)) {
            blockState = BWGBlocks.PRAIRIE_GRASS.get().defaultBlockState();
            optional = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(BWGOverworldVegationPlacedFeatures.PRAIRIE_GRASS_BONEMEAL);
        }

        label49:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos2 = blockPos;

            for(int j = 0; j < i / 16; ++j) {
                blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockPos2.below()).is(this) || level.getBlockState(blockPos2).isCollisionShapeFullBlock(level, blockPos2))
                    continue label49;
            }

            BlockState blockState2 = level.getBlockState(blockPos2);
            if (blockState2.is(blockState.getBlock()) && random.nextInt(10) == 0)
                ((BonemealableBlock)blockState.getBlock()).performBonemeal(level, random, blockPos2, blockState2);


            if (blockState2.isAir()) {
                Holder<PlacedFeature> holder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = level.getBiome(blockPos2).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) continue;

                    holder = ((RandomPatchConfiguration) list.get(0).config()).feature();
                } else {
                    if (!optional.isPresent()) continue;

                    holder = optional.get();
                }

                holder.value().place(level, level.getChunkSource().getGenerator(), random, blockPos2);
            }
        }
    }

}
