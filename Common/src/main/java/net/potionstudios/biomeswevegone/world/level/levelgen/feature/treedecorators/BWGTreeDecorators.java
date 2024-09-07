package net.potionstudios.biomeswevegone.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public class BWGTreeDecorators {

    public static final Supplier<TreeDecoratorType<GlowBerryDecorator>> GLOW_BERRY_DECORATOR = register("glow_berry_decorator", GlowBerryDecorator.CODEC);

    private static <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> register(String id, MapCodec<P> codec) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.TREE_DECORATOR_TYPE, id, () -> new TreeDecoratorType<>(codec));
    }

    public static void treeDecorators() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Tree Decorators");
    }
}
