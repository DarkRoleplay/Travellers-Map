package net.dark_roleplay.travellers_map.waypoints.categories;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.text.Collator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class CategoryList {

	private static Category FALLBACK = new Category(new ResourceLocation(TravellersMap.MODID, "miscellaneous"));
	private static Set<Category> CATEGORIES;

	public static Set<Category> getCategories(){
		return CATEGORIES;
	}

	public static void resetCategories(){
		CATEGORIES = new HashSet();
		CATEGORIES.add(FALLBACK);
	}

	public static void addCategories(Set<Category> categories){
		CATEGORIES.addAll(categories);
	}

	public static void sortCategories(){
		Locale locale = Minecraft.getInstance().getLanguageManager().getCurrentLanguage().getJavaLocale();
		Collator collator = Collator.getInstance(locale);
		collator.setStrength(Collator.PRIMARY);

		Set<Category> sortedCategories = new TreeSet<>((catA, catB) ->
				collator.compare(catA.getLocalizationKey(), catB.getLocalizationKey())
		);

		sortedCategories.addAll(CATEGORIES);
		CATEGORIES = sortedCategories;
	}
}
