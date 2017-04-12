package com.ulfric.plugin.platform.event.player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ulfric.commons.spigot.event.Events;
import com.ulfric.commons.spigot.event.player.AsyncPlayerQuitEvent;
import com.ulfric.commons.spigot.task.Tasks;

public class AsyncPlayerQuitEventListener implements Listener {

	private final Map<UUID, Lock> locks = new ConcurrentHashMap<>();

	@EventHandler
	private void onQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		Lock lock = new ReentrantLock();
		lock.lock();

		this.locks.put(player.getUniqueId(), lock);
		Tasks.runAsync(() ->
		{
			Events.fire(new AsyncPlayerQuitEvent(player));
			Tasks.run(lock::unlock);
		});
	}

	@EventHandler
	private void onPreJoin(AsyncPlayerPreLoginEvent event)
	{
		Lock lock = this.locks.get(event.getUniqueId());

		if (lock != null)
		{
			lock.lock();
		}
	}

}