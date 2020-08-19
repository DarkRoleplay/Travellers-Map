package net.dark_roleplay.travellers_map.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.objects.mappers.CaveColorMapper;
import net.dark_roleplay.travellers_map.objects.mappers.LightingColorMapper;
import net.dark_roleplay.travellers_map.mapping.IMapSegmentTicket;
import net.dark_roleplay.travellers_map.util2.MapSegmentProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MapSegment {

    public static MapSegment EMPTY = new MapSegment(null, "empty", null, 0L){
        @Override
        public boolean isEmpty(){
            return true;
        }
    };

    private final MapSegmentProvider owner;

    //IO Parts
    private File mapFile;
    private long identifier;

    private int segX;
    private int segZ;

    private DynamicTexture dynTexture;
    private NativeImage mapImage;
    private ResourceLocation mapLocation;
    private Set<IMapSegmentTicket> tickets = new HashSet<>();
    boolean dirtyIO = false;
    boolean dirtyGPU = false;
    private String segmentName;

    public MapSegment(MapSegmentProvider owner, String segmentName, File mapFile, long identifier, IMapSegmentTicket... tickets){
        this.owner = owner;
        this.segmentName = segmentName;
        this.mapFile = mapFile;
        this.identifier = identifier;
        this.segX = (int) (identifier >> 32 & 0xFFFFFFFF);
        this.segZ = (int) (identifier & 0xFFFFFFFF);
        try {
            if(mapFile != null && mapFile.exists()) this.mapImage = NativeImage.read(new FileInputStream(mapFile));
            else this.mapImage = new NativeImage(NativeImage.PixelFormat.RGBA, 512, 512, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(IMapSegmentTicket ticket : tickets)
            this.tickets.add(ticket);
    }

    public void addTicket(IMapSegmentTicket ticket) {
        this.tickets.add(ticket);
    }

    public DynamicTexture getDynTexture() {
        if(this.dynTexture == null){
            this.dynTexture = new DynamicTexture(this.mapImage);
            this.mapLocation = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation(this.segmentName, this.dynTexture);
        }
        return dynTexture;
    }

    public void updateChunk(World world, IChunk chunk){
        if(this.isEmpty()) return;
        this.owner.getMapper().mapChunk(world, chunk, mapImage);
    }

    public void markDirty(){
        if(this.isEmpty()) return;
        this.dirtyIO = true;
        this.dirtyGPU = true;
    }

    public void updadteGPU() {
        if(this.isEmpty()) return;
        if(this.dirtyGPU && this.dynTexture != null){
            this.dynTexture.updateDynamicTexture();
            this.dirtyGPU = false;
        }
    }

    public long getIdent(){
        return this.identifier;
    }

    public void update(){
        if(this.isEmpty()) return;
        if(this.dirtyIO){
            try {
                this.mapImage.write(this.mapFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.dirtyIO = false;
        }

        Iterator<IMapSegmentTicket> iter = tickets.iterator();
        while(iter.hasNext())
            if(!iter.next().isActive()) iter.remove();

        if(tickets.isEmpty()){
            this.free();
        }
    }

    public void free(){
        if(this.isEmpty()) return;
        RenderSystem.recordRenderCall(() -> Minecraft.getInstance().getTextureManager().deleteTexture(this.mapLocation));
        this.owner.unloadSegment(this);
    }

    public int getSegX() {
        return segX;
    }

    public int getSegZ() {
        return segZ;
    }

    public boolean isEmpty(){
        return false;
    }
}