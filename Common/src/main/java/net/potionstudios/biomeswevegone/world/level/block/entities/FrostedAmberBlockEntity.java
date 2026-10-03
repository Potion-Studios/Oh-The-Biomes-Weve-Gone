package net.potionstudios.biomeswevegone.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.custom.FrostedAmberBlock;
import org.jetbrains.annotations.NotNull;

public class FrostedAmberBlockEntity extends BlockEntity {
    private int emptyBottles = 0;
    private int fullBottles = 0;
    private int conversionTicks = 0;
    private static final int CONVERSION_TICKS = 20 * 25;
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

    public void removeFullBottles(int amount) {
        fullBottles -= amount;
    }

    public boolean isFull() {
        return emptyBottles + fullBottles >= 64;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FrostedAmberBlockEntity blockEntity) {
        if (state.getValue(FrostedAmberBlock.CONVERTING)) {
            blockEntity.conversionTicks++;
            if (blockEntity.conversionTicks >= CONVERSION_TICKS) {
                blockEntity.conversionTicks = 0;
                blockEntity.emptyBottles -= 4;
                blockEntity.fullBottles += 4;
                level.setBlockAndUpdate(pos, state.setValue(FrostedAmberBlock.CONVERTING, false));
            }
        } else if (blockEntity.getEmptyBottles() > 0 && blockEntity.getFullBottles() % 4 == 0 && !blockEntity.isFull()) {
            level.setBlockAndUpdate(pos, state.setValue(FrostedAmberBlock.CONVERTING, true));
        }
    }
}
