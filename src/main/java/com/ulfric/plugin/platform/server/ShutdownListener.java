package com.ulfric.plugin.platform.server;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ulfric.commons.spigot.event.server.ServerShutdownEvent;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.dragoon.inject.Inject;

public class ShutdownListener implements Listener {

	@Inject
	private Logger logger;

	@EventHandler
	@ShutdownAudit
	private void onShutdown(ServerShutdownEvent event)
	{
		this.kickAllPlayers();
		Bukkit.shutdown();
	}

	private void kickAllPlayers()
	{
		Text text = Text.getService();
		Bukkit.getOnlinePlayers().forEach(player -> text.sendMessage(player, "shutdown-disconnect"));
	}

}