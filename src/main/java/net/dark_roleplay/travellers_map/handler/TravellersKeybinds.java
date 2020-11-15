package net.dark_roleplay.travellers_map.handler;

import net.dark_roleplay.library.ChargedKeybinding;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.dark_roleplay.travellers_map.configs.ClientConfig;
import net.dark_roleplay.travellers_map.user_facing.screens.full_map.FullMapScreen;
import net.dark_roleplay.travellers_map.user_facing.screens.minimap.settings.MinimapSettingsScreen;
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

    public static KeyBinding ZOOM = new KeyBinding("key.travellers_map.zoom", KeyConflictContext.IN_GAME, InputMappings.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_4, "key.categories.travellers_map");
    public static KeyBinding ZOOM_IN = new KeyBinding("key.travellers_map.zoom.in", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.travellers_map");
    public static KeyBinding ZOOM_OUT = new KeyBinding("key.travellers_map.zoom.out", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.categories.travellers_map");
    public static KeyBinding SHOW_OVERLAY = new KeyBinding("key.travellers_map.overlay_map.show", GLFW.GLFW_KEY_N, "key.categories.travellers_map");
    public static KeyBinding TOGGLE_MINIMAP = new KeyBinding("key.travellers_map.minimap.toggle", GLFW.GLFW_KEY_UNKNOWN, "key.categories.travellers_map");
    public static KeyBinding TOGGLE_COMPASS = new KeyBinding("key.travellers_map.compass.toggle", GLFW.GLFW_KEY_UNKNOWN, "key.categories.travellers_map");

    public static ChargedKeybinding OPEN_MAP = new ChargedKeybinding("key.travellers_map.map.open", GLFW.GLFW_KEY_M, "key.categories.travellers_map",
          () -> Minecraft.getInstance().displayGuiScreen(new MinimapSettingsScreen(null)), () -> Minecraft.getInstance().displayGuiScreen(new FullMapScreen()));

    @SubscribeEvent
    public static void keyListeners(InputEvent.KeyInputEvent event){
        if(TOGGLE_MINIMAP.isPressed()){
            ClientConfig.MINIMAP.VISIBLE.set(!ClientConfig.MINIMAP.VISIBLE.get());
        }
        if(TOGGLE_COMPASS.isPressed()){
            ClientConfig.COMPASS.VISIBLE.set(!ClientConfig.COMPASS.VISIBLE.get());
        }
    }

    public static void registerKeybinds(FMLClientSetupEvent event){
        ClientRegistry.registerKeyBinding(OPEN_MAP);
        ClientRegistry.registerKeyBinding(ZOOM);
        ClientRegistry.registerKeyBinding(ZOOM_IN);
        ClientRegistry.registerKeyBinding(ZOOM_OUT);
        ClientRegistry.registerKeyBinding(SHOW_OVERLAY);
        ClientRegistry.registerKeyBinding(TOGGLE_MINIMAP);
        ClientRegistry.registerKeyBinding(TOGGLE_COMPASS);
    }
}
