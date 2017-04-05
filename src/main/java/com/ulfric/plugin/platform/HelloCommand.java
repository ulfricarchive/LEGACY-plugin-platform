package com.ulfric.plugin.platform;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;

@Name("hello")
@Alias({"hey", "hi"})
@Permission("hello-use")
public class HelloCommand implements Command {

	@Argument
	private Player target;

	@Override
	public void run(Context context)
	{
		Metadata.write(this.target, MetadataDefaults.LAST_TARGETED_BY, context.getSender().getName());
		Text text = Text.getService();
		String message = text.getMessage(this.target, "${LAST_TARGETED_BY} says hello!");
		this.target.sendMessage(message);
	}

	@Name("world")
	public static class WorldCommand extends HelloCommand
	{
		@Override
		public void run(Context context)
		{
			Bukkit.broadcastMessage(ChatColor.BLUE + "Hello, world!");
		}
	}

}