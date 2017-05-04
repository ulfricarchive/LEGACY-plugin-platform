package com.ulfric.plugin.platform.placeholder;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.placeholder.PlayerPlaceholder;

@Name("LAST_BALANCE_VIEW_USER")
class LastBalanceViewUserPlaceholder implements PlayerPlaceholder {

	@Override
	public String apply(Player player)
	{
		return Metadata.readString(player, MetadataDefaults.LAST_BALANCE_VIEW_USER);
	}

}