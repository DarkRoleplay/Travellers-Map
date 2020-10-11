package net.dark_roleplay.travellers_map.waypointer.icons;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mojang.serialization.JsonOps;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class WaypointIcons {

	public static Map<ResourceLocation, WaypointIcon> ICONS = new HashMap<>();

	public static WaypointIcon getWaypointIcon(ResourceLocation location){
		return ICONS.get(location);
	}

	public static final JsonParser PARSER = new JsonParser();

	public static CompletableFuture<Void> resourceReload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
		//TODO Add Error Handling at the end

		ICONS.clear();
		CompletableFuture<WaypointIcon>[] futures =
				resourceManager
						.getAllResourceLocations("travellers_map/waypoint_icons", fileName -> fileName.endsWith(".json"))
						.stream()
						.map(location ->
								CompletableFuture.<IResource>supplyAsync(() -> {
									try {
										return resourceManager.getResource(location);
									} catch (IOException e) {
										e.printStackTrace();
									}
									return null;
								})
						)
						.map(future ->
								future
										.thenApplyAsync(resource -> new JsonReader(new InputStreamReader(resource.getInputStream())))
										.thenApplyAsync(reader -> PARSER.parse(reader))
										.thenApplyAsync(jsonElement -> WaypointIcon.CODEC.decode(JsonOps.INSTANCE, jsonElement))
										.thenApplyAsync(dataPair -> dataPair.getOrThrow(false, TravellersMap.LOG::error).getFirst())
										.thenAccept(icon -> ICONS.put(icon.getRegistryName(), icon))
						)
						.<CompletableFuture<WaypointIcon>>toArray(count -> new CompletableFuture[count]);

		return CompletableFuture.allOf(futures).thenCompose(stage::markCompleteAwaitingOthers);
	}
}
