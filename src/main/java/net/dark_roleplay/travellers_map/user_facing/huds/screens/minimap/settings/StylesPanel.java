package net.dark_roleplay.travellers_map.user_facing.huds.screens.minimap.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.util.BlendBlitHelper;
import net.dark_roleplay.travellers_map.user_facing.huds.hud.Hud;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.objects.style.HudStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.gui.ScrollPanel;

public class StylesPanel extends ScrollPanel {

	private int spacing = 5;
	private Hud hud;

	public StylesPanel(Minecraft client, int x, int y, int width, int height, Hud hud) {
		super(client, width, height, x, y);
		this.hud = hud;
	}

	@Override
	protected int getContentHeight() {
		int height = 0;
		for(HudStyle style : this.hud.getStyleProvider().getStyles())
			height += style.getHeight() + spacing;
		return height;
	}

	@Override
	protected void drawPanel(MatrixStack matrix, int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {
		int offset = 5;
		for(HudStyle style : this.hud.getStyleProvider().getStyles()){
			int elemTop = (int)(this.top + offset - this.scrollDistance);
			offset += style.getHeight() + spacing;
			if(elemTop + style.getHeight() < this.top || elemTop > this.top + this.height) continue;

			Minecraft.getInstance().getTextureManager().bindTexture(style.getOverlay());

			if(mouseX >= this.left && mouseX <= this.right  - 6 && mouseY >= elemTop - 2 && mouseY <= elemTop + style.getHeight() + 2){
				fill(matrix, this.left, elemTop - 2, this.right, elemTop + style.getHeight() + 2, 0xFF202020);
			}

			BlendBlitHelper.blit(this.left + (this.width - style.getWidth()) / 2, elemTop, style.getWidth(), style.getHeight(), 0, 0, 1, 1, 1, 1);
		}
	}


	@Override
	protected boolean clickPanel(double mouseX, double mouseY, int button) {
		int offset = 5;
		for(HudStyle style : this.hud.getStyleProvider().getStyles()) {
			int elemTop = (int) (this.top + offset - this.scrollDistance);
			offset += style.getHeight() + spacing;
			if (elemTop + style.getHeight() < this.top || elemTop > this.top + this.height) continue;

			if(mouseX >= this.left && mouseX <= this.right  - 6 && mouseY >= elemTop - 2 && mouseY <= elemTop + style.getHeight() + 2){
				this.hud.getStyleProvider().setActiveStyle(style);
				Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				TravellersMap.LOG.info("Style set to {}.", style.getStyleName());
				return true;
			}
		}
		return false;
	}
}
