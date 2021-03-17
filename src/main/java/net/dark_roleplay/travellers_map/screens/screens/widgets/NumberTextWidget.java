package net.dark_roleplay.travellers_map.screens.screens.widgets;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;

public class NumberTextWidget extends NestedWidget{

	private static final ResourceLocation BG = new ResourceLocation(TravellersMap.MODID, "textures/guis/widgets/waypoint_creation.png");
	private static final String NUMBER_REGEX = "^-?[0-9]*$";
	private static final String POS_NUMBER_REGEX = "^[0-9]*$";

	private TextFieldWidget textField;

	private boolean canBeNegative = true;
	private int value = 0;


	public NumberTextWidget(FontRenderer fontRenderer, int x, int y, int width, int value, boolean canBeNegative, TextComponent text) {
		super(x, y, width, 14);

		this.canBeNegative = canBeNegative;
		this.value = value;

		this.addChild(textField = new TextFieldWidget(fontRenderer, x, y, width - 8, 12, text));
		textField.setText(String.valueOf(value));
		textField.setValidator(str -> str.matches(canBeNegative ? NUMBER_REGEX : POS_NUMBER_REGEX) || str.isEmpty());
		textField.setResponder(str -> this.value = str.isEmpty() || str.equals("-") ? 0 : Integer.parseInt(str));

		this.addChild(new ImageButton(x + width - 7, y -1, 7, 7, 242, 0, 7, BG, btn -> changeValue(1)));
		this.addChild(new ImageButton(x + width - 7, y + 6, 7, 7, 249, 0, 7, BG, btn -> changeValue(-1)));
	}

	private void changeValue(int amount){
		value += amount;
		if(!canBeNegative && value < 0)
			value = 0;
		textField.setText(String.valueOf(value));
	}

	public int getValue(){
		return value;
	}
}
