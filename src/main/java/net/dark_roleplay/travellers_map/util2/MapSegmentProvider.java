package net.dark_roleplay.travellers_map.util2;

import net.dark_roleplay.travellers_map.util.MapFileHelper;
import net.dark_roleplay.travellers_map.util.MapSegment;
import net.dark_roleplay.travellers_map.util.MapSegmentUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MapSegmentProvider {

    private final RegistryKey<World> dimension;
    private final Map<Long, MapSegment> segments = new HashMap<>();
    private final File mapSegmentFolder;

    protected MapSegmentProvider(RegistryKey<World> dimension){
        this.dimension = dimension;
        this.mapSegmentFolder = new File(MapFileHelper.getDimFolder(dimension), "/default_mapper");
        if(!this.mapSegmentFolder.exists() || !this.mapSegmentFolder.isDirectory())
            this.mapSegmentFolder.mkdirs();
    }

    public MapSegment getMapSegment(long ident){
        return getMapSegment(ident, null);
    }

    public MapSegment getMapSegment(IChunk chunk){
        return getMapSegment(MapSegmentUtil.getSegment(chunk), chunk);
    }

    private MapSegment getMapSegment(long ident, IChunk chunk){
        MapSegment segment = segments.get(ident);
        if(segment == null || segment.isEmpty()){
            segment = loadOrCreateSegment(ident, chunk);
            segments.put(ident, segment);
        };
        return segment;
    }


    private MapSegment loadOrCreateSegment(long ident, IChunk chunk){
        int segmentX = (int) (ident >> 32 & 0xFFFFFFFF);
        int segmentZ = (int) (ident & 0xFFFFFFFF);
        String name = "segment_" + segmentX + "_" +  segmentZ;

        File mapFile = new File(this.mapSegmentFolder, name + ".png");
        if(mapFile.exists() || chunk != null){
            return new MapSegment(name, mapFile, ident);
        }

        return MapSegment.EMPTY;
    }

    public void updateMaps(){
        for(MapSegment mapSegment : segments.values()){
            mapSegment.update();
        }
    }
}
