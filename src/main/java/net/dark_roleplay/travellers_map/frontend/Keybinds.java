package net.dark_roleplay.travellers_map.frontend;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class Keybinds {

	private static final String KEY_LOCALIZER = "key." + TravellersMap.MODID + ".";
	private static final String CAT_LOCALIZER = "key.category." + TravellersMap.MODID + ".";

	public static KeyBinding CREATE_WAYPOINT = new KeyBinding(KEY_LOCALIZER + "create_waypoint", GLFW.GLFW_KEY_B, CAT_LOCALIZER + "general");
	public static KeyBinding SHOW_WAYPOINTS = new KeyBinding(KEY_LOCALIZER + "show_waypoints", GLFW.GLFW_KEY_N, CAT_LOCALIZER + "general");

	public static void registerKeybinds(FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(CREATE_WAYPOINT);
		ClientRegistry.registerKeyBinding(SHOW_WAYPOINTS);
	}
}
