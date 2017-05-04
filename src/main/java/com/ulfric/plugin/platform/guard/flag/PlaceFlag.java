package com.ulfric.plugin.platform.guard.flag;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.guard.BooleanFlag;
import com.ulfric.commons.spigot.guard.Guard;

@Name("Place")
final class PlaceFlag extends BooleanFlag {

	private final Guard regions;

	PlaceFlag()
	{
		super(false);

		this.regions = Guard.getService();
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlace(BlockPlaceEvent event)
	{
		if (!this.regions.isAllowed(event.getBlock().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlaceWater(PlayerBucketEmptyEvent event)
	{
		if (!this.regions.isAllowed(event.getBlockClicked().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlaceHanging(HangingPlaceEvent event)
	{
		if (!this.regions.isAllowed(event.getEntity().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onIgnite(BlockIgniteEvent event)
	{
		if (!this.regions.isAllowed(event.getBlock().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onSignChange(SignChangeEvent event)
	{
		if (!this.regions.isAllowed(event.getBlock().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

}