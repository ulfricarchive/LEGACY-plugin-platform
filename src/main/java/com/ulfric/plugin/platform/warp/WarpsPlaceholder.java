package com.ulfric.plugin.platform.warp;

import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;
import com.ulfric.commons.spigot.warp.Warp;

@Name("WARPS")
class WarpsPlaceholder implements Placeholder {

	@Override
	public String apply(CommandSender sender)
	{
		WarpsService warps = ServiceUtils.getService(WarpsService.class);

		return warps.getWarps().stream().map(Warp::getName).collect(Collectors.joining(", "));
	}

}