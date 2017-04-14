package com.ulfric.plugin.platform.data;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.event.player.AsyncPlayerQuitEvent;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

public class PlayerData implements Listener {

	public static DataStore getPlayerData(Container container)
	{
		return Data.getDataStore(container).getDataStore("players");
	}

	@Inject
	private Container container;

	private DataStore folder;

	@Initialize
	private void initialize()
	{
		this.folder = PlayerData.getPlayerData(this.container);
	}

	@EventHandler
	private void onPreLogin(AsyncPlayerPreLoginEvent event)
	{
		if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED)
		{
			this.folder.getData(event.getUniqueId().toString());
		}
	}

	@EventHandler
	private void onLogout(AsyncPlayerQuitEvent event)
	{
		this.folder.getData(event.getPlayer().getUniqueId().toString()).save();
	}

}