package com.ulfric.plugin.platform.text;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

@Name("PRIMARY_COLOR")
public class PrimaryColorPlaceholder implements Placeholder {

	@Override
	public String apply(CommandSender to)
	{
		// TODO configurable...
		return ChatColor.BLUE.toString();
	}

}