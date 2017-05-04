package com.ulfric.plugin.platform.placeholder;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

@Name("SENDER_NAME")
class SenderNamePlaceholder implements Placeholder {

	@Override
	public String apply(CommandSender target)
	{
		return target.getName();
	}

}