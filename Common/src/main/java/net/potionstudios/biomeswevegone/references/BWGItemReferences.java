package net.potionstudios.biomeswevegone.references;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

public class BWGItemReferences {
    public static final ResourceKey<Item> BAOBAB_FRUIT = createKey("baobab_fruit");
    public static final ResourceKey<Item> YUCCA_FRUIT = createKey("yucca_fruit");
    public static final ResourceKey<Item> GREEN_APPLE = createKey("green_apple");
    public static final ResourceKey<Item> SOUL_FRUIT = createKey("soul_fruit");
    public static final ResourceKey<Item> VETAL = createKey("vetal");
    public static final ResourceKey<Item> ODDION_BULB = createKey("oddion_bulb");
    public static final ResourceKey<Item> BLUEBERRIES = createKey("blueberries");
    public static final ResourceKey<Item> WHITE_PUFFBALL_SPORES = createKey("white_puffball_spores");
    public static final ResourceKey<Item> CATTAIL_SPROUT = createKey("cattail_sprout");
    public static final ResourceKey<Item> FLUORESCENT_CATTAIL_SPROUT = createKey("fluorescent_cattail_sprout");
    public static final ResourceKey<Item> BLUE_GLOWCANE_SHOOT = createKey("blue_glowcane_shoot");
    public static final ResourceKey<Item> GREEN_GLOWCANE_SHOOT = createKey("green_glowcane_shoot");
    public static final ResourceKey<Item> RED_GLOWCANE_SHOOT = createKey("red_glowcane_shoot");
    public static final ResourceKey<Item> YELLOW_GLOWCANE_SHOOT = createKey("yellow_glowcane_shoot");
    public static final ResourceKey<Item> PALE_PUMPKIN_SEEDS = createKey("pale_pumpkin_seeds");

    private static ResourceKey<Item> createKey(String id) {
        return BiomesWeveGone.key(Registries.ITEM, id);
    }
}
