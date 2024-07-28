package net.potionstudios.biomeswevegone.compat.vanilla.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.potionstudios.biomeswevegone.world.entity.boats.BWGBoatEntity;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import org.jetbrains.annotations.NotNull;

public class BWGDispenseItemBehavior {
	public static void registerDispenseItemBehavior() {
		for (BWGBoatEntity.Type type : BWGBoatEntity.Type.values()) {
			DispenserBlock.registerBehavior(type.getBoatItem().get(), new BWGBoatDispenseItemBehavior(type));
			DispenserBlock.registerBehavior(type.getChestBoatItem().get(), new BWGBoatDispenseItemBehavior(type, true));
		}

		DispenserBlock.registerBehavior(BWGItems.MAN_O_WAR_BUCKET.get(), new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

			@Override
			public @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
				DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem) stack.getItem();
				BlockPos blockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				Level level = source.getLevel();
				if (dispensibleContainerItem.emptyContents(null, level, blockPos, null)) {
					dispensibleContainerItem.checkExtraContent(null, level, stack, blockPos);
					return new ItemStack(Items.BUCKET);
				} else return this.defaultDispenseItemBehavior.dispense(source, stack);
			}
		});
	}
}
