package net.dark_roleplay.travellers_map.user_facing.screens.waypoints;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.objects.waypoints.Waypoint;
import net.dark_roleplay.travellers_map.util.MapManager;
import net.dark_roleplay.travellers_map.user_facing.screens.full_map.FullMapScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.gui.ScrollPanel;

public class WaypointScrollPanel extends ScrollPanel {

	private Screen parent;
	private static int elementHeight = 24;

	public WaypointScrollPanel(Minecraft client, Screen parent, int width, int height, int top, int left) {
		super(client, width, height, top, left);
		this.parent = parent;
	}

	@Override
	protected int getContentHeight() {
		return Math.max(this.height - 4, MapManager.WAYPOINTS.size() * elementHeight);
	}

	@Override
	protected void drawPanel(MatrixStack matrix, int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {
		int offset = 0;
		for(Waypoint waypoint : MapManager.WAYPOINTS){
			int elemTop = (int)(this.top + 3 + offset - this.scrollDistance);
			if(elemTop + elementHeight < this.top ||
					elemTop > this.top + this.height){
				offset += elementHeight;
				continue;
			}

			if(mouseX >= this.left && mouseX <= this.right  - 6 && mouseY >= elemTop && mouseY <= elemTop + elementHeight){
				fill(matrix, this.left, elemTop, this.right, elemTop + elementHeight, 0xFF202020);
			}

			FontRenderer renderer = Minecraft.getInstance().fontRenderer;
			renderer.drawString(matrix, waypoint.getName(), this.left + 3, elemTop + 3, waypoint.getColor());
			renderer.drawString(matrix, String.format("x: %d, y: %d, z: %d", waypoint.getPos().getX(), waypoint.getPos().getY(), waypoint.getPos().getZ()), this.left + 3, elemTop + 13, 0xFFA0A0A0);

			Minecraft.getInstance().getTextureManager().bindTexture(FullMapScreen.FULL_MAP_TEXTURES);
			blit(matrix, this.right - 21, elemTop + 3, 14, 7, 242, waypoint.isVisible() ? 0 : 7, 14, 7, 256, 256);

			offset += elementHeight;
		}
	}

	@Override
	protected boolean clickPanel(double mouseX, double mouseY, int button) {
		int offset = 0;
		for(Waypoint waypoint : MapManager.WAYPOINTS) {
			int elemTop = (int)(offset);
			if(elemTop + elementHeight < this.top || elemTop > this.top + this.height){
				offset += elementHeight;
				continue;
			}

			if(mouseY >= elemTop && mouseY <= elemTop + elementHeight){
				if(button == 0)
					waypoint.toggleVisible();
				else if(button == 1)
					Minecraft.getInstance().displayGuiScreen(new WayPointCreationScreen(this.parent, waypoint));
				return true;
			}
			offset += elementHeight;
		}
		return true;
	}
}
