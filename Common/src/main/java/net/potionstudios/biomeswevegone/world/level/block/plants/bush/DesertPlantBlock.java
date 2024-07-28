package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DesertPlantBlock extends BWGPlacementBushBlock {

	public DesertPlantBlock(Properties properties, VoxelShape shape, TagKey<Block> validGround) {
		super(properties, shape, validGround);
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (entity instanceof LivingEntity && entity.getType() != EntityType.CAT && entity.getType() != EntityType.RABBIT) {
			entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			double d0 = Math.abs(entity.getX() - entity.xOld);
			double d1 = Math.abs(entity.getZ() - entity.zOld);
			if (d0 >= (double) 0.003F || d1 >= (double) 0.003F)
				entity.hurt(entity.damageSources().cactus(), 1.0F);
		}
	}
}
