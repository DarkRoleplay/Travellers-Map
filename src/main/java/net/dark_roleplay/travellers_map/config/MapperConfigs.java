package net.dark_roleplay.travellers_map.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MapperConfigs {

	public static ForgeConfigSpec MAPPER_SPECS;

	public static ForgeConfigSpec.IntValue THREAD_COUNT;

	static{
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.comment("Sets the amount of threads to be used for mapping, I'd recommend to experiment with this setting if you have performance issues.");
		THREAD_COUNT = builder.worldRestart().defineInRange("ThreadCount", 4, 1, Math.max(4, Runtime.getRuntime().availableProcessors()));

		MAPPER_SPECS = builder.build();
	}
}
