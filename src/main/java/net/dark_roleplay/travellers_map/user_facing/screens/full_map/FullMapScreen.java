package net.dark_roleplay.travellers_map.user_facing.screens.full_map;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.rendering.MapRenderInfo;
import net.dark_roleplay.travellers_map.rendering.MapRenderer;
import net.dark_roleplay.travellers_map.rendering.MapType;
import net.dark_roleplay.travellers_map.user_facing.screens.minimap.settings.MinimapSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class FullMapScreen extends Screen {
    public static ResourceLocation FULL_MAP_TEXTURES = new ResourceLocation(TravellersMap.MODID, "textures/guis/full_map.png");

    private float xOffset = 0;
    private float zOffset = 0;
    private int currentZoomLevel = 1;
    private MapRenderInfo mapRenderInfo = new MapRenderInfo();
    private float[] zoomLevels = new float[]{2.0F, 1.0F, 0.5F, 0.25F};

    private List<ITextComponent> tooltipText = new ArrayList();

    /** ++ Animation Stuff **/

    final int movementResetCooldown = 40;
    int mouseMovedCooldown = 0;

    int prevMouseX = 0;
    int prevMouseY = 0;

    final int targetOffset = 20;
    int offset = 0;

    private Widget[] bottomButtons;
    private Widget[] rightButtons;

    /** -- Animation Stuff **/

    public FullMapScreen(){
        super(new TranslationTextComponent("screen.travellers_map.full_map"));
    }

    @Override
    protected void init() {
        bottomButtons = new Widget[2];
        rightButtons = new Widget[1];

        //Options
        this.addButton(rightButtons[0] = new ImageButton(
              this.width - 13, 1, 12, 12, 112, 96, 16, FULL_MAP_TEXTURES, 128, 128,
              btn -> {
                  Minecraft.getInstance().displayGuiScreen(new MinimapSettingsScreen(this));
              },
              new TranslationTextComponent("screen.button.travellers_map.settings")
        ));

        //Center On Player
        this.addButton(bottomButtons[0] = new ImageButton(
              this.width - 15, this.height - 15, 14, 14, 0, 96, 16, FULL_MAP_TEXTURES, 128, 128,
              btn -> {
                  xOffset = 0;
                  zOffset = 0;
              },
              new TranslationTextComponent("screen.button.travellers_map.center_map")
        ));

        //Waypoint Adding
        this.addButton(bottomButtons[1] = new ImageButton(
              this.width - 27, this.height - 16, 11, 15, 96, 96, 16, FULL_MAP_TEXTURES, 128, 128,
              btn -> {},
              new TranslationTextComponent("screen.button.travellers_map.add_waypoint")
        ));
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        this.renderDirtBackground(0);

        //Map
        BlockPos playerPos = Minecraft.getInstance().player.getPosition();
        mapRenderInfo.update(this.width, this.height, 1/zoomLevels[currentZoomLevel], playerPos.add(xOffset, 0 , zOffset), mouseX, mouseY);

        MapRenderer.renderMap(matrix, mapRenderInfo, MapType.FULL_MAP, false, delta);

        Minecraft.getInstance().getTextureManager().bindTexture(FULL_MAP_TEXTURES);

        this.blit(matrix, 1, this.height - 14, 0, 0, 100, 12, 128, 128);
        int blocks = (int) (zoomLevels[currentZoomLevel] * 100);
        String measurement = String.format("%d Blocks", blocks);

        font.drawStringWithShadow(matrix, measurement, 51 - (font.getStringWidth(measurement)/2), this.height - 12, 0xFFFFFFFF);


        /** ++ Animation Stuff **/

        if(prevMouseX != mouseX && prevMouseY != mouseY){
            prevMouseX = mouseX;
            prevMouseY = mouseY;
            mouseMovedCooldown = movementResetCooldown;
        }

        fill(matrix, this.width - 15 + offset, 0, this.width, 15, 0xC0000000);
        fill(matrix, this.width - 28, this.height - 17 + offset, this.width, this.height, 0xC0000000);
        /** -- Animation Stuff **/

        super.render(matrix, mouseX, mouseY, delta);

        if(offset <= 0)
        for(Widget w : this.buttons){
            if(!w.isHovered()) continue;
            tooltipText.clear();
            tooltipText.add(w.getMessage());
            GuiUtils.drawHoveringText(matrix, tooltipText, w.x + 10, w.y , this.width, this.height, 100, font);
        }
    }

    /** ++ Animation Stuff **/

    @Override
    public void tick() {
        if(mouseMovedCooldown > 0){
            mouseMovedCooldown --;
            if(offset > 0){
                offset -= 4;
                for(Widget w : bottomButtons)
                    w.y -= 4;
                for(Widget w : rightButtons)
                    w.x -= 4;
            }
        }else if(mouseMovedCooldown == 0 && offset < targetOffset){
            offset += 2;
            for(Widget w : bottomButtons)
                w.y += 2;
            for(Widget w : rightButtons)
                w.x += 2;
        }
    }

    /** -- Animation Stuff **/

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double deltaX, double deltaY) {
        boolean success = super.mouseDragged(mouseX, mouseY, mouseButton, deltaX, deltaY);
        if(success || mouseButton != 0) return success;

        xOffset -= deltaX * zoomLevels[currentZoomLevel];
        zOffset -= deltaY * zoomLevels[currentZoomLevel];
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        boolean success = super.mouseScrolled(mouseX, mouseY, scroll);
        if(success) return success;

        if(scroll > 0){
            this.increaseZoom();
        }else if(scroll < 0){
            this.decreaseZoom();
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int btn) {
        boolean result = super.mouseClicked(mouseX, mouseY, btn);
        if(result || btn == 0) return result;

        BlockPos playerPos = Minecraft.getInstance().player.getPosition().add(xOffset, 0, zOffset);
        double worldX = playerPos.getX() + (( mouseX - (this.width/2)) / zoomLevels[currentZoomLevel]);
        double worldZ = playerPos.getZ() + (( mouseY - (this.height/2)) / zoomLevels[currentZoomLevel]);
        xOffset = 0;
        zOffset = 0;
        //Minecraft.getInstance().player.sendChatMessage("/tp " + worldX + " 255 " + worldZ);
        return true;
    }

    public void increaseZoom(){
        if(this.currentZoomLevel < this.zoomLevels.length - 1){
            this.currentZoomLevel += 1;
        }

    }

    public void decreaseZoom(){
        if(this.currentZoomLevel > 0){
            this.currentZoomLevel -= 1;
        }
    }
}
