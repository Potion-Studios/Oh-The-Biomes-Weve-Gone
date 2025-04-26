package net.potionstudios.biomeswevegone.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

import java.util.function.Consumer;

public class BWGCommands {
    public static void register(Consumer<LiteralArgumentBuilder<CommandSourceStack>> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> base = LiteralArgumentBuilder.literal(BiomesWeveGone.MOD_ID);
        LiteralArgumentBuilder<CommandSourceStack> bwg = LiteralArgumentBuilder.literal("bwg");
        base.then(BWGReloadCommand.register());
        bwg.then(BWGReloadCommand.register());
        dispatcher.accept(base);
        dispatcher.accept(bwg);
    }
}
