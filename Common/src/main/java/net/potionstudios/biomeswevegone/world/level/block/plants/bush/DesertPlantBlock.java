package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DesertPlantBlock extends BWGPlacementBushBlock {

	public DesertPlantBlock(Properties properties, VoxelShape shape, TagKey<Block> validGround) {
		super(properties, shape, validGround);
	}

    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity, @NotNull InsideBlockEffectApplier applier, boolean intersects) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.CAT && entity.getType() != EntityType.RABBIT) {
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
            if (level instanceof ServerLevel serverLevel) {
                if (Math.abs(entity.getX() - entity.xOld) >= (double) 0.003F || Math.abs(entity.getZ() - entity.zOld) >= (double) 0.003F)
                    entity.hurtServer(serverLevel, entity.damageSources().cactus(), 1.0F);
            }
        }
    }

    /**
     * Overrides the path type for mobs walking through the bush.getBlockPathType
     * @see net.neoforged.neoforge.common.extensions.IBlockExtension#getBlockPathType
     * @see net.minecraftforge.common.extensions.IForgeBlock#getBlockPathType
     */
    @Nullable
    PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.DAMAGING_IN_NEIGHBOR;
    }
}
