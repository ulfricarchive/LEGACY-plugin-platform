package com.ulfric.plugin.platform.data;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.ulfric.commons.spigot.event.player.AsyncPlayerQuitEvent;
import com.ulfric.dragoon.inject.Inject;

class PlayerDataListener implements Listener {

	@Inject
	private PlayerDataService service;

	@EventHandler
	private void onPreLogin(AsyncPlayerPreLoginEvent event)
	{
		if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED)
		{
			this.service.getData(event.getUniqueId());
		}
	}

	@EventHandler
	private void onLogout(AsyncPlayerQuitEvent event)
	{
		for (int x = 0; x < 50; x++)
			System.out.println(1);
		this.service.getData(event.getPlayer().getUniqueId()).save();
	}

}