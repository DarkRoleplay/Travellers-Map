package net.dark_roleplay.travellers_map2.objects.screens.full_map;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class SettingsButton extends Button {

	public SettingsButton(int posX, int posY, IPressable callback) {
		super(posX, posY, 12, 12, "", callback);
	}

	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(FullMapScreen.FULL_MAP_TEXTURES);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		int i = this.isHovered() ? 12 : 0;
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		this.blit(this.x, this.y, 230, i, this.width, this.height);
		this.renderBg(minecraft, p_renderButton_1_, p_renderButton_2_);
	}
}
