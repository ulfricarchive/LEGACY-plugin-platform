package com.ulfric.plugin.platform.guard.flag;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.ulfric.commons.spigot.guard.BooleanFlag;
import com.ulfric.commons.spigot.guard.Guard;

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

}