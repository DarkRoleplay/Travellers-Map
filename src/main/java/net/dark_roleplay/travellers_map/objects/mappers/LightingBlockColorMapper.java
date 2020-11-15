package net.dark_roleplay.travellers_map.objects.mappers;

import net.dark_roleplay.travellers_map.mapping.Mapper;
import net.dark_roleplay.travellers_map.objects.color_palettes.ColorPalette;
import net.dark_roleplay.travellers_map.objects.color_palettes.DefaultColorPalette;
import net.dark_roleplay.travellers_map.objects.color_palettes.MonoColorPalette;
import net.dark_roleplay.travellers_map.util.MapperUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;

public class LightingBlockColorMapper extends Mapper {
	public static Mapper INSTANCE = new LightingBlockColorMapper(new DefaultColorPalette());

	private ColorPalette palette;

	public LightingBlockColorMapper(ColorPalette palette){
		this.palette = palette;
	}

	@Override
	public void mapChunk(World world, IChunk chunk, NativeImage img) {
		ChunkPos chunkPos = chunk.getPos();
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockPos.Mutable fluidPos = new BlockPos.Mutable();
		int x = chunkPos.x * 16, z = chunkPos.z * 16;
		for(int i = 0, x2 = x; i < 16; i++, x2++){
			for(int j = 0, z2 = z; j < 16; j++, z2++){
				int y = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, x2, z2);
				BlockState firstBlock = MapperUtil.getFirstMappableBlock(world, pos.setPos(x2, y, z2), y, 0);

				if(firstBlock == null) continue;

				MaterialColor color = firstBlock.getMaterialColor(world, pos);
				if(color != null){

					int brightness;
					if(!firstBlock.getFluidState().isEmpty() && firstBlock.getFluidState().getFluid() == Fluids.WATER){
						brightness = 2;
						boolean isPrimary = (i + j) % 2 == 1;

						int depth = MapperUtil.getFluidDepth(world, fluidPos.setPos(pos));
						if(depth >= 10 || (depth >= 7 && isPrimary))
							brightness = 0;
						else if(depth >= 5 || depth >= 3 && isPrimary)
							brightness = 1;
					}else{
						brightness = 1;
						if(world.getBlockState(pos.add(0, 1, -1)).getMaterialColor(world, pos) != MaterialColor.AIR)
							brightness--;
						if(world.getBlockState(pos.add(0, 0, -1)).getMaterialColor(world, pos) == MaterialColor.AIR)
							brightness++;
					}

					int tintColor;
					if((tintColor = Minecraft.getInstance().getBlockColors().getColor(firstBlock, world, pos, 0)) != -1){
						img.setPixelRGBA(Math.floorMod(x2, 512), Math.floorMod(z2, 512), mediateARGB(palette.getRGBA(color.colorIndex, brightness), 0xFF000000 | tintColor)); //TODO add brightness handling
					}else{
						img.setPixelRGBA(Math.floorMod(x2, 512), Math.floorMod(z2, 512), (palette.getRGBA(color.colorIndex, brightness)));
					}
				}
			}
		}
	}

	public int mediateARGB(int c1, int c2){
		int r = (c1 & 0xFF0000) >> 16;
		int g = (c1 & 0x00FF00) >> 8;
		int b = (c1 & 0x0000FF);

		float rm = ((c2 & 0xFF0000) >> 16) / 255F;
		float gm = ((c2 & 0x00FF00) >> 8) / 255F;
		float bm = ((c2 & 0x0000FF)) / 255F;

		int r1 = (int) (r * bm);
		int g1 = (int) (g * gm);
		int b1 = (int) (b * rm);

		return 0xFF000000 | r1 << 16 | g1 << 8 | b1;
	}

	@Override
	public int getMappingInterval() {
		return 1000;
	}

	@Override
	public int getMaxChunksPerRun() {
		return 20;
	}

	@Override
	public boolean canMapChunk(World world, IChunk chunk){
		return world.getChunk(chunk.getPos().x, chunk.getPos().z - 1, ChunkStatus.FULL, false) != null &&
				world.getChunk(chunk.getPos().x, chunk.getPos().z + 1, ChunkStatus.FULL, false) != null &&
				world.getChunk(chunk.getPos().x - 1, chunk.getPos().z, ChunkStatus.FULL, false) != null &&
				world.getChunk(chunk.getPos().x + 1, chunk.getPos().z, ChunkStatus.FULL, false) != null;
	}
}
