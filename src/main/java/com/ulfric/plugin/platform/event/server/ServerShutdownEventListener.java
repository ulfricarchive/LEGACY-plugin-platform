package com.ulfric.plugin.platform.event.server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.ulfric.commons.spigot.event.Events;
import com.ulfric.commons.spigot.event.server.ServerShutdownEvent;
import com.ulfric.commons.spigot.event.server.UlfricPluginDisableEvent;
import com.ulfric.commons.spigot.plugin.PluginUtils;

class ServerShutdownEventListener implements Listener {

	@EventHandler
	private void onPluginDisable(UlfricPluginDisableEvent event)
	{
		Plugin plugin = event.getPlugin();
		if (plugin.equals(PluginUtils.getMainPlugin()))
		{
			Events.fire(new ServerShutdownEvent());
		}
	}

}