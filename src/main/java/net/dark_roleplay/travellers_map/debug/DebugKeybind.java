package net.dark_roleplay.travellers_map.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid= TravellersMap.MODID)
public class DebugKeybind {

	public static KeyBinding DEBUG = new KeyBinding("key.debug", GLFW.GLFW_KEY_H, "key.categories.debug");

	@SubscribeEvent
	public static void keyListeners(InputEvent.KeyInputEvent event){
		if(DEBUG.isPressed()){
			Minecraft.getInstance().displayGuiScreen(new DebugScreen());
		}
	}
}
