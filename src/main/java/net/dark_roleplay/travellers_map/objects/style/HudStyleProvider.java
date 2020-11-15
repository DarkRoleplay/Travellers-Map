package net.dark_roleplay.travellers_map.objects.style;

import net.dark_roleplay.travellers_map.configs.client.HudConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HudStyleProvider {
	private final Map<String, HudStyle> styles;
	private final HudStyle fallbackStyle;
	private final HudConfig config;

	private HudStyle activeStyle;

	public HudStyleProvider(HudConfig config, HudStyle fallbackStyle){
		this.config = config;
		this.fallbackStyle = fallbackStyle;
		this.styles = new HashMap<>();
	}

	public HudStyle getActiveStyle(){
		return activeStyle == null ? fallbackStyle : activeStyle;
	}

	public void setActiveStyle(HudStyle newStyle){
		this.activeStyle = newStyle;
		this.config.STYLE.set(newStyle.getStyleName());
	}

	public void reloadStyles(Set<HudStyle> styles){
		this.styles.clear();
		this.styles.put(fallbackStyle.getStyleName(), fallbackStyle);
		for(HudStyle style : styles)
			this.styles.put(style.getStyleName(), style);

		this.activeStyle = this.styles.get(this.config.STYLE.get());
	}

	public Collection<HudStyle> getStyles(){
		return this.styles.values();
	}
}
