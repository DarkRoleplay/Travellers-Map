package net.dark_roleplay.travellers_map2.handler;

import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map2.objects.screens.full_map.FullMapScreen;
import net.dark_roleplay.travellers_map2.objects.screens.minimap.settings.MinimapSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = TravellersMap.MODID, value = Dist.CLIENT)
public class TravellersKeybinds {

    public static KeyBinding ZOOM = new KeyBinding("travellers_map.zoom", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_KP_0, "key.categories.misc");
    public static KeyBinding ZOOM_IN = new KeyBinding("travellers_map.zoom.in", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.misc");
    public static KeyBinding ZOOM_OUT = new KeyBinding("travellers_map.zoom.out", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.misc");
    public static KeyBinding OPEN_MAP = new KeyBinding("travellers_map.map.open", GLFW.GLFW_KEY_M, "key.categories.misc");

    @SubscribeEvent
    public static void keyListeners(InputEvent.KeyInputEvent event){
        if(OPEN_MAP.isPressed()){
            Minecraft.getInstance().displayGuiScreen(new FullMapScreen());
        }
    }

    public static void registerKeybinds(FMLClientSetupEvent event){
        ClientRegistry.registerKeyBinding(OPEN_MAP);
        ClientRegistry.registerKeyBinding(ZOOM);
        ClientRegistry.registerKeyBinding(ZOOM_IN);
        ClientRegistry.registerKeyBinding(ZOOM_OUT);
    }
}
