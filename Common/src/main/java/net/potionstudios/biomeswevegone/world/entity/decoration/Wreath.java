package net.potionstudios.biomeswevegone.world.entity.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class Wreath extends HangingEntity {
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
		direction = facingDirection;
		setVariant(type);
	}

	@Override
	protected @NotNull AABB calculateBoundingBox(@NotNull BlockPos pos, @NotNull Direction direction) {
		double thickness = 0.0625;
		return switch (direction) {
			case NORTH -> new AABB(
					pos.getX(), pos.getY(), pos.getZ() + 1 - thickness,
					pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
			);
			case SOUTH -> new AABB(
					pos.getX(), pos.getY(), pos.getZ(),
					pos.getX() + 1, pos.getY() + 1, pos.getZ() + thickness
			);
			case WEST -> new AABB(
					pos.getX() + 1 - thickness, pos.getY(), pos.getZ(),
					pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
			);
			case EAST -> new AABB(
					pos.getX(), pos.getY(), pos.getZ(),
					pos.getX() + thickness, pos.getY() + 1, pos.getZ() + 1
			);
			case UP -> new AABB(
					pos.getX(), pos.getY(), pos.getZ(),
					pos.getX() + 1, pos.getY() + thickness, pos.getZ() + 1
			);
			case DOWN -> new AABB(
					pos.getX(), pos.getY() + 1 - thickness, pos.getZ(),
					pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
			);
		};
	}

	@Override
	public void playPlacementSound() {
		playSound(SoundEvents.AZALEA_LEAVES_PLACE);
	}

	@Override
	public void dropItem(@Nullable Entity entity) {
		playSound(SoundEvents.AZALEA_LEAVES_BREAK);
		//Drop the item
		gameEvent(GameEvent.BLOCK_CHANGE, entity);
	}

	public void setVariant(Type type) {
		entityData.set(DATA_ID_TYPE, type.ordinal());
	}

	public Type getVariant() {
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
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Type", 8))
			setVariant(Type.byName(compound.getString("Type")));
	}

	

	public enum Type implements StringRepresentable {
		HOLLY("holly", BWGItems.HOLLY_WREATH),
		MUSHROOM("mushroom", BWGItems.MUSHROOM_WREATH),
		ODDION("oddion", BWGItems.ODDION_WREATH),
		PETAL("petal", BWGItems.PETAL_WREATH),
		ROSY("rosy", BWGItems.ROSY_WREATH),
		WINTER_ROSY("winter_rosy", BWGItems.WINTER_ROSY_WREATH),
		DEFAULT("default", BWGItems.WREATH);

		private final String name;
		private final Supplier<HangingEntityItem> item;
		public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
		private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

		Type(String name, Supplier<HangingEntityItem> item) {
			this.name = name;
			this.item = item;
		}

		@Override
		public @NotNull String getSerializedName() {
			return name;
		}

		public HangingEntityItem getItem() {
			return item.get();
		}

		public static Type byId(int id) {
			return BY_ID.apply(id);
		}

		public static Type byName(String name) {
			return CODEC.byName(name, DEFAULT);
		}
	}
}
