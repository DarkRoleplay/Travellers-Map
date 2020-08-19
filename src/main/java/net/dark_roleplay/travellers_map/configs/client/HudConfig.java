package net.dark_roleplay.travellers_map.configs.client;

import net.dark_roleplay.travellers_map.user_facing.huds.GuiAlignment;
import net.minecraftforge.common.ForgeConfigSpec;

public class HudConfig {

	public ForgeConfigSpec.EnumValue<GuiAlignment> ALIGNMENT;
	public ForgeConfigSpec.IntValue POS_X;
	public ForgeConfigSpec.IntValue POS_Y;
	public ForgeConfigSpec.DoubleValue SCALE;
	public ForgeConfigSpec.BooleanValue VISIBLE;
	public ForgeConfigSpec.ConfigValue<String> STYLE;

	public HudConfig(ForgeConfigSpec.Builder builder, String name, int posX, int posY, GuiAlignment alignemnt){
		ALIGNMENT = builder.comment("Alignment of the " + name)
				.defineEnum("alignment", alignemnt);
		POS_X = builder.comment("X Position of the " + name)
				.defineInRange("posX", posX, -500, 500);
		POS_Y = builder.comment("Y Position of the " + name)
				.defineInRange("posY", posY, -500, 500);
		SCALE = builder.comment("Scale of the " + name)
				.defineInRange("scale", 1.0, 0.25, 4.0);
		VISIBLE = builder.comment("Is " + name + " visible?")
				.define("visible", true);
		STYLE = builder.comment("Selected Style (don't change this manually)")
				.define("style", "default");
	}
}
