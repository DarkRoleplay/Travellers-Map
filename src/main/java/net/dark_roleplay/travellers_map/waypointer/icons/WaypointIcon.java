package net.dark_roleplay.travellers_map.waypointer.icons;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;

public class WaypointIcon {
	public static final Codec<WaypointIcon> CODEC = RecordCodecBuilder.<WaypointIcon>create(
			instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("name").forGetter(icon -> icon.getRegistryName()),
					ResourceLocation.CODEC.optionalFieldOf("compassIcon").forGetter(icon -> icon.getCompassIcon() == null ? Optional.empty() : Optional.of(icon.getCompassIcon())),
					ResourceLocation.CODEC.optionalFieldOf("compassIconTinted").forGetter(icon -> icon.getCompassIconTinted() == null ? Optional.empty() : Optional.of(icon.getCompassIconTinted())),
					ResourceLocation.CODEC.optionalFieldOf("mapIcon").forGetter(icon -> icon.getMapIcon() == null ? Optional.empty() : Optional.of(icon.getMapIcon())),
					ResourceLocation.CODEC.optionalFieldOf("mapIconTinted").forGetter(icon -> icon.getMapIconTinted() == null ? Optional.empty() : Optional.of(icon.getMapIconTinted()))
			).apply(instance, WaypointIcon::new)
	);

	private ResourceLocation registryName;
	private String langKey;
	private ResourceLocation compassIcon;
	private ResourceLocation compassIconTinted;
	private ResourceLocation mapIcon;
	private ResourceLocation mapIconTinted;

	public WaypointIcon(ResourceLocation registryName, Optional<ResourceLocation> compassIcon, Optional<ResourceLocation> compassIconTin, Optional<ResourceLocation> mapIcon, Optional<ResourceLocation> mapIconTint) {
		this.registryName = registryName;
		this.langKey = registryName.getNamespace() + ".waypoint.icon." + registryName.getPath();
		this.compassIcon = compassIcon.orElseGet(() -> null);
		this.compassIconTinted = compassIconTin.orElseGet(() -> null);
		this.mapIcon = mapIcon.orElseGet(() -> null);
		this.mapIconTinted = mapIconTint.orElseGet(() -> null);
	}

	public ResourceLocation getRegistryName() {
		return registryName;
	}

	public String getLangKey() {
		return langKey;
	}

	public ResourceLocation getCompassIcon() {
		return compassIcon;
	}

	public ResourceLocation getCompassIconTinted() {
		return compassIconTinted;
	}

	public ResourceLocation getMapIcon() {
		return mapIcon;
	}

	public ResourceLocation getMapIconTinted() {
		return mapIconTinted;
	}
}
