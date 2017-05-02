package com.ulfric.plugin.platform.combat;

import com.ulfric.commons.spigot.combat.Combat;
import com.ulfric.commons.spigot.combat.Encounter;
import com.ulfric.commons.spigot.service.ServiceUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;

class CombatListener implements Listener {
	
	@EventHandler
	private void on(EntityDamageByEntityEvent event)
	{
		if (!(event.getEntity() instanceof Player))
		{
			return;
		}
		
		Player victim = (Player) event.getEntity();
		Player attacker = null;
		
		Entity damager = event.getDamager();
		
		if (damager instanceof Player)
		{
			attacker = (Player) damager;
		}
		else if (damager instanceof Projectile)
		{
			Projectile projectile = (Projectile) damager;
			
			if (projectile.getShooter() instanceof Player)
			{
				attacker = (Player) projectile.getShooter();
			}
		}
		
		if (attacker != null)
		{
			if (attacker.getUniqueId().equals(victim.getUniqueId()))
			{
				return;
			}
			
			this.combat(victim, attacker);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	private void on(PlayerDeathEvent event)
	{
		Combat.getService().removeCombat(event.getEntity().getUniqueId());
	}
	
	@EventHandler(ignoreCancelled = true)
	private void on(PlayerQuitEvent event)
	{
		Combat combat = Combat.getService();
		
		Player player = event.getPlayer();
		
		if (combat.inCombat(player.getUniqueId()))
		{
			player.setHealth(0);
			combat.removeCombat(player.getUniqueId());
		}
	}
	
	private void combat(Player victim, Player attacker)
	{
		CombatService service = ServiceUtils.getService(CombatService.class);
		
		Instant expiry = Instant.now().plusMillis(service.getLength());
		
		Encounter encounter = Encounter.builder()
				.setUniqueId(victim.getUniqueId())
				.setAttackerId(attacker.getUniqueId())
				.setExpiry(expiry)
				.build();
		
		service.setCombat(victim.getUniqueId(), encounter);
		
		encounter = Encounter.builder()
				.setUniqueId(attacker.getUniqueId())
				.setAttackerId(victim.getUniqueId())
				.setExpiry(expiry)
				.build();
		
		service.setCombat(attacker.getUniqueId(), encounter);
	}
	
}
