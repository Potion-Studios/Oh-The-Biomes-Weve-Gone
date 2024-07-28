package net.potionstudios.biomeswevegone.world.level.levelgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BWGWorldGenerationUtil {



    public static List<BlockPredicate> blockMatchesInAllDirections(Function<BlockPos, BlockPredicate> factory) {
        List<BlockPredicate> predicates = new ArrayList<>(Direction.values().length);

        for (Direction value : Direction.values()) {
            predicates.add(factory.apply(new BlockPos(value.getStepX(), value.getStepY(), value.getStepZ())));
        }
        return predicates;
    }
}
