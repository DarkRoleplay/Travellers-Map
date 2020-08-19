package net.dark_roleplay.travellers_map.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.objects.map_layers.MapLayer;
import net.dark_roleplay.travellers_map.objects.map_layers.PlayerMarkerLayer;
import net.dark_roleplay.travellers_map.objects.map_layers.WaypointLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Quaternion;

import java.util.ArrayList;
import java.util.List;

public class MapRenderer {

	private static List<IMapLayer> mapLayers = new ArrayList<>();

	static{
		mapLayers.add(new MapLayer());
		mapLayers.add(new PlayerMarkerLayer());
		mapLayers.add(new WaypointLayer());
	}

	public static void renderMap(MatrixStack matrix, MapRenderInfo renderInfo, MapType mapType, boolean rotate, float delta){
		matrix.push();
		matrix.translate(renderInfo.getWidth()/2F, renderInfo.getHeight()/2F, 0);
		matrix.scale(renderInfo.getScale(), renderInfo.getScale(), renderInfo.getScale());
		if(rotate){
			float yaw = -(float) Math.toRadians(Minecraft.getInstance().player.getYaw(delta) - 180) /2F;
			matrix.rotate(new Quaternion(0, 0, (float)Math.sin(yaw), (float)Math.cos(yaw)));
		}

		mapLayers
				.stream()
				.filter(layer -> layer.isEnabled() && layer.canRenderIn(mapType))
				.forEachOrdered(layer -> layer.renderLayer(matrix, renderInfo, mapType, rotate, delta));

		matrix.pop();
	}
}
