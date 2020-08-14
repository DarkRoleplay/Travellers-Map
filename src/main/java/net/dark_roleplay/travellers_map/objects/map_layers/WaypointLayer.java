package net.dark_roleplay.travellers_map.objects.map_layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.rendering.IMapLayer;
import net.dark_roleplay.travellers_map.rendering.MapType;
import net.dark_roleplay.travellers_map.rendering.MapRenderInfo;
import net.dark_roleplay.travellers_map.util.MapManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;

public class WaypointLayer extends IMapLayer {
	public WaypointLayer() {
		super(true);
	}

	private static ResourceLocation WAYPOINT_ICONS = new ResourceLocation(TravellersMap.MODID, "textures/guis/waypoint_icons.png");

	@Override
	public void renderLayer(MatrixStack matrix, MapRenderInfo renderInfo, MapType mapType, boolean isRotated, float delta) {
		float posX = renderInfo.getCenterX() - renderInfo.getScaledWidth() / 2 - 10;
		float posX2 = renderInfo.getCenterX() + renderInfo.getScaledWidth() / 2 + 10;
		float posZ = renderInfo.getCenterZ() - renderInfo.getScaledHeight() / 2 - 10;
		float posZ2 = renderInfo.getCenterZ() + renderInfo.getScaledHeight() / 2 + 10;

		MapManager.WAYPOINTS
				.stream()
				.filter(waypoint -> waypoint.isVisible())
				.filter(waypoint ->
						waypoint.getPos().getX() > posX && waypoint.getPos().getX() < posX2 &&
								waypoint.getPos().getZ() > posZ && waypoint.getPos().getZ() < posZ2
				)
				.forEach(waypoint -> {
					double waypointX = waypoint.getPos().getX() - renderInfo.getCenterX();
					double waypointZ = waypoint.getPos().getZ() - renderInfo.getCenterZ();
					boolean isHovered = isHovered(renderInfo, waypointX, waypointZ);

					matrix.push();
					matrix.translate(waypointX, waypointZ, 0);
					if (isRotated) {
						float yaw = (float) Math.toRadians(Minecraft.getInstance().player.getYaw(delta) - 180) / 2F;
						matrix.rotate(new Quaternion(0, 0, (float) Math.sin(yaw), (float) Math.cos(yaw)));
					}

					RenderSystem.color3f(1f, 0F, 0F);
					if(isHovered)
						RenderSystem.color3f(0.5f, 0.5F, 0.8F);

					Minecraft.getInstance().getTextureManager().bindTexture(WAYPOINT_ICONS);
					blit(matrix, -3, -11, 7, 12, 0, 12, 7, 12, 140, 120);
					RenderSystem.color3f(1f, 1F, 1F);

					matrix.translate(-waypointX-renderInfo.getScaledWidth()/2, -waypointZ-renderInfo.getScaledHeight()/2, 0);
//					if(isHovered)
//						//TODO Reimplement TOOLTIP
//						GuiUtils.drawHoveringText(
//								matrix,
//								new ArrayList() {{add(new StringTextComponent(waypoint.getName()));}},
//								(int) renderInfo.getScaledMouseX() + renderInfo.getScaledWidth()/2,
//								(int) renderInfo.getScaledMouseY() + renderInfo.getScaledHeight()/2,
//								(int) renderInfo.getScaledWidth(),
//								(int) renderInfo.getScaledHeight(),
//								renderInfo.getScaledWidth(),
//								Minecraft.getInstance().fontRenderer
//						);

					matrix.pop();
				});
	}

	private boolean isHovered(MapRenderInfo renderInfo, double waypointX, double waypointZ){
		if(!renderInfo.hasMouse()) return false;
		return waypointX - 1 < renderInfo.getScaledMouseX() && waypointX + 2 > renderInfo.getScaledMouseX()
				&& waypointZ - 9 < renderInfo.getScaledMouseY() && waypointZ > renderInfo.getScaledMouseY();
	}
}
