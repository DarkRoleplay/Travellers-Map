package net.dark_roleplay.travellers_map.waypointer;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.StreamSupport;

public class WaypointSpriteAtlasHelper {
	private static final JsonParser PARSER = new JsonParser();

	public  static final AtlasTexture WAYPOINT_SPRITE_ATLAS = new AtlasTexture(new ResourceLocation("textures/atlas/waypoint_sprites.png"));

	private static final Set<WaypointMarker> MARKERS = new HashSet<>();

	public static void clientSetup(FMLClientSetupEvent event) {
		if (Minecraft.getInstance() == null) return; //Nop out, running DataGen

		if (Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager) {
			((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(WaypointSpriteAtlasHelper::reload);
		}
	}

	public static CompletableFuture<Void> reload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
		return CompletableFuture.<Void>supplyAsync(() -> {
			MARKERS.clear();

			try {
				Set<ResourceLocation> textures = new HashSet<>();
				resourceManager.getAllResources(new ResourceLocation(TravellersMap.MODID, "travellers_map/waypoint_markers.json")).stream()
						.map(resource -> new JsonReader(new InputStreamReader(resource.getInputStream())))
						.map(reader -> (JsonObject) PARSER.parse(reader))
						.map(json -> JSONUtils.getJsonArray(json, "markerTypes", new JsonArray()))
						.flatMap(jsonArray -> StreamSupport.stream(jsonArray.spliterator(), false))
						.map(json -> new WaypointMarker((JsonObject) json))
						.forEach(marker -> {
							marker.gatherTextures(textures);
							MARKERS.add(marker);
						});

				WAYPOINT_SPRITE_ATLAS.stitch(resourceManager, textures.stream(), reloadProfiler, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}).thenCompose(stage::markCompleteAwaitingOthers);
	}
}
