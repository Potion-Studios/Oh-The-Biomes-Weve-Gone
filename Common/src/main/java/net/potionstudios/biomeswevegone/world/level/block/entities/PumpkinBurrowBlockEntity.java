package net.potionstudios.biomeswevegone.world.level.block.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;
import org.jetbrains.annotations.Nullable;

public class PumpkinBurrowBlockEntity extends BlockEntity {

    private Occupant stored = Occupant.EMPTY;
    public PumpkinBurrowBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntities.PUMPKIN_BURROW.get(), pos, blockState);
    }

    public void addOccupant(Entity occupant) {
        if (stored.equals(Occupant.EMPTY)) {
            occupant.stopRiding();
            occupant.ejectPassengers();
            stored = Occupant.of(occupant);
            occupant.discard();
            if (level != null)
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PumpkinBurrowBlock.OCCUPIED, true));
            super.setChanged();
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
            return new Occupant(CustomData.of(compoundTag));
        }

        @Nullable
        public Entity createEntity(Level level) {
            CompoundTag compoundTag = entityData.copyTag();
            return EntityType.loadEntityRecursive(compoundTag, level, entityx -> entityx);
        }
    }
}
