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
import net.minecraft.server.level.ServerEntity;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
	protected @NotNull AABB calculateBoundingBox(@NotNull BlockPos pos, @NotNull Direction direction) {
		Vec3 vec3 = Vec3.atCenterOf(pos).relative(direction, -0.46875);
		Direction.Axis axis = direction.getAxis();
		double d = axis == Direction.Axis.X ? 0.0625 : 0.75;
		double e = axis == Direction.Axis.Y ? 0.0625 : 0.75;
		double g = axis == Direction.Axis.Z ? 0.0625 : 0.75;
		return AABB.ofSize(vec3, d, e, g);
	}

	@Override
	public void playPlacementSound() {
		playSound(SoundEvents.AZALEA_LEAVES_PLACE);
	}

	@Override
	public void dropItem(@Nullable Entity entity) {
		if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
			playSound(SoundEvents.AZALEA_LEAVES_BREAK);
			if (!(entity instanceof Player player && player.hasInfiniteMaterials())) {
				this.spawnAtLocation(getVariant().getItem());
			}
			gameEvent(GameEvent.BLOCK_CHANGE, entity);
		}
	}

	@Override
	public void setVariant(@NotNull Type type) {
		entityData.set(DATA_ID_TYPE, type.ordinal());
	}

	@Override
	public @NotNull Type getVariant() {
		return Type.byId(entityData.get(DATA_ID_TYPE));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
		builder.define(DATA_ID_TYPE, Type.DEFAULT.ordinal());
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Type", getVariant().getSerializedName());
		compound.putByte("Facing", (byte)direction.get3DDataValue());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Type", 8))
			setVariant(Type.byName(compound.getString("Type")));
		setDirection(Direction.from3DDataValue(compound.getByte("Facing")));
	}

	@Override
	public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entity) {
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
