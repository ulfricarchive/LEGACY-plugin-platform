package com.ulfric.plugin.platform.placeholder;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.placeholder.PlayerPlaceholder;

@Name("NO_PERMISSION")
public class NoPermissionPlaceholder implements PlayerPlaceholder {

	@Override
	public String apply(Player player)
	{
		return Metadata.readString(player, MetadataDefaults.NO_PERMISSION);
	}

}
