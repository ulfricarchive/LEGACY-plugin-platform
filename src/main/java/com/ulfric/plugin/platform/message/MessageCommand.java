package com.ulfric.plugin.platform.message;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.message.Messaging;

@Name("message")
@Alias({"m", "msg", "whisper", "w"})
@Permission("message-use")
@MustBePlayer
public class MessageCommand implements Command {
	
	@Argument
	private Player target;
	
	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		Player target = this.target;
		
		String[] arguments = context.getEnteredArguments();
		String message = this.getMessage(Arrays.copyOfRange(arguments, 1, arguments.length - 1));
		
		Messaging.getService().send(player, target, message);
	}
	
	private String getMessage(String[] args)
	{
		return StringUtils.join(args, ' ');
	}
	
}
