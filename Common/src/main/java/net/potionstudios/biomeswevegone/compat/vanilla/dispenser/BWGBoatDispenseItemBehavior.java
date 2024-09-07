package net.potionstudios.biomeswevegone.compat.vanilla.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import net.potionstudios.biomeswevegone.world.entity.boats.BWGBoatEntity;
import net.potionstudios.biomeswevegone.world.entity.boats.BWGChestBoatEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Dispense Item Behavior for BWG Boats.
 * @see net.minecraft.core.dispenser.BoatDispenseItemBehavior
 * @author Joseph T. McQuigg
 */
public class BWGBoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
	private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
	private final BWGBoatEntity.Type type;
	private final boolean isChestBoat;

	public BWGBoatDispenseItemBehavior(BWGBoatEntity.Type type) {
		this(type, false);
	}

	public BWGBoatDispenseItemBehavior(BWGBoatEntity.Type type, boolean isChestBoat) {
		this.type = type;
		this.isChestBoat = isChestBoat;
	}

	@Override
	public @NotNull ItemStack execute(BlockSource blockSource, @NotNull ItemStack stack) {
		Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
		Level level = blockSource.level();
		Vec3 vec3 = blockSource.center();
		double d = 0.5625 + (double) BWGEntities.BWG_BOAT.get().getWidth() / 2.0;
		double e = vec3.x() + (double)direction.getStepX() * d;
		double f = vec3.y() + (double)((float)direction.getStepY() * 1.125F);
		double g = vec3.z() + (double)direction.getStepZ() * d;
		BlockPos blockPos = blockSource.pos().relative(direction);
		double h;
		if (level.getFluidState(blockPos).is(FluidTags.WATER))
			h = 1.0;
		else {
			if (!level.getBlockState(blockPos).isAir() || !level.getFluidState(blockPos.below()).is(FluidTags.WATER))
				return this.defaultDispenseItemBehavior.dispense(blockSource, stack);

			h = 0.0;
		}

		if (this.isChestBoat) {
			BWGChestBoatEntity entity = new BWGChestBoatEntity(level, e, f + h, g);
			entity.setVariant(this.type);
			entity.setYRot(direction.toYRot());
			level.addFreshEntity(entity);
		} else {
			BWGBoatEntity entity = new BWGBoatEntity(level, e, f + h, g);
			entity.setVariant(this.type);
			entity.setYRot(direction.toYRot());
			level.addFreshEntity(entity);
		}
		stack.shrink(1);
		return stack;
	}
}
