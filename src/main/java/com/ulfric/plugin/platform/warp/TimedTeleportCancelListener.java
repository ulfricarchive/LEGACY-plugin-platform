package com.ulfric.plugin.platform.warp;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.spigotmc.event.entity.EntityMountEvent;

import com.ulfric.commons.spigot.event.player.PlayerMoveBlockEvent;
import com.ulfric.commons.spigot.warp.Teleport;

class TimedTeleportCancelListener implements Listener {

	@EventHandler
	private void onQuit(PlayerQuitEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler
	private void onDeath(EntityDeathEvent event)
	{
		this.cancelTeleport(event.getEntity());
	}

	@EventHandler(ignoreCancelled = true)
	private void onDamageOther(EntityDamageByEntityEvent event)
	{
		this.cancelTeleport(event.getDamager());
	}

	@EventHandler(ignoreCancelled = true)
	private void onTakeDamage(EntityDamageEvent event)
	{
		this.cancelTeleport(event.getEntity());
	}

	@EventHandler(ignoreCancelled = true)
	private void onMoveBlock(PlayerMoveBlockEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onTeleport(EntityTeleportEvent event)
	{
		this.cancelTeleport(event.getEntity());
	}

	@EventHandler(ignoreCancelled = true)
	private void onShoot(ProjectileLaunchEvent event)
	{
		ProjectileSource source = event.getEntity().getShooter();
		if (source instanceof Entity)
		{
			this.cancelTeleport((Entity) source);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onMount(EntityMountEvent event)
	{
		this.cancelTeleport(event.getEntity());
	}

	@EventHandler(ignoreCancelled = true)
	private void onInventoryOpen(InventoryOpenEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onBreak(BlockBreakEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlace(BlockPlaceEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onBucket(PlayerBucketEmptyEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onBucket(PlayerBucketFillEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler
	private void onBedLeave(PlayerBedLeaveEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onBedEnter(PlayerBedEnterEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onLeashEntity(PlayerLeashEntityEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onChangeWorld(PlayerChangedWorldEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	private void onShearEntity(PlayerShearEntityEvent event)
	{
		this.cancelTeleport(event.getPlayer());
		this.cancelTeleport(event.getEntity());
	}

	@EventHandler(ignoreCancelled = true)
	private void onVelocityChange(PlayerVelocityEvent event)
	{
		this.cancelTeleport(event.getPlayer());
	}

	private void cancelTeleport(Entity entity)
	{
		if (entity == null)
		{
			return;
		}
		Teleport teleport = Teleport.getService();
		teleport.cancelTeleport(entity);
	}

}