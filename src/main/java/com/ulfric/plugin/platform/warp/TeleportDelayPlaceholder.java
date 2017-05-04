package com.ulfric.plugin.platform.warp;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

@Name("TELEPORT_DELAY")
public class TeleportDelayPlaceholder implements Placeholder {

	@Override
	public String apply(CommandSender to)
	{
		return Metadata.readString(to, MetadataDefaults.TELEPORT_DELAY);
	}

}