package net.dark_roleplay.travellers_map.objects.tickets;

import net.dark_roleplay.travellers_map.mapping.IMapSegmentTicket;
import net.dark_roleplay.travellers_map.util.MapSegmentUtil;
import net.dark_roleplay.travellers_map.user_facing.screens.full_map.FullMapScreen;
import net.dark_roleplay.travellers_map.user_facing.screens.minimap.settings.MinimapSettingsScreen;
import net.dark_roleplay.travellers_map.user_facing.screens.waypoints.WayPointCreationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import java.util.HashMap;
import java.util.Map;

public class FullMapRenderTicket implements IMapSegmentTicket {

	private static final Map<Long, FullMapRenderTicket> TICKETS = new HashMap<>();

	public static FullMapRenderTicket getOrCreateTicket(int segX, int segZ){
		long ident = MapSegmentUtil.toSegment(segX, segZ);
		FullMapRenderTicket ticket = TICKETS.get(ident);
		if(ticket == null) {
			ticket = new FullMapRenderTicket();
			TICKETS.put(ident, ticket);
		}
		return ticket;
	}

	private FullMapRenderTicket(){}

	@Override
	public boolean isActive() {
		if(Minecraft.getInstance().player == null) return false;
		Screen currentScreen = Minecraft.getInstance().currentScreen;
		return currentScreen instanceof FullMapScreen || currentScreen instanceof WayPointCreationScreen || currentScreen instanceof MinimapSettingsScreen;
	}
}
