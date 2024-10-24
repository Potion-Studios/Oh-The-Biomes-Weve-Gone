package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.pumpkin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class EquipableCarvedPalePumpkinBlock extends PaleCarvedPumpkinBlock implements Equipable {
    public EquipableCarvedPalePumpkinBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN));
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
