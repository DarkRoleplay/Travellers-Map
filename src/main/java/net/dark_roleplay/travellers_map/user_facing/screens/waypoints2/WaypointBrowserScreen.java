package net.dark_roleplay.travellers_map.user_facing.screens.waypoints2;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Set;

public class WaypointBrowserScreen extends Screen {


	protected WaypointBrowserScreen() {
		super(new TranslationTextComponent("screen." + TravellersMap.MODID + ".waypoint_browser"));
	}

	@Override
	protected void init() {
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {

	}
}
