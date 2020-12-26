package net.dark_roleplay.travellers_map.listeners;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.objects.waypoints.Waypoint;
import net.dark_roleplay.travellers_map.util.MapManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = TravellersMap.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DeathListener {

	@SubscribeEvent
	public static void joinWorldListener(ClientPlayerNetworkEvent.RespawnEvent event){
		if(event.getOldPlayer().getShouldBeDead()){
			MapManager.saveWaypoint(new Waypoint(UUID.randomUUID(), "Death", event.getOldPlayer().getPosition(), 0xFFFF0000, true), true);
		}
	}
}
