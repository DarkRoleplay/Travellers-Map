package net.dark_roleplay.travellers_map.screens.screens.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.waypoints.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class WaypointCreationWidget extends StackedWidget {

	private static final ResourceLocation BG = new ResourceLocation(TravellersMap.MODID, "textures/guis/widgets/waypoint_creation.png");

	private static int width = 147, height = 104;

	private NumberTextWidget[] coords = new NumberTextWidget[3];
	private FontRenderer fontRenderer;
	private Waypoint waypoint;

	public WaypointCreationWidget(int x, int y, Waypoint waypoint, FontRenderer fontRenderer, Runnable cancel, Runnable success) {
		super(x, y, 147, 104);
		this.fontRenderer = fontRenderer;
		this.waypoint = waypoint;

		TextFieldWidget nameField;
		this.addChild(nameField = new TextFieldWidget(this.fontRenderer, x + 28, y + 31, 111, 12, new StringTextComponent("Name")));
		nameField.setText(waypoint.getName());
		nameField.setResponder(waypoint::setName);

		this.addChild(coords[0] = new NumberTextWidget(this.fontRenderer, x + 8, y + 60, 42, waypoint.getPos().getX(), true, new StringTextComponent("X")));
		this.addChild(coords[1] = new NumberTextWidget(this.fontRenderer, x + 53, y + 60, 42, waypoint.getPos().getY(), false, new StringTextComponent("Y")));
		this.addChild(coords[2] = new NumberTextWidget(this.fontRenderer, x + 98, y + 60, 42, waypoint.getPos().getZ(), true, new StringTextComponent("Z")));

		this.addChild(new Button(x + 7, y + 77, 66, 20, new StringTextComponent("create"), btn -> {
			success.run();
		}));

		this.addChild(new Button(x + 75, y + 77, 66, 20, new StringTextComponent("cancel"), btn -> {
			cancel.run();
		}));

		this.addChild(new Button(x + 7, y + 28, 18, 18, new StringTextComponent("XYZ"), btn -> {
			pushChildStack();
			addChild(new ColorPickerWidget(x + 7, y + 28, fontRenderer));
			listeners.add(new IGuiEventListener() {
				@Override
				public boolean mouseClicked(double mouseX, double mouseY, int button) {
					popChildStack();
					return true;
				}
			});
		}));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		Minecraft.getInstance().textureManager.bindTexture(BG);
		blit(matrixStack, posX, posY, 0, 0, 147, 104);

		this.fontRenderer.drawStringWithShadow(matrixStack, "X:", this.posX + 7, this.posY + 50, 0xFFFFFFFF);
		this.fontRenderer.drawStringWithShadow(matrixStack, "Y:", this.posX + 52, this.posY + 50, 0xFFFFFFFF);
		this.fontRenderer.drawStringWithShadow(matrixStack, "Z:", this.posX + 97, this.posY + 50, 0xFFFFFFFF);

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
}
