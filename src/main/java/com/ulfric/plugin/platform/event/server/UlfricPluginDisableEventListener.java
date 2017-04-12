package com.ulfric.plugin.platform.event.server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.ulfric.commons.spigot.event.Events;
import com.ulfric.commons.spigot.event.server.UlfricPluginDisableEvent;
import com.ulfric.commons.spigot.plugin.UlfricPlugin;

public class UlfricPluginDisableEventListener implements Listener {

	@EventHandler
	private void onPluginDisable(PluginDisableEvent event)
	{
		Plugin plugin = event.getPlugin();
		if (plugin instanceof UlfricPlugin)
		{
			Events.fire(new UlfricPluginDisableEvent((UlfricPlugin) plugin));
		}
	}

}