package net.dark_roleplay.travellers_map.objects.screens.minimap.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.objects.screens.StyleChoser;
import net.dark_roleplay.travellers_map.util.Wrapper;
import net.dark_roleplay.travellers_map.objects.huds.minimap.MinimapHUD;
import net.dark_roleplay.travellers_map.objects.screens.SidePanelButton;
import net.dark_roleplay.travellers_map.objects.screens.full_map.FullMapScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.gui.ScrollPanel;

public class MinimapSettingsScreen extends Screen {

	//private ScrollPanel stylePanel;
	private Screen parentScreen;
	private MinimapMover mover;
	private StyleChoser styleChoser;

	private Wrapper<Boolean> isStlyeListOpen = new Wrapper(false);

	public MinimapSettingsScreen(Screen parentScreen) {
		super(new TranslationTextComponent("screen.travellers_map.minimap_settings"));
		this.parentScreen = parentScreen;
	}


	@Override
	public void init(Minecraft mc, int width, int height) {
		super.init(mc, width, height);

		this.styleChoser = new StyleChoser(128, MinimapHUD.INSTANCE.getStyleProvider());
		styleChoser.init(mc, width, height);
		if(isStlyeListOpen.get()){
			this.addListener(styleChoser);
		}
	}

	@Override
	protected void init() {
		if(mover != null) mover.onClose();
		//stylePanel = new StylesPanel(this.minecraft, 5, 5, 118, this.height - 10, MinimapHUD.INSTANCE);
		this.addButton(mover = new MinimapMover(MinimapHUD.INSTANCE));
		this.addButton(new SidePanelButton(-2, (this.height - 23) / 2, isStlyeListOpen, btn -> {
			if(isStlyeListOpen.get()){
				//this.children.add(stylePanel);
				this.addListener(styleChoser);
				//btn.x = 125;
			}else{
				//this.buttons.remove(stylePanel);
				this.children.remove(styleChoser);
				//btn.x = -2;
			}
		}));
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
		//this.renderDirtBackground(0);

		if(isStlyeListOpen.get()){
			mover.visible = false;
			this.styleChoser.render(matrix, mouseX, mouseY, delta);
			Minecraft.getInstance().getTextureManager().bindTexture(FullMapScreen.FULL_MAP_TEXTURES);
			//blit(matrix, 0, 0, 128, this.height, 0, 0, 128, 256, 256, 256);
			//stylePanel.render(matrix, mouseX, mouseY, delta);
		}
		super.render(matrix, mouseX, mouseY, delta);
		mover.visible = true;
	}

	@Override
	public void onClose() {
		if(mover != null) mover.onClose();
	}

	@Override
	public void closeScreen() {
		this.minecraft.displayGuiScreen(parentScreen);
	}
}
