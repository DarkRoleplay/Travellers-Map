package net.dark_roleplay.travellers_map;

import net.dark_roleplay.travellers_map.frontend.Keybinds;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TravellersMapClient {

	public static void modConstructorInit(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(TravellersMapClient::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(Keybinds::registerKeybinds);
	}

	public static void clientSetup(FMLClientSetupEvent event) {

	}
}
