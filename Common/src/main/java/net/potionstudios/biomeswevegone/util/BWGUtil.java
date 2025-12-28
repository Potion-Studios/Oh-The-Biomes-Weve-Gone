package net.potionstudios.biomeswevegone.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author CorgiTaco
 */
public class BWGUtil {

    public static boolean useTagReplacements = false;


    public static <V> List<List<V>> convert2DArray(V[][] arrayToConvert) {
        List<List<V>> convertedArrays = new ArrayList<>();
        for (V[] vs : arrayToConvert) {
            convertedArrays.add(Arrays.asList(vs));
        }
        return convertedArrays;
    }


    @SuppressWarnings("unchecked")
    public static <T> ResourceKey<T>[][] _2DResourceKeyArrayTo2DList(List<List<ResourceKey<T>>> listToConvert) {
        List<ResourceKey<T>[]> resultList = new ArrayList<>(listToConvert.size());
        for (List<ResourceKey<T>> vs : listToConvert) {
            resultList.add(vs.toArray(ResourceKey[]::new));
        }

        return resultList.toArray(ResourceKey[][]::new);
    }

    public static <T> String print2DResourceKeyArray(ResourceKey<T>[][] valueToPrint) {
        StringBuilder builder = new StringBuilder();

        for (ResourceKey<T>[] value : valueToPrint) {
            builder.append(Arrays.toString(Arrays.stream(value).map(ResourceKey::identifier).toArray(Identifier[]::new))).append("\n");
        }
        return builder.toString();
    }

    public static Optional<BlockPos> readBlockPos(CompoundTag tag, String key) {
        int[] is = tag.getIntArray(key).orElseThrow();
        return is.length == 3 ? Optional.of(new BlockPos(is[0], is[1], is[2])) : Optional.empty();
    }

    public static Tag writeBlockPos(BlockPos pos) {
        return new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()});
    }
}