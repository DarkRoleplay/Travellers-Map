package net.dark_roleplay.travellers_map.screens.screens.waypoints;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.waypoints.Waypoint;
import net.dark_roleplay.travellers_map.screens.screens.NestableScreen;
import net.dark_roleplay.travellers_map.screens.screens.widgets.WaypointCreationWidget;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointCreationScreen extends NestableScreen {

	private Waypoint waypoint;
	private WaypointCreationWidget waypointWidget;

	public WaypointCreationScreen(Waypoint waypoint) {
		super(new TranslationTextComponent("screen." + TravellersMap.MODID + ".waypoint_creation"));
		this.waypoint = waypoint.shallowClone();
	}

	@Override
	protected void init() {
		this.renderChildren.clear();
		if(waypointWidget == null)
			waypointWidget = new WaypointCreationWidget((this.width-147)/2, (this.height-104)/2, this.waypoint, this.font, this::closeScreen, this::handleSuccess);
		addChild(waypointWidget);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	private void handleSuccess(){
		this.closeScreen();
		//TODO Create Waypoint Object and register it to current session
	}
}
