package net.dark_roleplay.travellers_map2.objects.screens;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.util.Wrapper;
import net.dark_roleplay.travellers_map2.objects.screens.full_map.FullMapScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class SidePanelButton extends Button {

	private Wrapper<Boolean> value;
	private IPressable additionalCallback;

	public SidePanelButton(int posX, int posY, Wrapper<Boolean> value, IPressable additionalCallback) {
		super(posX, posY, 11, 15, "", SidePanelButton::toggleActive);
		this.value = value;
		this.additionalCallback = additionalCallback;
	}

	private static void toggleActive(Button btn){
		if(btn instanceof SidePanelButton)
			((SidePanelButton)btn).value.set(!((SidePanelButton)btn).value.get());
		((SidePanelButton)btn).additionalCallback.onPress(btn);
	}

	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(FullMapScreen.FULL_MAP_TEXTURES);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		int x = this.isHovered() ? 11 : 0;
		int y = this.value.get() ? 15 : 0;
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		this.blit(this.x, this.y, 128 + x, y, this.width, this.height);
		this.renderBg(minecraft, p_renderButton_1_, p_renderButton_2_);
	}
}
