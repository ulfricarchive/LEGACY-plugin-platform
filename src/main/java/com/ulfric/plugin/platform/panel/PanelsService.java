package com.ulfric.plugin.platform.panel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.panel.Panels;
import com.ulfric.commons.spigot.panel.browser.Browser;
import com.ulfric.plugin.platform.panel.browser.BukkitBrowser;

class PanelsService implements Panels {

	private final Map<UUID, Browser> browsers = new HashMap<>();

	@Override
	public Browser getBrowser(Player player)
	{
		return this.browsers.computeIfAbsent(player.getUniqueId(), ignored -> this.createBrowser(player));
	}

	private Browser createBrowser(Player player)
	{
		return new BukkitBrowser(player);
	}

}
