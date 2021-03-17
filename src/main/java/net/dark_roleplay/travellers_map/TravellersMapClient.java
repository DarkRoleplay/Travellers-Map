package net.dark_roleplay.travellers_map;

import net.dark_roleplay.travellers_map.config.MapperConfigs;
import net.dark_roleplay.travellers_map.screens.Keybinds;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TravellersMapClient {

	public static void modConstructorInit(){
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MapperConfigs.MAPPER_SPECS);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(TravellersMapClient::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(Keybinds::registerKeybinds);
	}

	public static void clientSetup(FMLClientSetupEvent event) {

	}
}
