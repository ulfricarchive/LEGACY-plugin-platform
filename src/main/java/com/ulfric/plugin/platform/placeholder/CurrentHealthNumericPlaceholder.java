package com.ulfric.plugin.platform.placeholder;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.text.placeholder.PlayerPlaceholder;
import com.ulfric.commons.text.FormatUtils;

@Name("CURRENT_HEALTH_NUMERIC")
class CurrentHealthNumericPlaceholder implements PlayerPlaceholder {

	@Override
	public String apply(Player display)
	{
		return FormatUtils.formatDouble(display.getHealth());
	}

}