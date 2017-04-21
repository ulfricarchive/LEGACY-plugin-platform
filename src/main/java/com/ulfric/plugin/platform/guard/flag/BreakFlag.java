package com.ulfric.plugin.platform.guard.flag;

import java.util.Iterator;

import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Hanging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.guard.BooleanFlag;
import com.ulfric.commons.spigot.guard.Guard;

@Name("Break")
public final class BreakFlag extends BooleanFlag {

	private final Guard regions;

	public BreakFlag()
	{
		super(false);

		this.regions = Guard.getService();
	}

	@EventHandler(ignoreCancelled = true)
	private void onBreak(BlockBreakEvent event)
	{
		if (!this.regions.isAllowed(event.getBlock().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onPickupWater(PlayerBucketFillEvent event)
	{
		if (!this.regions.isAllowed(event.getBlockClicked().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onBreakHanging(HangingBreakByEntityEvent event)
	{
		if (!this.regions.isAllowed(event.getEntity().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onHangingPop(EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Hanging)
		{
			if (!this.regions.isAllowed(event.getEntity().getLocation(), this))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onVehicleDamage(VehicleDamageEvent event)
	{
		if (!this.regions.isAllowed(event.getVehicle().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onVehicleDestroy(VehicleDestroyEvent event)
	{
		if (!this.regions.isAllowed(event.getVehicle().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onEntityChangeBlock(EntityChangeBlockEvent event)
	{
		if (event.getEntity() instanceof Enderman)
		{
			if (!this.regions.isAllowed(event.getBlock().getLocation(), this))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onExplode(BlockExplodeEvent event)
	{
		if (!this.regions.isAllowed(event.getBlock().getLocation(), this))
		{
			event.setCancelled(true);
			return;
		}

		Iterator<Block> blocks = event.blockList().iterator();
		while (blocks.hasNext())
		{
			if (!this.regions.isAllowed(blocks.next().getLocation(), this))
			{
				blocks.remove();
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onArmorStandChange(PlayerArmorStandManipulateEvent event)
	{
		if (!this.regions.isAllowed(event.getRightClicked().getLocation(), this))
		{
			event.setCancelled(true);
		}
	}

	// TODO piston events

}