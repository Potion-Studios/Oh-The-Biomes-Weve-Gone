package net.potionstudios.biomeswevegone.forge.datagen.generators.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Stream;

class EntityLootGenerator extends EntityLootSubProvider {
    private static final ArrayList<EntityType<?>> knownEntities = new ArrayList<>();
    protected EntityLootGenerator() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(BWGEntities.ODDION.get(), LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(BWGItems.ODDION_BULB.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                ));

        add(BWGEntities.MAN_O_WAR.get(), LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.STRING)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f)))
                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 2.0f))))
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.PHANTOM_MEMBRANE)
                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))
                )));
    }

    @Override
    protected void add(@NotNull EntityType<?> entityType, @NotNull ResourceLocation lootTableLocation, LootTable.@NotNull Builder builder) {
        knownEntities.add(entityType);
        super.add(entityType, lootTableLocation, builder);
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return knownEntities.stream();
    }
}
