package net.dark_roleplay.travellers_map.user_facing.huds;

public enum GuiAlignment {
	TOP_LEFT(0F, 0F), TOP(0.5F, 0F), TOP_RIGHT(1F, 0F),
	LEFT(0F, 0.5F), CENTER(0.5F, 0.5F), RIGHT(1F, 0.5F),
	BOTTOM_LEFT(0F, 1F), BOTTOM(0.5F, 1F), BOTTOM_RIGHT(1F, 1F);

	private final float multX;
	private final float multY;

	private GuiAlignment(float multX, float multY) {
		this.multX = multX;
		this.multY = multY;
	}

	public int getX(int screenWidth) {
		return (int) (screenWidth * multX);
	}

	public int getY(int screenHeight) {
		return (int) (screenHeight * multY);
	}
}
