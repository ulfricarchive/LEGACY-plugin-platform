package com.ulfric.plugin.platform.panel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.panel.Browser;
import com.ulfric.commons.spigot.panel.Panels;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.inject.Inject;

public class PanelsService implements Panels {

	private final Map<UUID, Browser> browsers = new HashMap<>();

	@Inject
	private ObjectFactory factory;

	@Override
	public Browser getBrowser(Player player)
	{
		return this.browsers.computeIfAbsent(player.getUniqueId(), ignored -> this.createBrowser(player));
	}

	private Browser createBrowser(Player player)
	{
		BukkitBrowser browser = (BukkitBrowser) this.factory.request(Browser.class);

		browser.setOwner(player);

		return browser;
	}

}
