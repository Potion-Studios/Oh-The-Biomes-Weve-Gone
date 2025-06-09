package net.potionstudios.biomeswevegone.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

public interface BWGEntityTypeTags {

    TagKey<EntityType<?>> ATTACKS_PUMPKIN_WARDEN = create("attacks_pumpkin_warden");

    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, BiomesWeveGone.id(id));
    }
}
