package net.potionstudios.biomeswevegone.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTypes;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import org.jetbrains.annotations.ApiStatus;

@Deprecated
@ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
class BWGVillagerUpgradeCommand {
	static LiteralArgumentBuilder<CommandSourceStack> register() {
		LiteralArgumentBuilder<CommandSourceStack> upgradeVillagers = LiteralArgumentBuilder.literal("upgrade_villagers");
		upgradeVillagers.requires(commandSourceStack -> commandSourceStack.hasPermission(4));
		upgradeVillagers.executes(context -> {
			MinecraftServer server = context.getSource().getServer();
			ServerLevel level = server.getLevel(Level.OVERWORLD);
			level.getAllEntities().forEach(entity -> {
				if (entity instanceof Villager villager) {
					Holder<Biome> biome = level.getBiome(villager.blockPosition());
					if (biome.is(BWGBiomes.SKYRIS_VALE))
						villager.setVillagerData(villager.getVillagerData().setType(BWGVillagerTypes.SKYRIS.get()));
					else if (biome.is(BWGBiomes.WEEPING_WITCH_FOREST))
						villager.setVillagerData(villager.getVillagerData().setType(BWGVillagerTypes.SALEM.get()));
					else if (biome.is(BWGBiomes.RED_ROCK_VALLEY) || biome.is(BWGBiomes.RED_ROCK_PEAKS))
						villager.setVillagerData(villager.getVillagerData().setType(BWGVillagerTypes.RED_ROCK.get()));
					villager.refreshBrain(level);
				}
			});
			context.getSource().sendSuccess(() -> Component.translatable("biomeswevegone.commands.upgrade_villagers.success").withStyle(ChatFormatting.GREEN), true);
			return 1;
		});
		return upgradeVillagers;
	}
}
