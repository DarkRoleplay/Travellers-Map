package net.dark_roleplay.travellers_map.screens.screens.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

import java.util.List;

public class NestedWidget extends FocusableGui implements IRenderable {

	protected List<IGuiEventListener> listeners = Lists.newArrayList();
	protected List<IRenderable> renderers = Lists.newArrayList();

	protected int posX, posY;
	protected int width, height;

	public NestedWidget(int x, int y, int width, int height){
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public List<? extends IGuiEventListener> getEventListeners() {
		return listeners;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		for(IRenderable child : renderers){
			child.render(matrixStack, mouseX, mouseY, partialTicks);
		}
	}

	public <T extends IRenderable & IGuiEventListener> void addChild(T child){
		this.listeners.add(child);
		this.renderers.add(child);
	}

	public <T extends IRenderable & IGuiEventListener> void removeChild(T child){
		this.listeners.remove(child);
		this.renderers.remove(child);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return mouseX > this.posX && mouseY > this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height;
	}
}