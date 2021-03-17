package net.dark_roleplay.travellers_map.mapping.mappers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.chunk.IChunk;

public abstract class Mapper<T> {
	private final ResourceLocation mapperID;

	public Mapper(ResourceLocation mapperID) {
		this.mapperID = mapperID;
	}

	public ITextComponent getDisplayName(){
		return new TranslationTextComponent("mapper." + mapperID.getNamespace() + "." + mapperID.getPath());
	}

	public ResourceLocation getMapperID() {
		return mapperID;
	}

	public abstract void mapChunk(IBlockReader world, IChunk chunk, int[] colors);

}
