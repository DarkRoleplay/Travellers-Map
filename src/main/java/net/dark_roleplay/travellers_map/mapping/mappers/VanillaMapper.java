package net.dark_roleplay.travellers_map.mapping.mappers;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.chunk.IChunk;

public class VanillaMapper extends Mapper{
	public VanillaMapper() {
		super(new ResourceLocation(TravellersMap.MODID, "vanilla"));
	}

	@Override
	public void mapChunk(IBlockReader world, IChunk chunk, int[] colors) {
		//TODO Implement Vanilla Mapper
	}
}
