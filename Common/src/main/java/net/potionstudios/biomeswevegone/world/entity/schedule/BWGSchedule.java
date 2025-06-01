package net.potionstudios.biomeswevegone.world.entity.schedule;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public class BWGSchedule {
	public static final Supplier<Schedule> PUMPKIN_WARDEN = register("pumpkin_warden", new ScheduleBuilder(new Schedule())
			.changeActivityAt(10, Activity.IDLE)
			.changeActivityAt(2000, Activity.WORK)
			.changeActivityAt(5000, Activity.PLAY)
			.changeActivityAt(7000, Activity.WORK)
			.changeActivityAt(9000, Activity.MEET)
			.changeActivityAt(10000, Activity.PLAY)
			.changeActivityAt(12000, Activity.REST));

	private static Supplier<Schedule> register(String key, ScheduleBuilder scheduleBuilder) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.SCHEDULE, key, scheduleBuilder::build);
	}

	public static void schedules() {
		BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Schedules");
	}
}
