package com.ulfric.plugin.platform.guard.flag;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.guard.BooleanFlag;
import com.ulfric.commons.spigot.guard.Guard;

@Name("Place")
public final class PlaceFlag extends BooleanFlag {

	private final Guard regions;

	public PlaceFlag()
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

}