package net.dark_roleplay.travellers_map.mapping;

import net.dark_roleplay.travellers_map.objects.mappers.LightingColorMapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.ChunkPos;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MappingHelper {

	private static final Set<MapperQueue> activeMappers = new HashSet<>();

	private static Timer chunkMapper;

	public static void initMapper(){
		if(chunkMapper != null) return;
		activeMappers.add(new MapperQueue());

		chunkMapper = new Timer("TravellersMap - Chunk Mapper", true);
		chunkMapper.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(Minecraft.getInstance().world == null){
					killMapper();
					return;
				}
				for(MapperQueue mapper : activeMappers)
					mapper.processLoadedChunksQueue();
			}
		}, 250, 250);

		for(MapperQueue mapper : activeMappers)
			mapper.initTimer(chunkMapper);
	}

	public static void killMapper(){
		chunkMapper.cancel();
		chunkMapper = null;
		for(MapperQueue mapper : activeMappers)
			mapper.stopMapper();
		activeMappers.clear();
	}

	public static void scheduleLoadedChunk(ChunkPos pos){
		for(MapperQueue mapper : activeMappers)
			mapper.scheduleLoadedChunk(pos);
	}

	public static void schedulePeriodicalChunk(ChunkPos pos){
		for(MapperQueue mapper : activeMappers)
			mapper.schedulePeriodicalChunk(pos);
	}
}
