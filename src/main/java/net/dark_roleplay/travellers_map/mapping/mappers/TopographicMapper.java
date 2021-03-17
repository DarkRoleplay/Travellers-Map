package net.dark_roleplay.travellers_map.mapping.mappers;

import jdk.jfr.internal.Utils;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;

public class TopographicMapper extends Mapper{
	public TopographicMapper() {
		super(new ResourceLocation(TravellersMap.MODID, "topographic"));
	}

	@Override
	public void mapChunk(IBlockReader world, IChunk chunk, int[] colors) {
		//TODO Implement Topographic Mapper
		for(int x = 0; x < 16; x++){
			for(int z = 0; z < 16; z++){
				int height = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, x, z);
				colors[x * 16 + z] = 256 << 24 | height << 16 | height << 8 | height;
			}
		}
	}
}
