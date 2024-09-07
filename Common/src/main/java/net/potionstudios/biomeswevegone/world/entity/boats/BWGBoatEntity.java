package net.potionstudios.biomeswevegone.world.entity.boats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * BWG Boat Entity
 * @see Boat
 * @author Joseph T. McQuigg
 */
public class BWGBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(BWGBoatEntity.class, EntityDataSerializers.INT);
    public BWGBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public BWGBoatEntity(Level level, double x, double y, double z) {
        this(BWGEntities.BWG_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public @NotNull Item getDropItem() {
        return getModVariant().getBoatItem().get();
    }

    public void setVariant(Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, Type.ASPEN.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8))
            this.setVariant(Type.byName(pCompound.getString("Type")));
    }

    public enum Type implements StringRepresentable {
        ASPEN(() -> BWGWood.ASPEN, "aspen"),
        BAOBAB(() -> BWGWood.BAOBAB, "baobab"),
        CHERRY(() -> BWGWood.BLUE_ENCHANTED, "blue_enchanted"),
        CIKA(() -> BWGWood.CIKA, "cika"),
        CYPRESS(() -> BWGWood.CYPRESS, "cypress"),
        EBONY(() -> BWGWood.EBONY, "ebony"),
        FIR(() -> BWGWood.FIR, "fir"),
        FLORUS(() -> BWGWood.FLORUS, "florus"),
        GREEN_ENCHANTED(() -> BWGWood.GREEN_ENCHANTED, "green_enchanted"),
        HOLLY(() -> BWGWood.HOLLY, "holly"),
        IRONWOOD(() -> BWGWood.IRONWOOD, "ironwood"),
        JACARANDA(() -> BWGWood.JACARANDA, "jacaranda"),
        MAHOGANY(() -> BWGWood.MAHOGANY, "mahogany"),
        MAPLE(() -> BWGWood.MAPLE, "maple"),
        PALM(() -> BWGWood.PALM, "palm"),
        PINE(() -> BWGWood.PINE, "pine"),
        RAINBOW_EUCALYPTUS(() -> BWGWood.RAINBOW_EUCALYPTUS, "rainbow_eucalyptus"),
        REDWOOD(() -> BWGWood.REDWOOD, "redwood"),
        SAKURA(() -> BWGWood.SAKURA, "sakura"),
        SKYRIS(() -> BWGWood.SKYRIS, "skyris"),
        WHITE_MANGROVE(() -> BWGWood.WHITE_MANGROVE, "white_mangrove"),
        WILLOW(() -> BWGWood.WILLOW, "willow"),
        WITCH_HAZEL(() -> BWGWood.WITCH_HAZEL, "witch_hazel"),
        ZELKOVA(() -> BWGWood.ZELKOVA, "zelkova");

        private final Supplier<BWGWoodSet> woodSet;
        private final String name;
        public static final StringRepresentable.EnumCodec<BWGBoatEntity.Type> CODEC = StringRepresentable.fromEnum(BWGBoatEntity.Type::values);
        private static final IntFunction<BWGBoatEntity.Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        Type(Supplier<BWGWoodSet> woodSet, String name) {
            this.woodSet = woodSet;
            this.name = name;
        }

        public @NotNull String getSerializedName() {
            return this.getName();
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.woodSet.get().name();
        }

        public Supplier<Item> getBoatItem() {
            return this.woodSet.get().boatItem();
        }

        public Supplier<Item> getChestBoatItem() {
            return this.woodSet.get().chestBoatItem();
        }

        public static BWGBoatEntity.Type byId(int id) {
            return BY_ID.apply(id);
        }

        public static BWGBoatEntity.Type byName(String name) {
            return CODEC.byName(name, ASPEN);
        }
    }
}
