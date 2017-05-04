package com.ulfric.plugin.platform.combattag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ulfric.commons.spigot.combattag.CombatTag;
import com.ulfric.commons.spigot.player.PlayerUtils;
import com.ulfric.commons.spigot.service.ServiceUtils;

class CombatTagListener implements Listener {
	
	@EventHandler
	private void on(EntityDamageByEntityEvent event)
	{
		Player victim = PlayerUtils.getPlayerCause(event.getEntity());

		if (victim == null)
		{
			return;
		}

		Player attacker = PlayerUtils.getPlayerCause(event.getDamager());

		if (attacker == null)
		{
			return;
		}

		this.tagPlayers(victim, attacker);
	}

	@EventHandler(ignoreCancelled = true)
	private void on(PlayerDeathEvent event)
	{
		CombatTag.getService().unmark(event.getEntity().getUniqueId());
	}

	@EventHandler(ignoreCancelled = true)
	private void on(PlayerQuitEvent event)
	{
		CombatTag combat = CombatTag.getService();

		Player player = event.getPlayer();

		if (combat.isMarked(player.getUniqueId()))
		{
			combat.unmark(player.getUniqueId());

			player.setHealth(0); // TODO set last damage cause?
		}
	}

	private void tagPlayers(Player... players)
	{
		CombatTagService service = ServiceUtils.getService(CombatTagService.class);
		
		for (Player player : players)
		{
			service.mark(player.getUniqueId());
		}
	}

}