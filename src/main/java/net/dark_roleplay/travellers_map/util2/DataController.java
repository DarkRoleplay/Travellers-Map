package net.dark_roleplay.travellers_map.util2;

import net.minecraft.client.Minecraft;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class DataController {
    private static final Map<RegistryKey<World>, MapSegmentProvider> MAP_SEGMENT_PROVIDERS = new HashMap<>();

    public static MapSegmentProvider getMapSegmentProvider(RegistryKey<World> dimension){
        return MAP_SEGMENT_PROVIDERS.computeIfAbsent(dimension, MapSegmentProvider::new);
    }

    public static MapSegmentProvider getCurrentMapSegmentProvider(){
        return getMapSegmentProvider(getPlayerWorldKey());
    }

    private static RegistryKey<World> getPlayerWorldKey(){
        return Minecraft.getInstance().world.func_234923_W_();
    }

    public static void updateAllMaps(){
        for(MapSegmentProvider provider : MAP_SEGMENT_PROVIDERS.values()){
            provider.updateMaps();
        }
    }

//    public static void unloadMapSegment(MapSegment segment){
//        segment.
//    }
}
