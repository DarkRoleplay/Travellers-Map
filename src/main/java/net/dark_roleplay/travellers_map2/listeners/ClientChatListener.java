package net.dark_roleplay.travellers_map2.listeners;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dark_roleplay.travellers_map.TravellersMap;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TravellersMap.MODID, value = Dist.CLIENT)
public class ClientChatListener {

	private static CommandDispatcher<CommandSource> commands = new CommandDispatcher();

	static{
		commands.register(Commands.literal("foo").executes(context -> {
			context.getSource().sendFeedback(new StringTextComponent("bar"), false);
			return 1;
		}));
	}

	@SubscribeEvent
	public static void playerChat(ClientChatEvent event){
		if(event.getMessage().startsWith("/")){
			try{
				ParseResults<CommandSource> parse = commands.parse(event.getMessage().substring(1), Minecraft.getInstance().player.getCommandSource());
				if(!parse.getContext().getNodes().isEmpty()){
					event.setCanceled(true);
					commands.execute(parse);
				}
			} catch (CommandSyntaxException e) {}
		}
	}
}
