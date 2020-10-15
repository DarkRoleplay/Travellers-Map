package net.dark_roleplay.travellers_map.user_facing.huds;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.handler.TravellersKeybinds;
import net.minecraft.client.gui.AbstractGui;

public class ChargedKeybindProgress extends AbstractGui {

	protected int wWidth;
	protected int wHeight;

	public void setWindowSize(int width, int height) {
		this.wWidth = width;
		this.wHeight = height;
	}

	public void render(MatrixStack matrix, int mouseX, int mouseY, float delta){
		int time = TravellersKeybinds.OPEN_MAP2.getPressTime();
		if(time <= 0) return;

		this.fillGradient(matrix, wWidth/2 - 5, wHeight/2 + 2, wWidth/2 - 5 + time, wHeight/2 + 4, 0xFFFFFFFF, 0xFFFFFFFF);
	}
}
