package net.potionstudios.biomeswevegone.client;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandler;

import java.util.function.Supplier;

/**
 * Sounds for Oh The Biomes We've Gone.
 * @author YaBoiChips
 */
public class BWGSounds {

    public static final Supplier<SoundEvent> ODDION_DEATH = createFixedRangeEvent("oddion.die", 8F);
    public static final Supplier<SoundEvent> ODDION_HURT = createFixedRangeEvent("oddion.hurt", 8F);
    public static final Supplier<SoundEvent> ODDION_AMBIENT = createFixedRangeEvent("oddion.ambient", 8F);
    public static final Supplier<SoundEvent> ODDION_HAPPY = createFixedRangeEvent("oddion.happy", 8F);

    public static final Supplier<SoundEvent> MUSIC_DISC_PIXIE_CLUB = RegistrationHandler.registerSoundEvent("music_disc.pixie_club", () -> SoundEvent.createVariableRangeEvent(BiomesWeveGone.id("music_disc.pixie_club")));

    public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_CRAG_GARDENS = RegistrationHandler.registerSoundEventHolder("music.overworld.crag_gardens");
    public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_ERODED_BOREALIS = RegistrationHandler.registerSoundEventHolder("music.overworld.eroded_borealis");
    public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_FORGOTTEN_FOREST = RegistrationHandler.registerSoundEventHolder("music.overworld.forgotten_forest");

    private static Supplier<SoundEvent> createFixedRangeEvent(String id, float range) {
        return RegistrationHandler.registerSoundEvent(id, () -> SoundEvent.createFixedRangeEvent(BiomesWeveGone.id(id), range));
    }

    public static void sounds() {
        BiomesWeveGone.LOGGER.info("Registering Biomes We've Gone Sounds");
    }
}
