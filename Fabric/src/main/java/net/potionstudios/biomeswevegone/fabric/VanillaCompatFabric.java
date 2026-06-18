package net.potionstudios.biomeswevegone.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTypes;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.item.brewing.BWGBrewingRecipes;
import net.potionstudios.biomeswevegone.world.item.tools.ToolInteractions;
import net.potionstudios.biomeswevegone.world.level.block.BlockFeatures;
import net.potionstudios.biomeswevegone.config.configs.BWGWorldGenConfig;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.modifiers.BWGBiomeModifiers;

/**
 * Used for Vanilla compatibility on the Fabric platform.
 * @author Joseph T. McQuigg
 */
public class VanillaCompatFabric {

    public static void init() {
        ToolInteractions.registerStrippableBlocks(StrippableBlockRegistry::register);
        BlockFeatures.registerFlammable(FlammableBlockRegistry.getDefaultInstance()::add);
        BlockFeatures.registerFurnaceFuels((item, burnTime) -> FuelValueEvents.BUILD.register(((builder, context) -> builder.add(item, burnTime))));
        BlockFeatures.registerCompostables(CompostableRegistry.INSTANCE::add);
        ToolInteractions.registerFlattenables(FlattenableBlockRegistry::register);
        ToolInteractions.registerTillables((block, pair) -> TillableBlockRegistry.register(block, pair.getFirst(), pair.getSecond()));
        registerBiomeModifiers();
        registerLootModifiers();
        FabricPotionBrewingBuilder.BUILD.register(builder -> BWGBrewingRecipes.buildBrewingRecipes(builder::addMix));
        BWGVillagerTypes.setVillagerBiomes(VillagerType.BY_BIOME::put);
        UseEntityCallback.EVENT.register(((player, level, interactionHand, entity, entityHitResult) -> PumpkinWarden.villagerToPumpkinWarden(entity, player.getItemInHand(interactionHand), level) ? InteractionResult.SUCCESS : InteractionResult.PASS));
    }

    private static void registerBiomeModifiers() {
        if (BWGWorldGenConfig.INSTANCE.vanilla_additions.value()) {
            BWGBiomeModifiers.BIOME_MODIFIERS_FACTORIES.values().stream()
                    .filter(BWGBiomeModifiers.BWGBiomeModifier::isEnabled)
                    .forEach((modifier) -> BiomeModifications.addFeature(
                            BiomeSelectors.includeByKey(modifier.biomes()),
                            modifier.step(),
                            modifier.feature()
                    ));
        }
    }

    private static void registerLootModifiers() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries)  -> {
            if (key.equals(BuiltInLootTables.SNIFFER_DIGGING))
                tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BWGItems.FLUORESCENT_CATTAIL_SPROUT.get()).build())
                        .add(LootItem.lootTableItem(BWGItems.BLUE_GLOWCANE_SHOOT.get()).build())
                        .add(LootItem.lootTableItem(BWGItems.GREEN_GLOWCANE_SHOOT.get()).build())
                        .add(LootItem.lootTableItem(BWGItems.RED_GLOWCANE_SHOOT.get()).build())
                        .add(LootItem.lootTableItem(BWGItems.YELLOW_GLOWCANE_SHOOT.get()).build())
                        .add(LootItem.lootTableItem(BWGItems.PALE_PUMPKIN_SEEDS.get()).build()));
        });
    }
}
