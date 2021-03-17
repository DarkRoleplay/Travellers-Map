package net.dark_roleplay.travellers_map.screens.huds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

public class HudStyle {

	public static Codec<HudStyle> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.fieldOf("displayName").forGetter(HudStyle::getDisplayName),
			ResourceLocation.CODEC.fieldOf("registryName").forGetter(HudStyle::getRegistryName),
			TextureData.CODEC.fieldOf("overlay").forGetter(HudStyle::getOverlay),
			TextureData.CODEC.fieldOf("mask").forGetter(HudStyle::getMask)
	).apply(i , HudStyle::new));

	private final String displayName;
	private final ResourceLocation registryName;
	private final TextureData overlay, mask;

	public HudStyle(String displayName, ResourceLocation registryName, TextureData overlay, TextureData mask) {
		this.displayName = displayName;
		this.registryName = registryName;
		this.overlay = overlay;
		this.mask = mask;
	}

	public String getDisplayName() {
		return displayName;
	}

	public ResourceLocation getRegistryName() {
		return registryName;
	}

	public TextureData getOverlay() {
		return overlay;
	}

	public TextureData getMask() {
		return mask;
	}

	public static class TextureData{
		public static Codec<TextureData> CODEC = RecordCodecBuilder.create(i -> i.group(
				Codec.INT.fieldOf("width").forGetter(TextureData::getWidth),
				Codec.INT.fieldOf("height").forGetter(TextureData::getHeight),
				Codec.INT.optionalFieldOf("offsetX", 0).forGetter(TextureData::getOffsetX),
				Codec.INT.optionalFieldOf("offsetY", 0).forGetter(TextureData::getOffsetY),
				ResourceLocation.CODEC.fieldOf("texture").forGetter(TextureData::getLocation)
		).apply(i , TextureData::new));

		private final int width, height;
		private final int offsetX, offsetY;
		private final ResourceLocation location;

		public TextureData(int width, int height, int offsetX, int offsetY, ResourceLocation location) {
			this.width = width;
			this.height = height;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.location = location;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}

		public ResourceLocation getLocation() {
			return location;
		}
	}
}
