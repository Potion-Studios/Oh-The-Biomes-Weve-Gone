package net.potionstudios.biomeswevegone.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FrostedAmberBlockEntity extends BlockEntity {
    private int emptyBottles = 0;
    private int fullBottles = 0;
    private int conversionTicks = 0;
    public FrostedAmberBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntityType.FROSTED_AMBER.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("emptyBottles", emptyBottles);
        tag.putInt("fullBottles", fullBottles);
        tag.putInt("conversionTicks", conversionTicks);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        emptyBottles = tag.getInt("emptyBottles");
        fullBottles = tag.getInt("fullBottles");
        conversionTicks = tag.getInt("conversionTicks");
    }

    public int getEmptyBottles() {
        return emptyBottles;
    }

    public int getFullBottles() {
        return fullBottles;
    }

    public void addBottle() {
        emptyBottles++;
    }

    public boolean isFull() {
        return emptyBottles + fullBottles >= 64;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FrostedAmberBlockEntity blockEntity) {

    }
}
