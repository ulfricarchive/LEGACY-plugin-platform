package com.ulfric.plugin.platform;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;

@Name("hello")
@Alias({"hey", "hi"})
@Permission("hello-use")
public class HelloCommand implements Command {

	@Argument
	private Player target;

	@Override
	public void run(Context context)
	{
		this.target.sendMessage(context.getSender().getName() + " says hello!");
	}

}