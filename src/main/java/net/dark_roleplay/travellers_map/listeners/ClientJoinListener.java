package net.dark_roleplay.travellers_map.listeners;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.mapping.MappingHelper;
import net.dark_roleplay.travellers_map.util.MapFileHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TravellersMap.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientJoinListener {

	@SubscribeEvent
	public static void joinWorldListener(EntityJoinWorldEvent event){
		if(!event.getWorld().isRemote()) return;
		if(event.getEntity() instanceof PlayerEntity){
			RegistryKey<World> key = event.getWorld().getDimensionKey();
			MapFileHelper.setupBaseMapFolder(key.getRegistryName());

			MappingHelper.initMapper();
		}
	}
}
