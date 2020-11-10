package net.dark_roleplay.travellers_map.frontend.screens.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StackedWidget extends NestedWidget {

	protected final Stack<List<IGuiEventListener>> listenerStack = new Stack();
	protected final LinkedList<List<IRenderable>>  rendererStack = new LinkedList();

	public StackedWidget(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		for(List<IRenderable> stackedRenderers : rendererStack){
			for(IRenderable child : stackedRenderers){
				child.render(matrixStack, mouseX, mouseY, partialTicks);
			}
		}

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	public void pushChildStack(){
		this.listenerStack.push(this.listeners);
		this.listeners = Lists.newArrayList();
		this.rendererStack.addLast(this.renderers);
		this.renderers = Lists.newArrayList();
	}

	public void popChildStack(){
		this.listeners = this.listenerStack.pop();
		this.renderers = this.rendererStack.getLast();
		this.rendererStack.removeLast();
	}
}
