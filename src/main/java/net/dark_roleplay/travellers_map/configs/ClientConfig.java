package net.dark_roleplay.travellers_map.configs;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.configs.client.HudConfig;
import net.dark_roleplay.travellers_map.user_facing.huds.GuiAlignment;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = TravellersMap.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig {

	public static ForgeConfigSpec CLIENT_SPECS;

	public static HudConfig MINIMAP;
	public static HudConfig COMPASS;

	public static ForgeConfigSpec.BooleanValue SPIN_MINIMAP;
	public static ForgeConfigSpec.BooleanValue USE_BIOME_COLORS;

	static{
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.comment("Mapper Settings").push("mapper");
		USE_BIOME_COLORS = builder.comment("Enables the usage of Biome Colors on the mapper").worldRestart()
				.define("BiomeColors", true);
		builder.pop();

		builder.comment("Settings for the Minimap").push("minimap");
		MINIMAP = new HudConfig(builder, "Minimap", -65, 1, GuiAlignment.TOP_RIGHT);
		SPIN_MINIMAP = builder.comment("Set this to true and the minimap will spin in the direction you're facing")
				.define("Spin", false);
		builder.pop();

		builder.comment("Settings for the Compass (Unimplemented right now)").push("compass");
		COMPASS = new HudConfig(builder, "Compass", -128, 1, GuiAlignment.TOP);
		builder.pop();

		CLIENT_SPECS = builder.build();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {

	}

	@SubscribeEvent
	public static void onReload(final ModConfig.Reloading configEvent) {
	}
}
