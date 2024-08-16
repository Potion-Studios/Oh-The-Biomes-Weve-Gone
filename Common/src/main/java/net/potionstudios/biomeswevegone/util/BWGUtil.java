package net.potionstudios.biomeswevegone.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            builder.append(Arrays.toString(Arrays.stream(value).map(ResourceKey::location).toArray(ResourceLocation[]::new))).append("\n");
        }
        return builder.toString();
    }
}