package net.dark_roleplay.travellers_map.mapping;

import net.dark_roleplay.travellers_map.config.MapperConfigs;
import net.dark_roleplay.travellers_map.mapping.mappers.Mapper;
import net.dark_roleplay.travellers_map.mapping.mappers.TopographicMapper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.chunk.IChunk;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MappingManager {

	private static Map<Mapper, Queue<MappingTask>> MAPPER_QUEUE;
	private static Set<Mapper> ACTIVE_MAPPERS = new HashSet<>();

	private static ThreadPoolExecutor executor;

	static{
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MapperConfigs.THREAD_COUNT.get());
		ACTIVE_MAPPERS.add(new TopographicMapper());
	}

	public static void addMapper(Mapper mapper){
		ACTIVE_MAPPERS.add(mapper);
		MAPPER_QUEUE.computeIfAbsent(mapper, map -> new ConcurrentLinkedQueue<MappingTask>());
	}

	public static void registerNewChunkToMap(IBlockReader world, IChunk chunk){
		for(Mapper mapper : ACTIVE_MAPPERS)
			executor.execute(new MappingTask(mapper, world, chunk));
	}

	private static class MappingTask implements Runnable{
		private final Mapper mapper;
		private final IBlockReader world;
		private final IChunk chunk;
		private int[] result;

		public MappingTask(Mapper mapper, IBlockReader world, IChunk chunk) {
			this.mapper = mapper;
			this.world = world;
			this.chunk = chunk;
		}

		@Override
		public void run() {
			result = new int[256];
			mapper.mapChunk(world, chunk, result);
		}
	}
}
