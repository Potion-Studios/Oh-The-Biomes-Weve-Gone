package net.potionstudios.biomeswevegone.neoforge.datagen.generators.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

class AdvancementLootGenerator implements LootTableSubProvider {

	private final HolderLookup.Provider loopupProvider;

	AdvancementLootGenerator(HolderLookup.Provider lookupProvider) {
		this.loopupProvider = lookupProvider;
	}

	@Override
	public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		createTable(output, "true_traveler/better_days_music_disc", 1.0f, 1.0f,
				lootItem(BWGItems.MUSIC_DISC_BETTER_DAYS.get())
		);
	}

	private LootPoolSingletonContainer.Builder<?> lootItem(ItemLike item) {
		return LootItem.lootTableItem(item);
	}

	private void createTable(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output, String id, float minRolls, float maxRolls, LootPoolEntryContainer.Builder<?>... entriesBuilder) {
		LootPool.Builder pool = LootPool.lootPool().setRolls(UniformGenerator.between(minRolls, maxRolls));
		for (LootPoolEntryContainer.Builder<?> entry : entriesBuilder) pool.add(entry);
		output.accept(BiomesWeveGone.key(Registries.LOOT_TABLE, "advancement_rewards/" + id), LootTable.lootTable().withPool(pool));
	}
}
