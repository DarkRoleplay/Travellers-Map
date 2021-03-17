package net.dark_roleplay.travellers_map.waypoints.listeners;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.waypoints.Waypoint;
import net.dark_roleplay.travellers_map.screens.Keybinds;
import net.dark_roleplay.travellers_map.screens.screens.waypoints.WaypointCreationScreen;
import net.dark_roleplay.travellers_map.screens.screens.waypoints.WaypointOverviewScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TravellersMap.MODID, value = Dist.CLIENT)
public class KeyInputListener {

	@SubscribeEvent
	public static void keyListeners(InputEvent.KeyInputEvent event){
		if(Keybinds.CREATE_WAYPOINT.isPressed()){
			Minecraft.getInstance().displayGuiScreen(new WaypointCreationScreen(new Waypoint("New Waypoint", null, 0xFFFFFF, null, Minecraft.getInstance().player.getPosition())));
		}
		else if(Keybinds.SHOW_WAYPOINTS.isPressed()){
			Minecraft.getInstance().displayGuiScreen(new WaypointOverviewScreen());
		}
	}
}
