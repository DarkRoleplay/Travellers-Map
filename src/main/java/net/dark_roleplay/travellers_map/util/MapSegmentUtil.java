package net.dark_roleplay.travellers_map.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.IChunk;

public class MapSegmentUtil {

	//func_233580_cy_ == player#getPosition

	public static long getSegment(PlayerEntity player, int offsetX, int offsetZ){
		return toSegment((player.getPosition().getX() + offsetX) >> 9, (player.getPosition().getZ() + offsetZ) >> 9);
	}

	public static long getSegment(IChunk chunk){
		return toSegment(chunk.getPos().x >> 5, chunk.getPos().z >> 5);
	}


	public static long getSegment(ChunkPos pos){
		return toSegment(pos.x >> 5, pos.z >> 5);
	}


	public static long getSegment(PlayerEntity player){
		return toSegment(player.getPosition().getX() >> 9, player.getPosition().getZ() >> 9);
	}

	public static long getSegment(BlockPos pos){
		return toSegment(pos.getX() >> 9, pos.getZ() >> 9);
	}

	public static long toSegment(int segX, int segZ){
		return ((long)segX & 0xFFFFFFFFL) << 32 | ((long)segZ & 0xFFFFFFFFL);
	}
}
