package com.ulfric.plugin.platform.placeholder;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.text.placeholder.PlayerPlaceholder;
import com.ulfric.commons.text.FormatUtils;

@Name("MAX_HEALTH_NUMERIC")
class MaxHealthNumericPlaceholder implements PlayerPlaceholder {

	@Override
	public String apply(Player display)
	{
		return FormatUtils.formatDouble(display.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
	}

}