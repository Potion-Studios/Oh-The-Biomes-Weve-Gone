package net.potionstudios.biomeswevegone.world.entity.pumpkinwarden;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import net.potionstudios.biomeswevegone.world.entity.ai.behavior.PumpkinWardenGoalPackages;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.ai.sensing.BWGSensorType;
import net.potionstudios.biomeswevegone.world.entity.schedule.BWGSchedule;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.function.IntFunction;

/**
 * The Pumpkin Warden Entity
 * @see PathfinderMob
 * @see GeoEntity
 * @author JT122406
 */
public class PumpkinWarden extends PathfinderMob implements GeoEntity, VariantHolder<PumpkinWarden.Variant> {

    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private BlockPos jukebox;
    private boolean party;
    private static final EntityDataAccessor<Boolean> HIDING = SynchedEntityData.defineId(PumpkinWarden.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(PumpkinWarden.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<BlockState>> DATA_CARRY_STATE = SynchedEntityData.defineId(PumpkinWarden.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(PumpkinWarden.class, EntityDataSerializers.INT);

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
        MemoryModuleType.NEAREST_LIVING_ENTITIES,
        MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
        MemoryModuleType.INTERACTION_TARGET,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.VISIBLE_VILLAGER_BABIES,
        MemoryModuleType.PATH,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.HOME,
        BWGMemoryModuleType.PUMPKIN_STEMS.get(),
        BWGMemoryModuleType.VISIBLE_PUMPKIN_WARDENS.get()
    );

    private static final ImmutableList<SensorType<? extends Sensor<? super PumpkinWarden>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.VILLAGER_BABIES,
            BWGSensorType.NEAREST_STEM_BLOCK.get(),
            BWGSensorType.NEAREST_VISIBLE_PUMPKIN_WARDENS.get(),
            BWGSensorType.NEAREST_PUMPKIN_BURROW.get()
    );

    public PumpkinWarden(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        setPathfindingMalus(PathType.DANGER_FIRE, 16.0F);
        setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
        getNavigation().setCanFloat(true);
    }

    @Override
    public @NotNull Brain<PumpkinWarden> getBrain() {
        return (Brain<PumpkinWarden>) super.getBrain();
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
        Brain<PumpkinWarden> brain = (Brain<PumpkinWarden>) super.makeBrain(dynamic);
        registerBrainGoals(brain);
        return brain;
    }

    private void refreshBrain(ServerLevel serverLevel) {
        Brain<PumpkinWarden> brain = this.getBrain();
        brain.stopAll(serverLevel, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }

    private void registerBrainGoals(Brain<PumpkinWarden> brain) {
        brain.setSchedule(BWGSchedule.PUMPKIN_WARDEN.get());
        brain.addActivity(Activity.PLAY, PumpkinWardenGoalPackages.getPlayPackage());
        brain.addActivity(Activity.WORK, PumpkinWardenGoalPackages.getWorkPackage());
        brain.addActivity(Activity.REST, PumpkinWardenGoalPackages.getRestPackage());
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.PLAY);
        brain.setActiveActivityIfPossible(Activity.PLAY);
        brain.updateActivityFromSchedule(level().getDayTime(), level().getGameTime());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CARRY_STATE, Optional.empty());
        builder.define(HIDING, false);
        builder.define(TIMER, 0);
        builder.define(DATA_VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant().getId());
        BlockState blockState = null;
        if (compound.contains("carriedBlockState", 10)) {
            blockState = NbtUtils.readBlockState(level().holderLookup(Registries.BLOCK), compound.getCompound("carriedBlockState"));
            if (blockState.isAir())
                blockState = null;
        }

        this.setCarriedBlock(blockState);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(Variant.byId(compound.getInt("Variant")));
        BlockState blockState = this.getCarriedBlock();
        if (blockState != null) compound.put("carriedBlockState", NbtUtils.writeBlockState(blockState));
        if (level() instanceof ServerLevel serverLevel)
            refreshBrain(serverLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FOLLOW_RANGE, 48);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void checkDespawn() {}

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (isHiding()) return InteractionResult.FAIL;
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.is(BWGBlocks.ROSE.getBlock().asItem())){
            if (player.level().isClientSide()) {
                level().addParticle(ParticleTypes.HEART, this.getX(), this.getY() + 1, this.getZ(), 1, 1, 1);
                level().playSound(player, player.blockPosition(), SoundEvents.VILLAGER_AMBIENT, SoundSource.NEUTRAL, 1, getVoicePitch());
            }
            itemInHand.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    private static final RawAnimation HIDE_START = RawAnimation.begin().thenPlay("animation.pumpkinwarden.hidestart");
    private static final RawAnimation HIDE = RawAnimation.begin().thenLoop("animation.pumpkinwarden.hide");
    private static final RawAnimation HIDE_END = RawAnimation.begin().thenPlay("animation.pumpkinwarden.hideend");
    private static final RawAnimation HOLDING_WALKING = RawAnimation.begin().thenPlay("animation.pumpkinwarden.holding_walking");
    private static final RawAnimation HOLDING_IDLE = RawAnimation.begin().thenPlay("animation.pumpkinwarden.holding_idle");
    private static final RawAnimation WALKING = RawAnimation.begin().thenPlay("animation.pumpkinwarden.walking");
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("animation.pumpkinwarden.idle");
    private static final RawAnimation WAVE = RawAnimation.begin().thenPlay("animation.pumpkinwarden.wave");


    private <E extends GeoAnimatable> PlayState predicate(@NotNull AnimationState<E> event) {
        event.getController().transitionLength(0);
        if (this.isHiding()) {
            if (this.getTimer() < 10) {
                return event.setAndContinue(HIDE_START);
            } else if ((this.getTimer() > 10 && this.getTimer() < 180) || !this.level().isDay() && this.getTimer() > 10) {
                return event.setAndContinue(HIDE);
            } else if (this.getTimer() > 180) {
                if (this.level().getBrightness(LightLayer.SKY, this.getOnPos()) > 2)
                    return event.setAndContinue(HIDE_END);
                return event.setAndContinue(HIDE);
            }
        }
        if (this.getCarriedBlock() != null) {
            if (event.isMoving())
                return event.setAndContinue(HOLDING_WALKING);
            return event.setAndContinue(HOLDING_IDLE);
        } else if (event.isMoving() && this.getCarriedBlock() == null) {
            return event.setAndContinue(WALKING);
        } else if (this.party) {
            return event.setAndContinue(WAVE);
        }
        return event.setAndContinue(IDLE);
    }

    @Override
    public void setRecordPlayingNearby(@NotNull BlockPos blockPos, boolean partying) {
        this.jukebox = blockPos;
        this.party = partying;
    }

    @Override
    public void aiStep() {
        if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 10D) || !this.level().getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
            this.party = false;
            this.jukebox = null;
        }
        super.aiStep();
    }

    @Override
    protected void customServerAiStep() {
        level().getProfiler().push("pumpkinwardenBrain");
        getBrain().tick((ServerLevel) level(), this);
        level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected void updateControlFlags() {
        goalSelector.setControlFlag(Goal.Flag.MOVE, canMove());
        goalSelector.setControlFlag(Goal.Flag.JUMP, canMove());
        goalSelector.setControlFlag(Goal.Flag.LOOK, !isHiding());
        goalSelector.setControlFlag(Goal.Flag.TARGET, !isHiding());
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setVariant(Variant.getSpawnVariant(level.getRandom()));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.VILLAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return (getRandom().nextFloat() - getRandom().nextFloat()) * 0.2F + 1.5F;
    }

    public boolean canMove() {
        return !isHiding() && !party;
    }

    public boolean isHiding() {
        return entityData.get(HIDING);
    }

    public void setHiding(boolean flag) {
        entityData.set(HIDING, flag);
    }

    public int getTimer() {
        return entityData.get(TIMER);
    }

    public void setTimer(int flag) {
        entityData.set(TIMER, flag);
    }

    public void setCarriedBlock(@Nullable BlockState state) {
        setItemInHand(InteractionHand.MAIN_HAND, state == null ? ItemStack.EMPTY : new ItemStack(state.getBlock().asItem()));
        this.entityData.set(DATA_CARRY_STATE, Optional.ofNullable(state));
    }

    @Nullable
    public BlockState getCarriedBlock() {
        return this.entityData.get(DATA_CARRY_STATE).orElse(null);
    }

    @Override
    public void setVariant(@NotNull Variant variant) {
        this.entityData.set(DATA_VARIANT, variant.getId());
    }

    @Override
    public @NotNull Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_VARIANT));
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (this.isHiding()) return this.getType().getDimensions().scale(1F, 0.5F);
        else return super.getDimensions(pose);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> dataAccessor) {
        super.onSyncedDataUpdated(dataAccessor);
        if (HIDING.equals(dataAccessor))
            refreshDimensions();
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    public enum Variant implements StringRepresentable {
        DEFAULT(0, "default"),
        PALE(1, "pale"),
        CHEERY(2, "cheery"),
        FROWNY(3, "frowny"),
        SILLY(4, "silly");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        private final String name;
        private final int id;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public static Variant byId(int id) {
            return BY_ID.apply(id);
        }

        private static Variant getSpawnVariant(@NotNull RandomSource random) {
            int i = random.nextInt(100);
            if (i < 25) return DEFAULT;
            else if (i < 50) return CHEERY;
            else if (1 < 75) return SILLY;
            else if (i < 95) return FROWNY;
            else return PALE;
        }
    }

    public static boolean villagerToPumpkinWarden(Entity entity, ItemStack stack, Level level) {
        if (entity instanceof Villager villager && villager.isBaby() && villager.hasEffect(MobEffects.WEAKNESS))
            if (stack.is(Items.CARVED_PUMPKIN) || stack.is(BWGBlocks.CARVED_PALE_PUMPKIN.get().asItem()))
                if (level instanceof ServerLevel serverLevel) {
                    PumpkinWarden warden = BWGEntities.PUMPKIN_WARDEN.get().create(serverLevel);
                    warden.setPos(villager.position());
                    if (stack.is(BWGBlocks.CARVED_PALE_PUMPKIN.get().asItem()))
                        warden.setVariant(PumpkinWarden.Variant.PALE);
                    serverLevel.addFreshEntity(warden);
                    serverLevel.playSound(null, villager.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.NEUTRAL, 1, 1);
                    villager.remove(Entity.RemovalReason.DISCARDED);
                    stack.shrink(1);
                    return true;
                }
        return false;
    }
}
