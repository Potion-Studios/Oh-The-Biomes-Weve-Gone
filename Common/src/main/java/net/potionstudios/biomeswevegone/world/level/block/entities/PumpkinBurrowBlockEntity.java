package net.potionstudios.biomeswevegone.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PumpkinBurrowBlockEntity extends BlockEntity {
    private static final List<String> IGNORED_TAGS = List.of(
            "Air",
            "ArmorDropChances",
            "ArmorItems",
            "CanPickUpLoot",
            "FallDistance",
            "FallFlying",
            "Fire",
            "HandDropChances",
            "Hiding",
            "HurtByTimestamp",
            "HurtTime",
            "LeftHanded",
            "OnGround",
            "Pos",
            "Rotation"
    );
    private Occupant stored = Occupant.EMPTY;
    public PumpkinBurrowBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntityType.PUMPKIN_BURROW.get(), pos, blockState);
    }

    public void addOccupant(LivingEntity occupant) {
        if (isEmpty()) {
            occupant.stopRiding();
            occupant.ejectPassengers();
            stored = Occupant.of(occupant);
            occupant.setSleepingPos(getBlockPos());
            occupant.discard();
            if (getLevel() != null)
                getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PumpkinBurrowBlock.OCCUPIED, true));
            super.setChanged();
        }
    }

    public boolean isEmpty() {
        return stored.equals(Occupant.EMPTY);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PumpkinBurrowBlockEntity blockEntity) {
        if (!blockEntity.isEmpty() && level.isDay()) {
                Entity entity = blockEntity.stored.createEntity(level);
                if (entity instanceof PumpkinWarden pumpkinWarden) {
                    Direction direction = state.getValue(PumpkinBurrowBlock.FACING);
                    BlockPos blockPos = pos.relative(direction);
                    if (level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty()) {
                        pumpkinWarden.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
                        pumpkinWarden.clearSleepingPos();
                        level.addFreshEntity(pumpkinWarden);
                        blockEntity.stored = Occupant.EMPTY;
                        level.setBlockAndUpdate(pos, state.setValue(PumpkinBurrowBlock.OCCUPIED, false));
                    }
                }
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("occupant"))
            stored = new Occupant(tag.getCompound("occupant"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("occupant", stored.entityData);
    }

    static void removeIgnoredTags(CompoundTag tag) {
        IGNORED_TAGS.forEach(tag::remove);
    }

    record Occupant(CompoundTag entityData) {

        public static final Occupant EMPTY = new Occupant(new CompoundTag());

        Occupant {
            removeIgnoredTags(entityData);
        }

        public static Occupant of(Entity entity) {
            CompoundTag compoundTag = new CompoundTag();
            entity.save(compoundTag);
            removeIgnoredTags(compoundTag);
            return new Occupant(compoundTag);
        }

        @Nullable
        public Entity createEntity(Level level) {
            CompoundTag compoundTag = entityData.copy();
            return EntityType.loadEntityRecursive(compoundTag, level, entityx -> entityx);
        }
    }
}
