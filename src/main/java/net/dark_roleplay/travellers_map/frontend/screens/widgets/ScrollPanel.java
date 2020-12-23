package net.dark_roleplay.travellers_map.frontend.screens.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;

public abstract class ScrollPanel extends NestedWidget {

	private boolean scrolling;
	protected float vScrollDistance;
	protected float hScrollDistance;

	public ScrollPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public abstract int getContentHeight();

	public abstract int getContentWidth();



	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks){

	}
}
