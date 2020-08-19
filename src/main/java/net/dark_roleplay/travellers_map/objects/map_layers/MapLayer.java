package net.dark_roleplay.travellers_map.objects.map_layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.mapping.IMapSegmentTicket;
import net.dark_roleplay.travellers_map.rendering.IMapLayer;
import net.dark_roleplay.travellers_map.rendering.MapType;
import net.dark_roleplay.travellers_map.rendering.MapRenderInfo;
import net.dark_roleplay.travellers_map.objects.tickets.RenderTicket;
import net.dark_roleplay.travellers_map.util.MapManager;
import net.dark_roleplay.travellers_map.util.MapSegment;
import net.dark_roleplay.travellers_map.util2.DataController;
import net.minecraft.client.gui.AbstractGui;

public class MapLayer extends IMapLayer {

	public MapLayer() {
		super(false);
	}

	@Override
	public void renderLayer(MatrixStack matrix, MapRenderInfo renderInfo, MapType mapType, boolean isRotated, float delta) {
		Long[][] maps = renderInfo.getSegments();

		for(int x = 0; x < maps.length; x++){
			for(int z = 0; z < maps[x].length; z++){
				MapSegment map = DataController.getCurrentMapSegmentProvider().getMapSegment(maps[x][z]);
				if(map.isEmpty()) continue;

				IMapSegmentTicket ticket = RenderTicket.getOrCreateTicket(map.getSegX(), map.getSegZ());
				map.addTicket(ticket);

				map.updadteGPU();

				drawSegment(matrix, map, renderInfo.getOffsetX() + (x * 512), renderInfo.getOffsetZ() + (z * 512));
			}
		}
	}

	private static void drawSegment(MatrixStack matrix, MapSegment map, int offsetX, int offsetZ){
		if(map != null && !map.isEmpty()) {
			map.getDynTexture().bindTexture();
			AbstractGui.blit(matrix, offsetX, offsetZ, 512, 512, 0, 0, 1, 1, 1, 1);
		}
	}
}
