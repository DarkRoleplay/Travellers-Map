package net.dark_roleplay.travellers_map.mapping;

import net.dark_roleplay.travellers_map.mapping.mappers.Mapper;
import net.dark_roleplay.travellers_map.mapping.tickets.ChunkLoadedTicket;
import net.dark_roleplay.travellers_map.mapping.tickets.IMapSegmentTicket;
import net.dark_roleplay.travellers_map.util.MapManager;
import net.dark_roleplay.travellers_map.util.MapSegment;
import net.minecraft.client.Minecraft;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.AbstractQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MapperQueue {

	private final Mapper mapper;

	private final AbstractQueue<ChunkPos> newChunksQueue = new ConcurrentLinkedQueue<>();
	private final AbstractQueue<ChunkPos> periodicalChunksQueue = new ConcurrentLinkedQueue<>();

	private TimerTask periodicTask ;

	public MapperQueue(Mapper mapper){
		this.mapper = mapper;
	}

	public void stopMapper(){
		newChunksQueue.clear();
		periodicalChunksQueue.clear();
	}

	//TODO Implement PeriodicalChunkQueue
	public void initTimer(Timer timer){}

	public void scheduleLoadedChunk(ChunkPos pos){
		newChunksQueue.add(pos);
	}

	public void schedulePeriodicalChunk(ChunkPos pos){
		periodicalChunksQueue.add(pos);
	}

	public void processLoadedChunksQueue(){
		if(Minecraft.getInstance().world == null){
			MappingHelper.killMapper();
			return;
		}
		enqueToMainThread(() -> {
			World world = Minecraft.getInstance().world;
			for(int i = 0; i < mapper.getMaxChunksPerRun() && !newChunksQueue.isEmpty(); i++){
				ChunkPos pos = newChunksQueue.remove();
				if(world.chunkExists(pos.x, pos.z)){
					Chunk chunk = world.getChunk(pos.x, pos.z);
					if(mapper.canMapChunk(world, chunk)){
						IMapSegmentTicket ticket = ChunkLoadedTicket.loadChunk(chunk);
						MapSegment segment = MapManager.getOrCreateMapSegment(chunk, ticket);
						if(segment == null) return;
						segment.updateChunk(world, chunk);
						segment.markDirty();
					}else{
						newChunksQueue.add(pos);
					}
				}else{
					i--;
				}
			}
		});
	}

	private static void enqueToMainThread(Runnable runnable){
		ThreadTaskExecutor<?> executor = LogicalSidedProvider.WORKQUEUE.get(LogicalSide.CLIENT);
		// Must check ourselves as Minecraft will sometimes delay tasks even when they are received on the client thread
		// Same logic as ThreadTaskExecutor#runImmediately without the join
		if (!executor.isOnExecutionThread()) {
			executor.deferTask(runnable); // Use the internal method so thread check isn't done twice
		} else {
			runnable.run();
		}
	}
}
