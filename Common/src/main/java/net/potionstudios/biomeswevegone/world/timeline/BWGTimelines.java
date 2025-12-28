package net.potionstudios.biomeswevegone.world.timeline;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.timeline.Timeline;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.attribute.BWGEnvironmentAttributes;

import java.util.Map;
import java.util.function.Supplier;

public interface BWGTimelines {
	Map<ResourceKey<Timeline>, TimelineFactory> TIMELINE_FACTORIES = new Reference2ObjectOpenHashMap<>();

	ResourceKey<Timeline> PUMPKIN_WARDEN = register("pumpkin_warden", () -> Timeline.builder()
			.setPeriodTicks(24000)
			.addTrack(BWGEnvironmentAttributes.PUMPKIN_WARDEN_ACTIVITY.get(), (builder -> builder
					.addKeyframe(10, Activity.IDLE)
					.addKeyframe(2000, Activity.WORK)
					.addKeyframe(5000, Activity.PLAY)
					.addKeyframe(7000, Activity.WORK)
					.addKeyframe(9000, Activity.MEET)
					.addKeyframe(10000, Activity.PLAY)
					.addKeyframe(12000, Activity.REST))));

	private static ResourceKey<Timeline> register(String id, Supplier<Timeline.Builder> timelineBuilder) {
		ResourceKey<Timeline> key = BiomesWeveGone.key(Registries.TIMELINE, id);
		TIMELINE_FACTORIES.put(key, context -> timelineBuilder.get().build());
		return key;
	}

	static void timelines() {
		BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Timelines");
	}

	@FunctionalInterface
	interface TimelineFactory {
		Timeline generate(BootstrapContext<Timeline> context);
	}
}
