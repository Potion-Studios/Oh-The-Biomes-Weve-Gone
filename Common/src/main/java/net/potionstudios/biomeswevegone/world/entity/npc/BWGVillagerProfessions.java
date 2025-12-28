package net.potionstudios.biomeswevegone.world.entity.npc;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.entity.ai.village.poi.BWGPoiTypes;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BWGVillagerProfessions {

    public static final ResourceKey<VillagerProfession> FORAGER = register("forager", BWGPoiTypes.FORAGER, null, () -> ImmutableSet.of(
            BWGItems.WHITE_PUFFBALL_SPORES.get(), BWGBlocks.WHITE_PUFFBALL.getBlock().asItem()
    ));

    private static ResourceKey<VillagerProfession> register(String id, ResourceKey<PoiType> poiType, @Nullable SoundEvent soundEvent, Supplier<ImmutableSet<Item>> requestedItems){
        ResourceKey<VillagerProfession> name = BiomesWeveGone.key(Registries.VILLAGER_PROFESSION, id);
        PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.VILLAGER_PROFESSION, id, () -> new VillagerProfession(Component.translatable("entity." + name.identifier().getNamespace() + ".villager." + name.identifier().getPath()), poiTypeHolder -> poiTypeHolder.is(poiType), (poiTypeHolder) -> poiTypeHolder.is(poiType), requestedItems.get(), ImmutableSet.of(), soundEvent));
        return name;
    }

    public static void professions() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Villager Professions");
    }
}
