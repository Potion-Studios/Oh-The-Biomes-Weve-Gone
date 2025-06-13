package net.potionstudios.biomeswevegone.world.entity.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.item.custom.WreathItem;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class Wreath extends HangingEntity implements VariantHolder<Wreath.Type> {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Wreath.class, EntityDataSerializers.INT);

    public Wreath(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
        setVariant(Type.DEFAULT);
    }

    public Wreath(Level level, BlockPos pos, Direction facingDirection, Type type) {
        this(BWGEntityType.WREATH.get(), level, pos, facingDirection, type);
    }

    public Wreath(EntityType<? extends HangingEntity> entityType, Level level, BlockPos pos, Direction facingDirection, Type type) {
        super(entityType, level, pos);
        setVariant(type);
        setDirection(facingDirection);
    }

    @Override
    protected void setDirection(@NotNull Direction facingDirection) {
        Validate.notNull(facingDirection);
        this.direction = facingDirection;
        if (facingDirection.getAxis().isHorizontal()) {
            this.setXRot(0.0F);
            this.setYRot(this.direction.get2DDataValue() * 90);
        } else {
            this.setXRot(-90 * facingDirection.getAxisDirection().getStep());
            this.setYRot(0.0F);
        }

        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
        this.recalculateBoundingBox();
    }

    @Override
    protected void recalculateBoundingBox() {
        if (this.direction != null) {
            double e = this.pos.getX() + 0.5 - this.direction.getStepX() * 0.46875;
            double f = this.pos.getY() + 0.5 - this.direction.getStepY() * 0.46875;
            double g = this.pos.getZ() + 0.5 - this.direction.getStepZ() * 0.46875;
            this.setPosRaw(e, f, g);
            double h = this.getWidth();
            double i = this.getHeight();
            double j = this.getWidth();
            Direction.Axis axis = this.direction.getAxis();
            switch (axis) {
                case X:
                    h = 1.0;
                    break;
                case Y:
                    i = 1.0;
                    break;
                case Z:
                    j = 1.0;
            }

            h /= 32.0;
            i /= 32.0;
            j /= 32.0;
            this.setBoundingBox(new AABB(e - h, f - i, g - j, e + h, f + i, g + j));
        }
    }

    @Override
    public boolean survives() {
        if (!this.level().noCollision(this)) {
            return false;
        } else {
            BlockState blockState = this.level().getBlockState(this.pos.relative(this.direction.getOpposite()));
            return (blockState.isSolid() || this.direction.getAxis().isHorizontal() && DiodeBlock.isDiode(blockState)) && this.level().getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty();
        }
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public void dropItem(@Nullable Entity brokenEntity) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            playSound(SoundEvents.AZALEA_LEAVES_BREAK);
            if (!(brokenEntity instanceof Player player && player.getAbilities().instabuild)) {
                this.spawnAtLocation(getVariant().getItem());
            }
            gameEvent(GameEvent.BLOCK_CHANGE, brokenEntity);
        }
    }

    @Override
    public void playPlacementSound() {
        playSound(SoundEvents.AZALEA_LEAVES_PLACE);
    }

    @Override
    public void setVariant(@NotNull Type variant) {
        entityData.set(DATA_ID_TYPE, variant.ordinal());
    }

    @Override
    public @NotNull Type getVariant() {
        return Type.byId(entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ID_TYPE, Type.DEFAULT.ordinal());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", getVariant().getSerializedName());
        compound.putByte("Facing", (byte) direction.get3DDataValue());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Type", 8))
            setVariant(Type.byName(compound.getString("Type")));
        setDirection(Direction.from3DDataValue(compound.getByte("Facing")));
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.setDirection(Direction.from3DDataValue(packet.getData()));
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return getVariant().getItem().getDefaultInstance();
    }

    @Override
    protected @NotNull Component getTypeName() {
        return Component.translatable(getVariant().getItem().getDescriptionId());
    }

    public enum Type implements StringRepresentable {
        HOLLY("holly", () -> BWGItems.HOLLY_WREATH),
        MUSHROOM("mushroom", () -> BWGItems.MUSHROOM_WREATH),
        ODDION("oddion", () -> BWGItems.ODDION_WREATH),
        PETAL("petal", () -> BWGItems.PETAL_WREATH),
        ROSY("rosy", () -> BWGItems.ROSY_WREATH),
        WINTER_ROSY("winter_rosy", () -> BWGItems.WINTER_ROSY_WREATH),
        DEFAULT("default", () -> BWGItems.WREATH);

        private final String name;
        private final Supplier<Supplier<WreathItem>> item;
        public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        Type(String name, Supplier<Supplier<WreathItem>> item) {
            this.name = name;
            this.item = item;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }

        public WreathItem getItem() {
            return item.get().get();
        }

        public static Type byId(int id) {
            return BY_ID.apply(id);
        }

        public static Type byName(String name) {
            return CODEC.byName(name, DEFAULT);
        }
    }
}
