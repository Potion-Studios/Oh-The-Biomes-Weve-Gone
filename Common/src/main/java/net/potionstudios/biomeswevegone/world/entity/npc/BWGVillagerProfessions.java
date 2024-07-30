package net.potionstudios.biomeswevegone.world.entity.npc;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;
import net.potionstudios.biomeswevegone.world.entity.ai.village.poi.BWGPoiTypes;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BWGVillagerProfessions {

    public static final Supplier<VillagerProfession> FORAGER = register("forager", () -> create("forager", BWGPoiTypes.FORAGER, null, ImmutableSet.of(
            BWGItems.WHITE_PUFFBALL_SPORES.get(), BWGBlocks.WHITE_PUFFBALL.getBlock().asItem()
    )));

    public static void init() {
        BiomesWeveGone.LOGGER.info("Registering BWG Villager Professions");
    }

    private static VillagerProfession create(String name, ResourceKey<PoiType> poiType, @Nullable SoundEvent soundEvent, ImmutableSet<Item> requestedItems) {
        return new VillagerProfession(name, poiTypeHolder -> poiTypeHolder.is(poiType), (poiTypeHolder) -> poiTypeHolder.is(poiType),
                requestedItems, ImmutableSet.of(), soundEvent);

    }

    private static Supplier<VillagerProfession> register(String id, Supplier<VillagerProfession> villagerProfession){
        return RegistrationHandlerA.REGISTRATION.register(BuiltInRegistries.VILLAGER_PROFESSION, id, villagerProfession);
    }
}
