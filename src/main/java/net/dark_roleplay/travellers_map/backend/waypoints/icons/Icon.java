package net.dark_roleplay.travellers_map.backend.waypoints.icons;

import net.minecraft.util.ResourceLocation;

public class Icon {

	private ResourceLocation iconName;

	public Icon(ResourceLocation iconName) {
		this.iconName = iconName;
	}

	public ResourceLocation getIconName() {
		return iconName;
	}
}
