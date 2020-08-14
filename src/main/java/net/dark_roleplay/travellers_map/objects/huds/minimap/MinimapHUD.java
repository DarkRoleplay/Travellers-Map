package net.dark_roleplay.travellers_map.objects.huds.minimap;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.objects.style.HudStyleProvider;
import net.dark_roleplay.travellers_map.rendering.MapType;
import net.dark_roleplay.travellers_map.rendering.MapRenderInfo;
import net.dark_roleplay.travellers_map.util.BlendBlitHelper;
import net.dark_roleplay.travellers_map.configs.ClientConfig;
import net.dark_roleplay.travellers_map.objects.huds.hud.Hud;
import net.dark_roleplay.travellers_map.objects.style.HudStyle;
import net.dark_roleplay.travellers_map.objects.screens.full_map.FullMapScreen;
import net.dark_roleplay.travellers_map.rendering.MapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class MinimapHUD extends Hud {
	public static final MinimapHUD INSTANCE = new MinimapHUD();

	private float[] zoomLevels = new float[]{0.25F, 0.5F, 1.0F, 2.0F};
	private int currentZoomLevel = 1;

	private MapRenderInfo mapRenderInfo = new MapRenderInfo();

	private MinimapHUD() {
		super(ClientConfig.MINIMAP, "hud." + TravellersMap.MODID + ".minimap",
				new HudStyleProvider(ClientConfig.MINIMAP,
						new HudStyle("Default", 64, 64, "travellers_map:textures/styles/minimap/default_mask.png", "travellers_map:textures/styles/minimap/default_overlay.png")
				));
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
		if (Minecraft.getInstance().currentScreen instanceof FullMapScreen) return;
		renderOverlay(matrix);
		renderMap(matrix, delta);
	}

	private void renderOverlay(MatrixStack matrix) {
		HudStyle style = this.getStyleProvider().getActiveStyle();

		RenderSystem.pushMatrix();
		RenderSystem.scaled(ClientConfig.MINIMAP.SCALE.get(), ClientConfig.MINIMAP.SCALE.get(), 1);

		Minecraft.getInstance().getTextureManager().bindTexture(style.getOverlay());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		BlendBlitHelper.blit(0, 0, style.getWidth(), style.getHeight(), 0, 0, 1, 1, 1, 1);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		MatrixStack stack = new MatrixStack();

		Minecraft.getInstance().getTextureManager().bindTexture(style.getMask());
		RenderSystem.enableDepthTest();
		RenderSystem.translatef(0.0F, 0.0F, 950.0F);
		RenderSystem.colorMask(false, false, false, false);
		fill(stack, 4680, 2260, -4680, -2260, 0xFFFFFFFF);
		RenderSystem.translatef(0.0F, 0.0F, -950.0F);
		RenderSystem.depthFunc(518);
		blit(stack, 0, 0, style.getWidth(), style.getHeight(), 0, 0, 1, 1, 1, 1);
		RenderSystem.depthFunc(515);
		RenderSystem.colorMask(true, true, true, true);
	}

	private void renderMap(MatrixStack matrix, float delta) {
		HudStyle style = this.getStyleProvider().getActiveStyle();
		MatrixStack stack = new MatrixStack();

		BlockPos playerPos = Minecraft.getInstance().player.getPosition();
		mapRenderInfo.update(style.getWidth(), style.getHeight(), zoomLevels[currentZoomLevel], playerPos);
		MapRenderer.renderMap(matrix, mapRenderInfo, MapType.MINIMAP, true, delta);


		RenderSystem.translatef(style.getWidth() / 2, style.getHeight() / 2, 0);
//		//Reset Minimap Mask
		RenderSystem.depthFunc(518);
		RenderSystem.translatef(0.0F, 0.0F, -950.0F);
		RenderSystem.colorMask(false, false, false, false);
		fill(stack, 4680, 2260, -4680, -2260, -16777216);
		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.depthFunc(515);
		RenderSystem.popMatrix();
	}

	public static void increaseZoom() {
		INSTANCE.currentZoomLevel = Math.min(INSTANCE.zoomLevels.length - 1, INSTANCE.currentZoomLevel + 1);
	}

	public static void decreaseZoom() {
		INSTANCE.currentZoomLevel = Math.max(0, INSTANCE.currentZoomLevel - 1);
	}
}
