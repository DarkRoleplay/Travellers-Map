package net.dark_roleplay.travellers_map.debug;

import net.dark_roleplay.travellers_map.backend.waypoints.Waypoint;
import net.dark_roleplay.travellers_map.frontend.screens.NestableScreen;
import net.dark_roleplay.travellers_map.frontend.screens.widgets.ColorPickerWidget;
import net.dark_roleplay.travellers_map.frontend.screens.widgets.WaypointDisplayWidget;
import net.dark_roleplay.travellers_map.frontend.screens.widgets.ScrollPanel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class DebugScreen extends NestableScreen {

	protected DebugScreen() {
		super(new StringTextComponent("Lorem Ipsum"));
	}

	protected void init() {
		this.renderChildren.clear();

		ColorPickerWidget colorPicker = new ColorPickerWidget(this.width/2 + 74, this.height/2 - 64, this.font);
		addChild(colorPicker);

		addChild(new ScrollPanel(minecraft, 128, 128, 50, 50));

		addChild(new WaypointDisplayWidget(this.width/2 - 100, this.height/2 - 50, new Waypoint("New Waypoint", null, 0xFFFFFF, null, new BlockPos(0, 0, 0)), this.font));
	}

}