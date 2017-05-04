package com.ulfric.plugin.platform.placeholder;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.placeholder.PlayerPlaceholder;

@Name("LAST_TARGETED_BY")
class LastTargetedByPlaceholder implements PlayerPlaceholder {

	@Override
	public String apply(Player target)
	{
		return Metadata.readString(target, MetadataDefaults.LAST_TARGETED_BY);
	}

}