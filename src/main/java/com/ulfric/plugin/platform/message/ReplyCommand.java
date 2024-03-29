package com.ulfric.plugin.platform.message;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.message.Messaging;
import com.ulfric.commons.spigot.text.Text;

@Name("reply")
@Alias("r")
@Permission("reply-use")
@MustBePlayer
class ReplyCommand implements Command {
	
	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		
		Messaging messaging = Messaging.getService();
		Text text = Text.getService();
		
		if (!messaging.hasLastRecipient(player.getUniqueId()))
		{
			text.sendMessage(player, "reply-no-last-recipient");
			return;
		}
		
		Player target = Bukkit.getPlayer(messaging.getLastRecipient(player.getUniqueId()));
		
		if (target == null)
		{
			messaging.clear(player.getUniqueId());
			text.sendMessage(player, "reply-last-recipient-offline");
			return;
		}
		
		String[] arguments = context.getEnteredArguments();
		String message = this.getMessage(Arrays.copyOfRange(arguments, 0, arguments.length - 1));
		
		messaging.reply(player, target, message);
	}
	
	private String getMessage(String[] arguments)
	{
		return StringUtils.join(arguments, ' ');
	}
	
}
