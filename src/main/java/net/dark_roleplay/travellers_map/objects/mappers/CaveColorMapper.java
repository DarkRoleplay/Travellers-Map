package net.dark_roleplay.travellers_map.objects.mappers;

import net.dark_roleplay.travellers_map.mapping.Mapper;
import net.dark_roleplay.travellers_map.objects.color_palettes.ColorPalette;
import net.dark_roleplay.travellers_map.objects.color_palettes.DefaultColorPalette;
import net.dark_roleplay.travellers_map.util.MapperUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;

public class CaveColorMapper extends Mapper {
	public static Mapper INSTANCE = new CaveColorMapper(new DefaultColorPalette());

	private ColorPalette palette;
	private static int height = 96;

	public CaveColorMapper(ColorPalette palette){
		this.palette = palette;
	}

	@Override
	public void mapChunk(World world, IChunk chunk, NativeImage img) {
		ChunkPos chunkPos = chunk.getPos();
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockPos.Mutable worker = new BlockPos.Mutable();
		int x = chunkPos.x * 16, z = chunkPos.z * 16;
		for(int i = 0, x2 = x; i < 16; i++, x2++){
			for(int j = 0, z2 = z; j < 16; j++, z2++){
				BlockState firstBlock = MapperUtil.getFirstMappableBlock(world, pos.setPos(x2, height, z2), height, 0);

				if(firstBlock == null) continue;

				MaterialColor color = firstBlock.getMaterialColor(world, pos);
				if(color != null &&  (pos.getY() < height || (isVisible(chunk, pos, worker)))){
					int brightness = 1;
					if(world.getBlockState(pos.add(0, 1, -1)).getMaterialColor(world, pos) != MaterialColor.AIR)
						brightness--;
					if(world.getBlockState(pos.add(0, 0, -1)).getMaterialColor(world, pos) == MaterialColor.AIR)
						brightness++;

					float light = (0.04F * world.getLight(pos.add(0, 1, 0))) + 0.4F;
					int argb = palette.getRGBA(color.colorIndex, brightness);
					int red = (int) ((argb >> 16 & 0xFF) * light);
					int green = (int) ((argb >> 8 & 0xFF) * light);
					int blue = (int) ((argb & 0xFF) * light);
					img.setPixelRGBA(Math.floorMod(x2, 512), Math.floorMod(z2, 512), argb & 0xFF000000 | red << 16 | green << 8 | blue);
				}else{
					img.setPixelRGBA(Math.floorMod(x2, 512), Math.floorMod(z2, 512), 0xFF000000);
				}
			}
		}
	}

	@Override
	public int getMappingInterval() {
		return 1000;
	}

	@Override
	public int getMaxChunksPerRun() {
		return 10;
	}

	@Override
	public boolean canMapChunk(World world, IChunk chunk){
		return world.getChunk(chunk.getPos().x, chunk.getPos().z - 1, ChunkStatus.FULL, false) != null &&
				world.getChunk(chunk.getPos().x, chunk.getPos().z + 1, ChunkStatus.FULL, false) != null &&
				world.getChunk(chunk.getPos().x - 1, chunk.getPos().z, ChunkStatus.FULL, false) != null &&
				world.getChunk(chunk.getPos().x + 1, chunk.getPos().z, ChunkStatus.FULL, false) != null;
	}

	private boolean isVisible(IChunk chunk, BlockPos center, BlockPos.Mutable worker){
		if(chunk.getBlockState(worker.setPos(center.getX(), center.getY(), center.getZ() - 1)).getMaterialColor(Minecraft.getInstance().world, worker) == MaterialColor.AIR) return true;
		if(chunk.getBlockState(worker.setPos(center.getX() - 1, center.getY(), center.getZ())).getMaterialColor(Minecraft.getInstance().world, worker) == MaterialColor.AIR) return true;
		if(chunk.getBlockState(worker.setPos(center.getX(), center.getY(), center.getZ() + 1)).getMaterialColor(Minecraft.getInstance().world, worker) == MaterialColor.AIR) return true;
		if(chunk.getBlockState(worker.setPos(center.getX() + 1, center.getY(), center.getZ())).getMaterialColor(Minecraft.getInstance().world, worker) == MaterialColor.AIR) return true;
		return false;
	}
}
