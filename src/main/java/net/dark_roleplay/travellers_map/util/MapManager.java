package net.dark_roleplay.travellers_map.util;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.mapping.IMapSegmentTicket;
import net.dark_roleplay.travellers_map.objects.waypoints.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MapManager {

	public static final List<Waypoint> WAYPOINTS = new ArrayList<>();

	public static void saveWaypoint(Waypoint waypoint, boolean isNew) {
		File waypointFile = new File(MapFileHelper.getWaypointFolder(), waypoint.uuid.toString() + ".waypoint");
		try {
			CompressedStreamTools.write(waypoint.serializeNBT(), waypointFile);
			if (isNew) WAYPOINTS.add(waypoint);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadWaypoints(File waypointFolder) {
		File[] children = waypointFolder.listFiles();

		for (File file : children) {
			if (file.isDirectory()) loadWaypoints(file);
			else {
				try {
					TravellersMap.LOG.info("Reading waypoints from {}.", file.getName());
					Waypoint wp = new Waypoint(UUID.fromString(file.getName().substring(0, file.getName().lastIndexOf('.'))));
					wp.deserializeNBT(CompressedStreamTools.read(file));
					WAYPOINTS.add(wp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deleteWaypoint(Waypoint waypoint) {
		File waypointFile = new File(MapFileHelper.getWaypointFolder(), waypoint.uuid.toString() + ".waypoint");
		try {
			if (waypointFile.exists())
				Files.delete(waypointFile.toPath());
		} catch (IOException e) {
			TravellersMap.LOG.error("Failed to delete waypoint {}.", waypointFile.getName(), e);
			e.printStackTrace();
		}
		WAYPOINTS.remove(waypoint);
	}
}
