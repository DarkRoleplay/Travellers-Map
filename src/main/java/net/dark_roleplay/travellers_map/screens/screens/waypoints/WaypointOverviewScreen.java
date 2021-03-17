package net.dark_roleplay.travellers_map.screens.screens.waypoints;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.screens.screens.NestableScreen;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointOverviewScreen extends NestableScreen {
	public WaypointOverviewScreen() {
		super(new TranslationTextComponent("screen." + TravellersMap.MODID + ".waypoint_overview"));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderDirtBackground(0);

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
}
