package net.dark_roleplay.travellers_map;

import net.dark_roleplay.travellers_map2.configs.ClientConfig;
import net.dark_roleplay.travellers_map2.handler.TravellersKeybinds;
import net.dark_roleplay.travellers_map.handler.TravellersNetworking;
import net.dark_roleplay.travellers_map.util.IOHandler;
import net.dark_roleplay.travellers_map.util.MapManager;
import net.dark_roleplay.travellers_map2.listeners.ResourceReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Timer;
import java.util.TimerTask;

@Mod(TravellersMap.MODID)
public class TravellersMap {

	public static final String MODID = "travellers_map";

	//TODO properly encapsulate fields
//    public static Thread IO_THREAD;
//    public static Thread MAPPING_THREAD;

	public TravellersMap() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_SPECS);
		//Config.loadConfig(ClientConfig.CLIENT_SPECS, FMLPaths.CONFIGDIR.get().resolve("mytutorial-client.toml"));

		DistExecutor.runWhenOn(Dist.CLIENT, () -> ResourceReloadListener::run);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(IOHandler::clientSetup);

		TravellersNetworking.initNetworking();
	}

	public void clientSetup(FMLClientSetupEvent event) {

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			TravellersKeybinds.registerKeybinds(event);
		});

//        IO_THREAD = new Thread(new MapIOThread(), "Travellers Map - IO");
		Timer timer = new Timer("TravellersMap - IO", true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				MapManager.performUpdates();
			}
		}, 5000, 5000);
	}
}
