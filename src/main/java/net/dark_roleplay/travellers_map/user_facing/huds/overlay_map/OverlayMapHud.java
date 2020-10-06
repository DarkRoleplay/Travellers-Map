package net.dark_roleplay.travellers_map.user_facing.huds.overlay_map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.rendering.MapRenderInfo;
import net.dark_roleplay.travellers_map.rendering.MapRenderer;
import net.dark_roleplay.travellers_map.rendering.MapType;
import net.dark_roleplay.travellers_map.user_facing.huds.hud.Hud;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class OverlayMapHud extends Hud {

    public static final OverlayMapHud INSTANCE = new OverlayMapHud();

    private MapRenderInfo mapRenderInfo = new MapRenderInfo();

    protected OverlayMapHud() {
        super(null, "hud." + TravellersMap.MODID + ".overlay_map", null);
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        BlockPos playerPos = Minecraft.getInstance().player.getPosition();
        mapRenderInfo.update(200, 200, 1, playerPos);

        matrix.push();
        matrix.translate(wWidth/2 - 100, wHeight/2 - 100, 0);

        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrix, 4680, 2260, -4680, -2260, 0xFFFFFFFF);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.depthFunc(518);
        fill(matrix, 0, 0, 200, 200, 0xFFFFFFFF);
        RenderSystem.depthFunc(515);
        RenderSystem.colorMask(true, true, true, true);

        RenderSystem.enableBlend();
        RenderSystem.color4f(1F, 1F, 1F, 0.5F);

        MapRenderer.renderMap(matrix, mapRenderInfo, MapType.MINIMAP, false, delta);

        RenderSystem.color4f(1F, 1F, 1F, 1F);
        matrix.pop();
    }
}
