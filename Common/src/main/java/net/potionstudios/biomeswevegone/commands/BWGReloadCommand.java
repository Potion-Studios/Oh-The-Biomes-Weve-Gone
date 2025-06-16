package net.potionstudios.biomeswevegone.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.potionstudios.biomeswevegone.config.ConfigLoader;
import net.potionstudios.biomeswevegone.config.configs.BWGMobSpawnConfig;

class BWGReloadCommand {

	static LiteralArgumentBuilder<CommandSourceStack> register() {
		LiteralArgumentBuilder<CommandSourceStack> reload = LiteralArgumentBuilder.literal("reload");
		reload.requires(commandSourceStack -> commandSourceStack.hasPermission(2));
		reload.executes(context -> {
			BWGMobSpawnConfig.INSTANCE = ConfigLoader.loadConfig(BWGMobSpawnConfig.class, "spawn").spawn;
			context.getSource().sendSuccess(() -> Component.translatable("biomeswevegone.commands.reload.success").withStyle(ChatFormatting.GREEN), true);
			return 1;
		});

		LiteralArgumentBuilder<CommandSourceStack> reloadSpawn = LiteralArgumentBuilder.literal("spawn");
		reloadSpawn.requires(commandSourceStack -> commandSourceStack.hasPermission(2));
		reloadSpawn.executes(context -> {
			BWGMobSpawnConfig.INSTANCE = ConfigLoader.loadConfig(BWGMobSpawnConfig.class, "spawn").spawn;
			context.getSource().sendSuccess(() -> Component.translatable("biomeswevegone.commands.reload.spawn.success").withStyle(ChatFormatting.GREEN), true);
			return 1;
		});

		reload.then(reloadSpawn);
		return reload;
	}

}
