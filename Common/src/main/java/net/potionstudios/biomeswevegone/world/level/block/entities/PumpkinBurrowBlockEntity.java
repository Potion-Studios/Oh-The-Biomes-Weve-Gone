package net.potionstudios.biomeswevegone.world.level.block.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.component.BWGDataComponents;
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
            "DeathTime",
            "FallDistance",
            "FallFlying",
            "Fire",
            "HandDropChances",
            "Hiding",
            "HurtByTimestamp",
            "HurtTime",
            "LeftHanded",
            "Motion",
            "OnGround",
            "PortalCooldown",
            "Pos",
            "Rotation",
            "UUID"
    );
    private Occupant stored = Occupant.EMPTY;
    public PumpkinBurrowBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntityType.PUMPKIN_BURROW.get(), pos, blockState);
    }

    public void addOccupant(LivingEntity occupant) {
        if (isEmpty()) {
            occupant.stopRiding();
            occupant.ejectPassengers();
            occupant.setSleepingPos(getBlockPos());
            stored = Occupant.of(occupant);
            occupant.discard();
            if (getLevel() != null)
                getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PumpkinBurrowBlock.OCCUPIED, true));
            super.setChanged();
        }
    }

    public boolean isEmpty() {
        return stored.equals(Occupant.EMPTY);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("occupant")) {
            Occupant.CODEC
                    .parse(NbtOps.INSTANCE, tag.getCompound("occupant"))
                    .resultOrPartial(string -> BiomesWeveGone.LOGGER.error("Failed to parse occupants: '{}'", string))
                    .ifPresent(occupant -> stored = occupant);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("occupant", Occupant.CODEC.encodeStart(NbtOps.INSTANCE, stored).getOrThrow());
    }

    @Override
    protected void applyImplicitComponents(@NotNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        stored = componentInput.getOrDefault(BWGDataComponents.PUMPKIN_WARDEN.get(), Occupant.EMPTY);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
        super.collectImplicitComponents(components);
        components.set(BWGDataComponents.PUMPKIN_WARDEN.get(), stored);
    }

    public void emptyOccupant(Level level) {
        if (!isEmpty()) {
            Entity entity = stored.createEntity(level);
            if (entity instanceof PumpkinWarden pumpkinWarden) {
                pumpkinWarden.setPos(getBlockPos().getX() + 0.5, getBlockPos().getY(), getBlockPos().getZ() + 0.5);
                pumpkinWarden.stopSleeping();
                level.addFreshEntity(pumpkinWarden);
                stored = Occupant.EMPTY;
            }
        }
    }

    public record Occupant(CustomData entityData) {
        public static final Occupant EMPTY = new Occupant(CustomData.EMPTY);

        public static final Codec<Occupant> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        CustomData.CODEC.optionalFieldOf("entity_data", CustomData.EMPTY).forGetter(Occupant::entityData)
        )
        .apply(instance, Occupant::new));

        public static final StreamCodec<ByteBuf, Occupant> STREAM_CODEC = StreamCodec.composite(
                CustomData.STREAM_CODEC,
                Occupant::entityData,
                Occupant::new
        );

        public static Occupant of(Entity entity) {
            CompoundTag compoundTag = new CompoundTag();
            entity.save(compoundTag);
            IGNORED_TAGS.forEach(compoundTag::remove);
            return new Occupant(CustomData.of(compoundTag));
        }

        @Nullable
        public Entity createEntity(Level level) {
            CompoundTag compoundTag = entityData.copyTag();
            return EntityType.loadEntityRecursive(compoundTag, level, entityx -> entityx);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PumpkinBurrowBlockEntity blockEntity) {
        if (!blockEntity.isEmpty() && level.isDay() && level.getRandom().nextBoolean()) {
                Entity entity = blockEntity.stored.createEntity(level);
                if (entity instanceof PumpkinWarden pumpkinWarden) {
                    Direction direction = state.getValue(PumpkinBurrowBlock.FACING);
                    BlockPos blockPos = pos.relative(direction);
                    if (level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty()) {
                        pumpkinWarden.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
                        pumpkinWarden.stopSleeping();
                        pumpkinWarden.getBrain().setMemory(MemoryModuleType.HOME, new GlobalPos(level.dimension(), pos));
                        level.addFreshEntity(pumpkinWarden);
                        blockEntity.stored = Occupant.EMPTY;
                        level.setBlockAndUpdate(pos, state.setValue(PumpkinBurrowBlock.OCCUPIED, false));
                    }
                }
        }
    }
}
