package net.dark_roleplay.travellers_map.user_facing.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.objects.style.HudStyle;
import net.dark_roleplay.travellers_map.objects.style.HudStyleProvider;
import net.dark_roleplay.travellers_map.util.BlendBlitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class StyleChoser extends Screen {

	private HudStyleProvider styleProvider;
	private int xOffset;

	private int currentStyle;
	private List<HudStyle> styles;
	private Button setStyleBtn;


	public StyleChoser(int xOffset, HudStyleProvider styleProvider) {
		super(new TranslationTextComponent("travellers_map.style_chooser"));
		this.xOffset = xOffset;
		this.styleProvider = styleProvider;
		this.styles = new ArrayList(styleProvider.getStyles());
		this.currentStyle = styles.indexOf(styleProvider.getActiveStyle());
	}

	@Override
	public void init(){
		int trueWidth = this.width - this.xOffset;
		int centerX = this.width/2;
		if(centerX - 100 < this.xOffset)
			centerX += this.xOffset - (centerX - 100);

		this.addButton(setStyleBtn = new Button(centerX - Math.min(100, trueWidth/2), this.height - 30, Math.min(200, trueWidth), 20, new StringTextComponent("SELECT"), btn -> {
			this.styleProvider.setActiveStyle(this.styles.get(this.currentStyle));
			this.setStyleBtn.active = false;
		}));
		this.setStyleBtn.active = this.styles.get(this.currentStyle) != this.styleProvider.getActiveStyle();

		this.addButton(new Button(centerX - Math.min(100, trueWidth/2), 10, 20, 20, new StringTextComponent("<"), btn -> {
			this.currentStyle = this.currentStyle > 0 ? this.currentStyle -1 : this.styles.size() - 1;
			this.setStyleBtn.active = this.styles.get(this.currentStyle) != this.styleProvider.getActiveStyle();
		}));
		this.addButton(new Button(centerX + Math.min(90, trueWidth/2 - 10), 10, 20, 20, new StringTextComponent(">"), btn -> {
			this.currentStyle = this.currentStyle < this.styles.size() - 1 ? this.currentStyle + 1 : 0;
			this.setStyleBtn.active = this.styles.get(this.currentStyle) != this.styleProvider.getActiveStyle();
		}));
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
		int centerX = this.width/2;
		if(centerX - 100 < this.xOffset)
			centerX += this.xOffset - (centerX - 100);
		int centerY = this.height/2;

		this.renderBackground(matrix);

		super.render(matrix, mouseX, mouseY, delta);

		HudStyle currentStyle = this.styles.get(this.currentStyle);

		drawCenteredString(matrix, this.font, currentStyle.getStyleName(), centerX, 16, 0xFFFFFFFF);

		Minecraft.getInstance().getTextureManager().bindTexture(currentStyle.getOverlay());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		BlendBlitHelper.blit(centerX - currentStyle.getWidth()/2, centerY - currentStyle.getHeight()/2, currentStyle.getWidth(), currentStyle.getHeight(), 0, 0, 1, 1, 1, 1);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
	}
}

