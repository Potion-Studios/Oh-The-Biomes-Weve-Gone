package net.potionstudios.biomeswevegone.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.potionstudios.biomeswevegone.RegistrationHandler;

import java.util.function.Supplier;

public class BWGTreeDecorators {

    public static final Supplier<TreeDecoratorType<GlowBerryDecorator>> GLOW_BERRY_DECORATOR = register("glow_berry_decorator", GlowBerryDecorator.CODEC);

    private static <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> register(String id, Codec<P> codec) {
        return RegistrationHandler.registerTreeDecoratorType(id, () -> codec);
    }

    public static void init() {
    }
}
