package net.dark_roleplay.travellers_map.screens.screens.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.waypoints.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointDisplayWidget extends NestedWidget {

	private Waypoint waypoint;
	private FontRenderer font;

	public WaypointDisplayWidget(int x, int y, Waypoint waypoint, FontRenderer font) {
		super(x, y, 128, 21);
		this.waypoint = waypoint;
		this.font = font;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		fill(matrixStack, this.posX, this.posY, this.posX + this.width, this.posY + this.height, 0xFF808080);
		fill(matrixStack, this.posX + 2, this.posY + 2, this.posX + 18, this.posY + 18, 0xFFFF0000);

		font.drawStringWithShadow(matrixStack, waypoint.getName(), this.posX + 20, this.posY + 2, 0xFFFFFFFF);
		TranslationTextComponent comp = new TranslationTextComponent("screen." + TravellersMap.MODID + ".waypoint_display.distance", (int) waypoint.getDistance(Minecraft.getInstance().player.getPosition()));
		font.func_243246_a(matrixStack, comp, this.posX + 20, this.posY + 11, 0xFFD0D0D0);
	}
}
