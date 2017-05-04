package com.ulfric.plugin.platform.text.color;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.text.Color;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

@Name("SECONDARY_COLOR")
public class SecondaryColorPlaceholder implements Placeholder {

	@Override
	public String apply(CommandSender to)
	{
		return Color.getService().secondary(to);
	}

}