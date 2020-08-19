package net.dark_roleplay.travellers_map.objects.tickets;

import net.dark_roleplay.travellers_map.mapping.IMapSegmentTicket;
import net.dark_roleplay.travellers_map.util.MapSegmentUtil;

import java.util.HashMap;
import java.util.Map;

public class RenderTicket implements IMapSegmentTicket {

	private static final Map<Long, RenderTicket> TICKETS = new HashMap<>();

	private int unloadTicker = 3;

	public static RenderTicket getOrCreateTicket(long ident){
		RenderTicket ticket = TICKETS.computeIfAbsent(ident, key -> new RenderTicket());
		ticket.unloadTicker = 3;

		return ticket;
	}

	private RenderTicket(){}

	@Override
	public boolean isActive() {
		return unloadTicker-- > 0;
	}
}
