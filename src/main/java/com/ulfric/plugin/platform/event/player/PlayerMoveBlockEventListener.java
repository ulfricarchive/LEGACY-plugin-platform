package com.ulfric.plugin.platform.event.player;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ulfric.commons.spigot.event.Events;
import com.ulfric.commons.spigot.event.player.PlayerMoveBlockEvent;

class PlayerMoveBlockEventListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	private void onMove(PlayerMoveEvent event)
	{
		Location from = event.getFrom();
		Location to = event.getTo();

		if (from == to)
		{
			return;
		}

		if (from.getBlockX() == to.getBlockX()
			&& from.getBlockY() == to.getBlockY()
			&& from.getBlockZ() == to.getBlockZ())
		{
			return;
		}

		PlayerMoveEvent call = new PlayerMoveBlockEvent(event.getPlayer(), from, to);
		if (Events.fire(call).isCancelled())
		{
			event.setCancelled(true);
		}
	}

}