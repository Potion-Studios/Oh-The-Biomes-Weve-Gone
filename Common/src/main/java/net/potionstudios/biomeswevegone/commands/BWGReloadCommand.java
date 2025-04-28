package net.potionstudios.biomeswevegone.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.config.configs.BWGMiscConfig;
import net.potionstudios.biomeswevegone.config.configs.BWGMobSpawnConfig;

class BWGReloadCommand {

	static LiteralArgumentBuilder<CommandSourceStack> register() {
		LiteralArgumentBuilder<CommandSourceStack> reload = LiteralArgumentBuilder.literal("reload");
		reload.requires(commandSourceStack -> PlatformHandler.PLATFORM_HANDLER.hasPermission(commandSourceStack, "biomeswevegone.commands.reload"));
		reload.executes(context -> {
			BWGMiscConfig.reload();
			BWGMobSpawnConfig.reload();
			context.getSource().sendSuccess(() -> Component.translatable("biomeswevegone.commands.reload.success").withStyle(ChatFormatting.GREEN), true);
			return 1;
		});

		LiteralArgumentBuilder<CommandSourceStack> reloadMisc = LiteralArgumentBuilder.literal("misc");
		reloadMisc.requires(commandSourceStack -> PlatformHandler.PLATFORM_HANDLER.hasPermission(commandSourceStack, "biomeswevegone.commands.reload"));
		reloadMisc.executes(context -> {
			BWGMiscConfig.reload();
			context.getSource().sendSuccess(() -> Component.translatable("biomeswevegone.commands.reload.misc.success").withStyle(ChatFormatting.GREEN), true);
			return 1;
		});

		LiteralArgumentBuilder<CommandSourceStack> reloadSpawn = LiteralArgumentBuilder.literal("spawn");
		reloadSpawn.requires(commandSourceStack -> PlatformHandler.PLATFORM_HANDLER.hasPermission(commandSourceStack, "biomeswevegone.commands.reload"));
		reloadSpawn.executes(context -> {
			BWGMobSpawnConfig.reload();
			context.getSource().sendSuccess(() -> Component.translatable("biomeswevegone.commands.reload.spawn.success").withStyle(ChatFormatting.GREEN), true);
			return 1;
		});

		reload.then(reloadMisc).then(reloadSpawn);
		return reload;
	}
}
