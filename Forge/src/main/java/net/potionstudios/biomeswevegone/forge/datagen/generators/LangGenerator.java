package net.potionstudios.biomeswevegone.forge.datagen.generators;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import net.potionstudios.biomeswevegone.world.item.BWGCreativeTabs;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * This class is used to generate the language file for the mod.
 * @see LanguageProvider
 * @author Joseph T. McQuigg
 */
public class LangGenerator extends LanguageProvider {
    public LangGenerator(PackOutput output, String locale) {
        super(output, BiomesWeveGone.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(BWGCreativeTabs.CREATIVE_TAB.get().getDisplayName().getString(), "Biomes We've Gone");
        add(BWGCreativeTabs.WOOD_TAB.get().getDisplayName().getString(), "Biomes We've Gone Wood");
        BWGBlocks.BLOCKS.forEach(block -> add(block.get(), getBlockName(block)));
        BWGItems.ITEMS.forEach(item -> add(item.get(), getItemName(item)));
        add(BWGItems.MUSIC_DISC_PIXIE_CLUB.get(), "Music Disc");
        add("item.biomeswevegone.music_disc_pixie_club.desc", "AOCAWOL - Pixie Club");
        BWGWood.WOOD_BLOCK_ITEMS.forEach(wood -> add(wood.get(), getItemName(wood)));
        BWGWood.WOOD.stream().filter(wood -> wood.get() instanceof FlowerPotBlock).forEach(wood -> add(wood.get(), getBlockName(wood)));
        add(BWGEntities.MAN_O_WAR.get(), "Man O' War");
        add(BWGEntities.PUMPKIN_WARDEN.get(), "Pumpkin Warden");
        add(BWGEntities.ODDION.get(), "Oddion");
        add(BWGEntities.BWG_BOAT.get(), "Biomes We've Gone Boat");
        add(BWGEntities.BWG_CHEST_BOAT.get(), "Biomes We've Gone Chest Boat");
        BWGBiomes.BIOME_FACTORIES.forEach((key, factory) -> add("biome." + BiomesWeveGone.MOD_ID + "." + key.location().getPath(), getBiomeName(key)));

        add(advancement("title.root"), "Oh The Biomes We've Gone");
        add(advancement("description.root"), "Launch a world with the Oh The Biomes We've Gone");
        add(advancement("adventure.root.title"), "Adventure");
        add(advancement("adventure.root.description"), "The root of all things BWG adventure");
        add(advancement("adventure.oh_the_biomes_weve_gone.title"), "Oh The Biomes We've Gone");
        add(advancement("adventure.oh_the_biomes_weve_gone.description"), "Explore all of the BWG biomes");
        add(advancement("adventure.inside_quicksand.title"), "I'm Sinking?");
        add(advancement("adventure.inside_quicksand.description"), "Fall into quicksand");
        add(advancement("adventure.little_house_on_the_prairie.title"), "Little House on the Prairie");
        add(advancement("adventure.little_house_on_the_prairie.description"), "Find all the Prairie houses");
        add(advancement("husbandry.root.title"), "Husbandry");
        add(advancement("husbandry.root.description"), "The root of all things BWG husbandry");
        add(advancement("husbandry.granny_smith.title"), "Granny Smith?");
        add(advancement("husbandry.granny_smith.description"), "Obtain a Green Apple from the Skyris Highlands");
        add(advancement("husbandry.forager.title"), "Forager");
        add(advancement("husbandry.forager.description"), "Obtain White Puffball Caps");
        add(advancement("husbandry.berrily_alive.title"), "Berrily Alive");
        add(advancement("husbandry.berrily_alive.description"), "Obtain Blueberries");
        add(advancement("husbandry.just_like_grandmas.title"), "Just Like Grandma's");
        add(advancement("husbandry.just_like_grandmas.description"), "Craft both of the BWG pies.");
        add(advancement("husbandry.johnny_appleseed.title"), "Johnny Appleseed");
        add(advancement("husbandry.johnny_appleseed.description"), "Find All 3 Apples");
        add(advancement("husbandry.hot_diggity_not_dog.title"), "Hot Diggity Not Dog");
        add(advancement("husbandry.hot_diggity_not_dog.description"), "Cook a Cattail on a campfire, you might wanna back away...");

        add("entity.minecraft.villager.biomeswevegone.forager", "Forager");
        add("entity.minecraft.villager.forager", "Forager");

        add("subtitles.entity.oddion_ambient", "Oddion purrs");
        add("subtitles.entity.oddion_hurt", "Oddion hurts");
        add("subtitles.entity.oddion_death", "Oddion dies");
        add("subtitles.entity.oddion_happy", "Oddion happy purrs");

        add(death("inQuicksand"), "%s tried to swim in the desert");
        add(death("cattailExplosion"), "%s got too curious and put a cattail in a campfire");
    }

    private static String advancement(String key) {
        return "advancements." + BiomesWeveGone.MOD_ID + "." + key;
    }

    private static String death(String key) {
        return "death.attack." + BiomesWeveGone.MOD_ID + "." + key;
    }


    private String getBlockName(Supplier<? extends Block> item) {
        return getId((ForgeRegistries.BLOCKS.getKey(item.get()).getPath()));
    }

    private String getItemName(Supplier<? extends ItemLike> item) {
        return getId(ForgeRegistries.ITEMS.getKey(item.get().asItem()).getPath());
    }

    private String getBiomeName(ResourceKey<Biome> biome) {
        return getId(biome.location().getPath());
    }

    @NotNull
    private String getId(String name) {
        name = name.substring(name.indexOf(":") + 1);  //Removes Mod Tag from front of name
        name = name.replace('_', ' ');
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        for (int i = 0; i < name.length(); i++)
            if (name.charAt(i) == ' ')
                name = name.substring(0, i + 1) + name.substring(i + 1, i + 2).toUpperCase() + name.substring(i + 2);
        return name;
    }
}
