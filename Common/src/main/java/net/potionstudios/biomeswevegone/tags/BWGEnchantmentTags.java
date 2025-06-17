package net.potionstudios.biomeswevegone.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

public interface BWGEnchantmentTags {

    TagKey<Enchantment> PREVENTS_PUMPKIN_WARDENS_SPAWNS_WHEN_MINING = create("prevents_pumpkin_warden_when_mining");

    private static TagKey<Enchantment> create(String id) {
        return TagKey.create(Registries.ENCHANTMENT, BiomesWeveGone.id(id));
    }
}
