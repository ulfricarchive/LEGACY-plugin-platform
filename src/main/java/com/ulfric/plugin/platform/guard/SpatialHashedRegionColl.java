package com.ulfric.plugin.platform.guard;

import org.bukkit.Location;

import com.ulfric.commons.spigot.guard.Region;
import com.ulfric.commons.spigot.guard.RegionColl;
import com.ulfric.commons.spigot.guard.RegionList;

public class SpatialHashedRegionColl implements RegionColl {

	private final SpatialHash<Region> regions = SpatialHash.<Region>builder()
			.setSectionSize(128)
			.build();

	@Override
	public RegionList getRegions(Location location)
	{
		SortedRegionList regions = new SortedRegionList();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		this.regions.hitTestAll(x, y, z, regions::addRegion);
		return regions;
	}

	@Override
	public void addRegion(Region region)
	{
		this.regions.put(region.getBounds(), region);
	}

	@Override
	public void removeRegion(Region region)
	{
		this.regions.remove(region::equals);
	}

}