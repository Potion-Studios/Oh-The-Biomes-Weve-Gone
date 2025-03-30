package net.potionstudios.biomeswevegone.world.level.block.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PumpkinBurrowBlockEntity extends BlockEntity {

    public PumpkinBurrowBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntities.PUMPKIN_BURROW.get(), pos, blockState);
    }

    public record Occupant(CustomData entityData, int ticksInBurrow, int minTicksInBurrow) {
        public static final Occupant EMPTY = new Occupant(CustomData.EMPTY, 0, 0);

        public static final Codec<Occupant> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        CustomData.CODEC.optionalFieldOf("entity_data", CustomData.EMPTY).forGetter(Occupant::entityData),
                        Codec.INT.fieldOf("ticks_in_burrow").forGetter(Occupant::ticksInBurrow),
                        Codec.INT.fieldOf("min_ticks_in_burrow").forGetter(Occupant::minTicksInBurrow)
        )
        .apply(instance, Occupant::new));

        public static final StreamCodec<ByteBuf, Occupant> STREAM_CODEC = StreamCodec.composite(
                CustomData.STREAM_CODEC,
                Occupant::entityData,
                ByteBufCodecs.VAR_INT,
                Occupant::ticksInBurrow,
                ByteBufCodecs.VAR_INT,
                Occupant::minTicksInBurrow,
                Occupant::new
        );
    }
}
