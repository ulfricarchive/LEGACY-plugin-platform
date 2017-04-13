package com.ulfric.plugin.platform.placeholder;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

@Name("SERVER_NAME")
public class ServerNamePlaceholder implements Placeholder {

	@Override
	public String apply(CommandSender display)
	{
		return Bukkit.getName();
	}

}