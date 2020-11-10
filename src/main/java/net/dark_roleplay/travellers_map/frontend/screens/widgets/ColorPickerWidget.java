package net.dark_roleplay.travellers_map.frontend.screens.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

public class ColorPickerWidget extends NestedWidget {

	private static final ResourceLocation BG = new ResourceLocation(TravellersMap.MODID, "textures/guis/widgets/color_picker.png");

	int hueResolution = 16;
	int valueResolution = 16;

	float selectedHue = 0;
	int hueColor = 0xFF0000;
	int selectedColor = 0xFF0000;

	FontRenderer font;

	public ColorPickerWidget(int x, int y, FontRenderer font) {
		super(x, y, 76, 80);
		this.font = font;

		this.addChild(new TextFieldWidget(Minecraft.getInstance().fontRenderer, x + 1, y + 67, 50, 12, new StringTextComponent("hex-color")));

		this.listeners.add(new IGuiEventListener() {
			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int button) {
				int relX = (int) (mouseX - posX);
				int relY = (int) (mouseY - posY);
				if(relX > 67 && relX < 75 && relY > 1 && relY < 65){ //h selector
					float modifier = 64/hueResolution;
					float relY2 = (int) ((relY - 1) / modifier);

					selectedHue = (relY2/hueResolution) * 360;
					hueColor = hueToRGB(selectedHue, 1, 1);
					return true;
				}else if(relX > 1 && relX < 65 && relY > 1 && relY < 65){ //vs selector
					float modifier = 64/valueResolution;
					float relX2 = (int) ((relX - 1) / modifier);
					float relY2 = (int) ((relY - 1) / modifier);

					float saturation =(relX2/valueResolution);
					float value = 1-(relY2/valueResolution);
					selectedColor = hueToRGB(selectedHue, saturation, value);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		Minecraft.getInstance().textureManager.bindTexture(BG);
		blit(matrixStack, posX, posY, 0, 0, 90, 94);

		float[] rgb = {hueColor >> 16 & 0xFF, hueColor >> 8 & 0xFF, hueColor & 0xFF};

		drawColorGrid(matrixStack, this.posX + 1, this.posY + 1, 64, 64, valueResolution, valueResolution, rgb[0] / 255F, rgb[1] / 255F, rgb[2] / 255F);
		drawHueBar(matrixStack, this.posX + 67, this.posY + 1, 8, 64, hueResolution);

		fill(matrixStack, this.posX + 52, this.posY + 67, this.posX + 52 + 23, this.posY + 67 + 12, 0xFF000000 | this.selectedColor);

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	private static int hueToRGB(float hue, float saturation, float value){
		float r = 0, g = 0, b = 0;

		float chroma = value * saturation;
		float hue1 = hue/60F;
		float x = chroma * (1- Math.abs((hue1 % 2) - 1));
		if(hue1 <= 1){
			r = chroma;
			g = x;
			b = 0;
		}else if(hue1 <= 2){
			r = x;
			g = chroma;
			b = 0;
		}else if(hue1 <= 3){
			r = 0;
			g = chroma;
			b = x;
		}else if(hue1 <= 4){
			r = 0;
			g = x;
			b = chroma;
		}else if(hue1 <= 5){
			r = x;
			g = 0;
			b = chroma;
		}else if(hue1 <= 6){
			r = chroma;
			g = 0;
			b = x;
		}

		float m = value - chroma;
		int r1 = (int) ((r + m) * 255);
		int g1 = (int) ((g + m) * 255);
		int b1 = (int) ((b + m) * 255);
		return r1 << 16 | b1 << 8 | g1;

	}

	private static void drawHueBar(MatrixStack matrixStack, int px, int py, int pw, int ph, int res) {
		float hScale = res - 1;
		float hueRes = 360F/res;

		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();

		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

		matrixStack.push();
		matrixStack.translate(px, py, 0);
		matrixStack.scale(pw, ph /(float) res, 1.0f);
		Matrix4f m = matrixStack.getLast().getMatrix();
		for(int i = 0; i < res; i++)
		{
			float hue = hueRes * i;
			int color = hueToRGB(hue, 1, 1);

			float r = (color >> 16 & 0xFF) / 255F;
			float g = (color >> 8 & 0xFF) /255F;
			float b = (color & 0xFF)/255F;

			buffer.pos(m, 0, i, 0).color(r,g,b,1.0f).endVertex();
			buffer.pos(m, 0,  i+1, 0).color(r,g,b,1.0f).endVertex();
			buffer.pos(m, 0+1, i+1, 0).color(r,g,b,1.0f).endVertex();
			buffer.pos(m, 0+1, i, 0).color(r,g,b,1.0f).endVertex();
		}
		matrixStack.pop();

		buffer.finishDrawing();
		WorldVertexBufferUploader.draw(buffer);

		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	private static void drawColorGrid(MatrixStack matrixStack, int px, int py, int pw, int ph, int wRes, int hRes, float rColor, float gColor, float bColor) {
		float wScale = wRes - 1.0f;
		float hScale = hRes - 1.0f;

		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();

		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

		matrixStack.push();
		matrixStack.translate(px, py, 0);
		matrixStack.scale(pw /(float) wRes, ph /(float) hRes, 1.0f);
		Matrix4f m = matrixStack.getLast().getMatrix();
		for(int y = 0; y< hRes; y++)
		{
			float lr = MathHelper.lerp(y/ hScale, 1, 0);
			float lg = MathHelper.lerp(y/ hScale, 1, 0);
			float lb = MathHelper.lerp(y/ hScale, 1, 0);

			float rr = MathHelper.lerp(y/ hScale, rColor, 0);
			float rg = MathHelper.lerp(y/ hScale, gColor, 0);
			float rb = MathHelper.lerp(y/ hScale, bColor, 0);
			for(int x = 0; x< wRes; x++)
			{
				float r = MathHelper.lerp(x/ wScale,lr,rr);
				float g = MathHelper.lerp(x/ wScale,lg,rg);
				float b = MathHelper.lerp(x/ wScale,lb,rb);
				buffer.pos(m, x, y, 0).color(r,g,b,1.0f).endVertex();
				buffer.pos(m, x, y+1, 0).color(r,g,b,1.0f).endVertex();
				buffer.pos(m, x+1, y+1, 0).color(r,g,b,1.0f).endVertex();
				buffer.pos(m, x+1, y, 0).color(r,g,b,1.0f).endVertex();
			}
		}
		matrixStack.pop();

		buffer.finishDrawing();
		WorldVertexBufferUploader.draw(buffer);

		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}
}
