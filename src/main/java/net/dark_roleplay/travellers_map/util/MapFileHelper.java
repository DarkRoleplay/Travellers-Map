package net.dark_roleplay.travellers_map.util;

import net.dark_roleplay.travellers_map.util2.DataController;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.SaveFormat;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class MapFileHelper {

	private static File SP_FOLDER;
	private static File MP_FOLDER;

	private static File ACTIVE_FOLDER;
	private static File WAYPOINT_FOLDER;

	static{
		File baseFolder = new File("./mod_data/travellers_map/");
		SP_FOLDER = new File(baseFolder, "singleplayer");
		MP_FOLDER = new File(baseFolder, "multiplayer");

		SP_FOLDER.mkdirs();
		MP_FOLDER.mkdirs();
	}

	public static void setupBaseMapFolder(ResourceLocation dimensionLoc){
		boolean changedWorld = false;
		if(Minecraft.getInstance().isIntegratedServerRunning()){
			SaveFormat.LevelSave saveFile = Minecraft.getInstance().getIntegratedServer().anvilConverterForAnvilFile;
			File tmpFolder = new File(SP_FOLDER, saveFile.func_237282_a_());
			if(!tmpFolder.equals(ACTIVE_FOLDER)){
				changedWorld = true;
				ACTIVE_FOLDER = tmpFolder;
			}
		}else{
			SocketAddress socket = Minecraft.getInstance().getConnection().getNetworkManager().getRemoteAddress();
			if(socket instanceof InetSocketAddress){
				InetSocketAddress inet = (InetSocketAddress) socket;
				File tmpFolder = new File(MP_FOLDER, inet.getHostName() + "_" +  inet.getPort());
				if(!tmpFolder.equals(ACTIVE_FOLDER)){
					changedWorld = true;
					ACTIVE_FOLDER = tmpFolder;
				}
			}
		}

		WAYPOINT_FOLDER = new File(ACTIVE_FOLDER, "waypoints");
		WAYPOINT_FOLDER.mkdirs();

		if(changedWorld){
			DataController.clear();
			MapManager.WAYPOINTS.clear();
			MapManager.loadWaypoints(WAYPOINT_FOLDER);
		}
	}

	public static File getDimFolder(RegistryKey<World> dimension){
		return new File(ACTIVE_FOLDER, dimension.func_240901_a_().getNamespace() + "_" + dimension.func_240901_a_().getPath());
	}

	public static File getWaypointFolder(){
		return WAYPOINT_FOLDER;
	}
}
