package net.dark_roleplay.travellers_map.user_facing.screens.minimap.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.util.BlendBlitHelper;
import net.dark_roleplay.travellers_map.configs.ClientConfig;
import net.dark_roleplay.travellers_map.user_facing.huds.GuiAlignment;
import net.dark_roleplay.travellers_map.user_facing.huds.hud.Hud;
import net.dark_roleplay.travellers_map.objects.style.HudStyle;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;

public class MinimapMover extends Widget {

	private Hud hud;

	private int posX;
	private int posY;

	private double initOffsetX = 0;
	private double initOffsetY = 0;
	private double scale = 0;

	public MinimapMover(Hud hud) {
		super(0, 0, 200, 20, new TranslationTextComponent(hud.getUnlocalizedName() + ".mover"));
		this.hud = hud;

		MainWindow window = Minecraft.getInstance().getMainWindow();
		GuiAlignment alignment = ClientConfig.MINIMAP.ALIGNMENT.get();
		this.posX = ClientConfig.MINIMAP.POS_X.get() + alignment.getX(window.getScaledWidth());
		this.posY = ClientConfig.MINIMAP.POS_Y.get() + alignment.getY(window.getScaledHeight());
		this.scale = ClientConfig.MINIMAP.SCALE.get();
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		initOffsetX = posX - mouseX;
		initOffsetY = posY - mouseY;
	}

	@Override
	public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		HudStyle style = this.hud.getStyleProvider().getActiveStyle();
		posX = (int)Math.min(Math.max(0, mouseX + initOffsetX), Minecraft.getInstance().getMainWindow().getScaledWidth() - Math.floor(style.getWidth() * this.scale));
		posY = (int)Math.min(Math.max(0, mouseY + initOffsetY), Minecraft.getInstance().getMainWindow().getScaledHeight() - Math.floor(style.getHeight() * this.scale));
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta){
		this.scale = Math.max(0.25, Math.min(4, scale + (delta * 0.1)));
		return true;
	}

	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float delta) {
		MainWindow window = Minecraft.getInstance().getMainWindow();
		HudStyle style = this.hud.getStyleProvider().getActiveStyle();

		this.width = (int)(style.getWidth() * this.scale);
		this.height = (int)(style.getWidth() * this.scale);

		this.x = posX;
		this.y = posY;

		RenderSystem.pushMatrix();
		RenderSystem.translatef(x, y, 0);
		RenderSystem.scaled(this.scale, this.scale, 1);
		Minecraft.getInstance().getTextureManager().bindTexture(style.getOverlay());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		BlendBlitHelper.blit(0, 0, style.getWidth(), style.getHeight(), 0, 0, 1, 1, 1, 1);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		RenderSystem.popMatrix();
	}

	public void onClose() {MainWindow window = Minecraft.getInstance().getMainWindow();
		int windowWidth = window.getScaledWidth();
		int windowHeight = window.getScaledHeight();
		GuiAlignment alignment;

		if(posX < windowWidth/3)
			alignment = posY < windowHeight/3 ?
					GuiAlignment.TOP_LEFT : posY < (windowHeight/3) * 2 ?
					GuiAlignment.LEFT : GuiAlignment.BOTTOM_LEFT;
		else if(posX < (windowWidth/3)*2)
			alignment = posY < windowHeight/3 ?
					GuiAlignment.TOP : posY < (windowHeight/3) * 2 ?
					GuiAlignment.CENTER : GuiAlignment.BOTTOM;
		else
			alignment = posY < windowHeight/3 ?
					GuiAlignment.TOP_RIGHT : posY < (windowHeight/3) * 2 ?
					GuiAlignment.RIGHT : GuiAlignment.BOTTOM_RIGHT;


		int newX = posX - alignment.getX(window.getScaledWidth());
		int newY = posY - alignment.getY(window.getScaledHeight());

		ClientConfig.MINIMAP.SCALE.set(this.scale);
		ClientConfig.MINIMAP.ALIGNMENT.set(alignment);
		ClientConfig.MINIMAP.POS_X.set(newX);
		ClientConfig.MINIMAP.POS_Y.set(newY);
	}
}
