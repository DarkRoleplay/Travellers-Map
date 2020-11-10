package net.dark_roleplay.travellers_map.backend.waypoints.categories;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class Category {

	private final ResourceLocation categoryName;

	public Category(ResourceLocation categoryName) {
		this.categoryName = categoryName;
	}

	public ResourceLocation getCategoryName() {
		return categoryName;
	}

	public TextComponent getLocalizedName(){
		return new TranslationTextComponent(getLocalizationKey());
	}

	public String getLocalizationKey(){
		return String.format("waypoint.category.%s.%s", categoryName.getNamespace(), categoryName.getPath());
	}
}
