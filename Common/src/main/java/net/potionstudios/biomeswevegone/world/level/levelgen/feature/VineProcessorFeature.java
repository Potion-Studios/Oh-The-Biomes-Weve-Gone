package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VineProcessorFeature extends Feature<NoneFeatureConfiguration> {

    public static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public VineProcessorFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        ChunkAccess chunk = context.level().getChunk(context.origin());
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        ChunkPos pos = chunk.getPos();
        int minWorldX = pos.getMinBlockX();
        int minWorldZ = pos.getMinBlockZ();

        WorldGenLevel level = context.level();

        for (int sectionIdx = 0; sectionIdx < chunk.getHighestFilledSectionIndex(); sectionIdx++) {
            int sectionY = chunk.getSectionYFromSectionIndex(sectionIdx);
            int startY = SectionPos.sectionToBlockCoord(sectionY);

            LevelChunkSection section = chunk.getSection(sectionIdx);
            if (section.hasOnlyAir()) {
                continue;
            }

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int height = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                    int heightSection = SectionPos.blockToSectionCoord(height);

                    if (sectionY >= heightSection) {
                        for (int y = 0; y < 16; y++) {
                            int worldY = y + startY;
                            if (worldY > height) {
                                mutableBlockPos.set(minWorldX + x, worldY, minWorldZ + z);
                                BlockState blockState = chunk.getBlockState(mutableBlockPos);

                                if (blockState.isAir()) {
                                    if (context.random().nextDouble() < 0.05) {
                                        int length = -1;
                                        for (Direction direction : DIRECTIONS) {
                                            mutableBlockPos.set(minWorldX + x, worldY, minWorldZ + z).move(direction);
                                            if (level.getBlockState(mutableBlockPos).canOcclude()) {
                                                if (length == -1) {
                                                   length = context.random().nextInt(3, 10);
                                                }
                                                BlockState state = Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), true);
                                                chunk.setBlockState(mutableBlockPos.set(minWorldX + x, worldY, minWorldZ + z), state, false);

                                                for (int vineLength = 0; vineLength < length; vineLength++) {
                                                    if(chunk.getBlockState(mutableBlockPos.move(Direction.DOWN)).isAir()) {
                                                        chunk.setBlockState(mutableBlockPos, state, false);
                                                    } else {
                                                        break;
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
