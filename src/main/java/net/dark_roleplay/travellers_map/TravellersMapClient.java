package net.dark_roleplay.travellers_map;

import net.dark_roleplay.travellers_map.waypointer.icons.WaypointIcons;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;

public class TravellersMapClient {

	public static void modConstructorInit(){
		IResourceManager resManager = Minecraft.getInstance().getResourceManager();

		if(resManager instanceof IReloadableResourceManager){
			IReloadableResourceManager relResManager = (IReloadableResourceManager) resManager;

			relResManager.addReloadListener(WaypointIcons::resourceReload);

		}

	}
}
