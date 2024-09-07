package net.potionstudios.biomeswevegone.world.entity.boats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import org.jetbrains.annotations.NotNull;

/**
 * BWG Chest Boat Entity
 * @see ChestBoat
 * @author Joseph T. McQuigg
 */
public class BWGChestBoatEntity extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(BWGChestBoatEntity.class, EntityDataSerializers.INT);
    public BWGChestBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public BWGChestBoatEntity(Level level, double x, double y, double z) {
        this(BWGEntities.BWG_CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public @NotNull Item getDropItem() {
        return getModVariant().getChestBoatItem().get();
    }

    public void setVariant(BWGBoatEntity.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, BWGBoatEntity.Type.ASPEN.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8))
            this.setVariant(BWGBoatEntity.Type.byName(pCompound.getString("Type")));
    }

    public BWGBoatEntity.Type getModVariant() {
        return BWGBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }
}
