package net.dark_roleplay.travellers_map.waypointer;

import net.dark_roleplay.travellers_map.waypointer.icons.WaypointIcon;
import net.minecraft.util.RegistryKey;

import java.util.Map;

public class Waypoint2{

	private String name;
	private int color;
	private WaypointIcon icon;
	private Map<RegistryKey, WaypointPosition> positions;

	public Waypoint2(String name, int color, WaypointIcon icon, Map positions) {
		this.name = name;
		this.color = color;
		this.icon = icon;
		this.positions = positions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public WaypointIcon getIcon() {
		return icon;
	}

	public void setIcon(WaypointIcon icon) {
		this.icon = icon;
	}

	public Map<RegistryKey, WaypointPosition> getPositions() {
		return positions;
	}

	public void setPositions(Map<RegistryKey, WaypointPosition> positions) {
		this.positions = positions;
	}
}
