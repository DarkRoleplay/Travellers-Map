package net.dark_roleplay.travellers_map.user_facing.huds;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
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
		int time = (int) Math.floor(11 * TravellersKeybinds.OPEN_MAP.getPressProgress());

		TravellersKeybinds.OPEN_MAP.processChargedPress();
		if(time <= 0) return;

		int cX = wWidth/2;
		int cY = wHeight/2;

		RenderSystem.defaultBlendFunc();
		RenderSystem.enableBlend();
		this.fillGradient(matrix, cX - 7, cY + 8, cX + 6, cY + 13, 0x80000000, 0x80000000);
		this.fillGradient(matrix, cX - 6, cY + 9, cX - 6 + time, cY + 12, 0x80FFFFFF, 0x80FFFFFF);
		RenderSystem.disableBlend();
	}
}
