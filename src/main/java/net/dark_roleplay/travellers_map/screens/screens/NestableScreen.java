package net.dark_roleplay.travellers_map.screens.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class NestableScreen extends Screen {

	protected final List<IRenderable> renderChildren = Lists.newArrayList();

	protected NestableScreen(ITextComponent titleIn) {
		super(titleIn);
	}

	@Override
	protected void init() {
		this.renderChildren.clear();
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		for(IRenderable child : renderChildren) child.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	public <T extends IRenderable & IGuiEventListener> void addChild(T child){
		this.children.add(child);
		this.renderChildren.add(child);
	}
}
