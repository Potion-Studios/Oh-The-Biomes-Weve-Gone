package net.potionstudios.biomeswevegone.world.entity.bizzar;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.potionstudios.biomeswevegone.tags.BWGItemTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class Bizzar extends TamableAnimal implements NeutralMob, GeoAnimatable {
	private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

	private static final RawAnimation IDLE_SIT = RawAnimation.begin().thenLoop("idle_sit");
	private static final RawAnimation IDLE_STAND = RawAnimation.begin().thenLoop("idle_stand");
	private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");

	private static final EntityDataAccessor<Byte> DATA_DYE_ID = SynchedEntityData.defineId(Bizzar.class, EntityDataSerializers.BYTE);

	private static int createBizzarColor(DyeColor dyeColor) {
		if (dyeColor == DyeColor.WHITE) {
			return -1644826;
		} else {
			int i = dyeColor.getTextureDiffuseColor();
			return FastColor.ARGB32.color(255, Mth.floor((float) FastColor.ARGB32.red(i) * 0.75F), Mth.floor((float) FastColor.ARGB32.green(i) * 0.75F), Mth.floor((float) FastColor.ARGB32.blue(i) * 0.75F));
		}
	}

	public Bizzar(EntityType<? extends TamableAnimal> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 6;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0F, 10.0F, 2.0F));
		this.goalSelector.addGoal(4, new BizzarBlizzardGoal(this));
		this.goalSelector.addGoal(7, new BreedGoal(this, 1.0F));
		this.goalSelector.addGoal(8, new TemptGoal(this, 1.25D, this::isFood, false));

		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
//		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
//		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_DYE_ID, (byte) 0);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putByte("Color", (byte)this.getColor().getId());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setColor(DyeColor.byId(compound.getByte("Color")));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return TamableAnimal.createLivingAttributes().add(Attributes.FOLLOW_RANGE);
	}

	public DyeColor getColor() {
		return DyeColor.byId(this.entityData.get(DATA_DYE_ID) & 15);
	}

	public void setColor(DyeColor dyeColor) {
		byte b = this.entityData.get(DATA_DYE_ID);
		this.entityData.set(DATA_DYE_ID, (byte)(b & 240 | dyeColor.getId() & 15));
	}

	@Override
	public boolean isFood(@NotNull ItemStack stack) {
		return stack.is(BWGItemTags.BIZZAR_FOOD);
	}

	@Override
	public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.getItem() instanceof DyeItem dyeItem) {
			if (this.getColor() != dyeItem.getDyeColor() && this.isAlive() && ((this.isTame() && this.getOwner().is(player)) || !this.isTame())) {
				level().playSound(player, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
				if (!level().isClientSide()) {
					this.setColor(dyeItem.getDyeColor());
					itemStack.shrink(1);
				}

				return InteractionResult.sidedSuccess(level().isClientSide());
			}
		} else if (!isTame() && isFood(itemStack) && !this.isAngry()) {
			itemStack.consume(1, player);
			this.tryToTame(player);
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, hand);
	}

	private void tryToTame(Player player) {
		if (this.random.nextInt(3) == 0) {
			this.tame(player);
			this.navigation.stop();
			this.setTarget(null);
			this.setOrderedToSit(true);
			this.level().broadcastEntityEvent(this, (byte)7);
		} else {
			this.level().broadcastEntityEvent(this, (byte)6);
		}
	}

	@Override
	public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		return null;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return 0;
	}

	@Override
	public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {

	}

	@Override
	public @Nullable UUID getPersistentAngerTarget() {
		return null;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {

	}

	@Override
	public void startPersistentAngerTimer() {

	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return animatableInstanceCache;
	}

	private <E extends GeoAnimatable> PlayState predicate(@NotNull AnimationState<E> event) {
		event.getController().transitionLength(0);
		if (this.isInSittingPose())
			return event.setAndContinue(IDLE_SIT);

		if (event.isMoving())
			return event.setAndContinue(WALK);

		return event.setAndContinue(IDLE_STAND);
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private static class BizzarBlizzardGoal extends Goal {
		private final Bizzar bizzar;
		private int activeTicks = 0;
		private int cooldownTicks = 0;

        private BizzarBlizzardGoal(Bizzar bizzar) {
            this.bizzar = bizzar;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
		public boolean canUse() {
			if (this.cooldownTicks > 0) {
				this.cooldownTicks--;
				return false;
			}

			if (bizzar.isOrderedToSit())
				return false;

			return !getNearbyHostiles().isEmpty();
		}

		@Override
		public boolean canContinueToUse() {
			return this.activeTicks < 300;
		}

		@Override
		public void tick() {
			activeTicks++;
		}

		@Override
		public void stop() {
			this.activeTicks = 0;
			this.cooldownTicks = 500;
		}

		private List<Monster> getNearbyHostiles() {
			AABB searchBox = this.bizzar.getBoundingBox().inflate(9.0D);
			return this.bizzar.level().getEntitiesOfClass(Monster.class, searchBox);
		}
	}
}
