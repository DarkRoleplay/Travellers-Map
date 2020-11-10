package net.dark_roleplay.travellers_map.backend.collor_palettes;

public class ColorPalette {

	private static final int COLOR_COUNT = 64;
	private static final float[] modifiers = {220/255, 180/255, 135/255};

	public int[] colors;

	public ColorPalette(int[] primaryColors){
		this.colors = new int[COLOR_COUNT * 4];
		for(int i = 0; i < COLOR_COUNT && i < primaryColors.length; i++){
			int r = primaryColors[i] >> 16 & 0xFF;
			int g = primaryColors[i] >> 8 & 0xFF;
			int b = primaryColors[i] & 0xFF;
			colors[i] 					= primaryColors[i];
			for(int j = 0; j < 3; j++)
				colors[i + (j * COLOR_COUNT) + COLOR_COUNT] = (int) (r * modifiers[j]) << 16 | (int) (g * modifiers[j]) << 8 | (int) (b * modifiers[j]);
		}
	}

	public int getColor(int index, int darkness){
		if(index >= COLOR_COUNT || darkness < 0 || darkness > 4) throw new IllegalArgumentException("Requested color was out of range");
		return colors[index + darkness * COLOR_COUNT];
	}
}
