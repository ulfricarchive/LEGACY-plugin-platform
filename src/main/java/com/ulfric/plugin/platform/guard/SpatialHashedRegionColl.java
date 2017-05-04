package com.ulfric.plugin.platform.guard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;

import com.ulfric.commons.spigot.guard.Region;
import com.ulfric.commons.spigot.guard.RegionColl;
import com.ulfric.commons.spigot.guard.RegionList;

class SpatialHashedRegionColl implements RegionColl {

	private final SpatialHash<Region> regions = SpatialHash.<Region>builder()
			.setSectionSize(128)
			.build();

	private final List<Region> global = new ArrayList<>();

	@Override
	public RegionList getRegions(Location location)
	{
		SortedRegionList regions = new SortedRegionList();
		this.global.forEach(regions::addRegion);
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		this.regions.hitTestAll(x, y, z, regions::addRegion);
		regions.sort();
		return regions;
	}

	@Override
	public void addRegion(Region region)
	{
		if (region.isGlobal())
		{
			// TODO check if it contains
			this.global.add(region);
			Collections.sort(this.global);
			return;
		}
		this.regions.put(region.getBounds(), region);
	}

	@Override
	public void removeRegion(Region region)
	{
		if (region.isGlobal())
		{
			this.global.remove(region);
			return;
		}
		this.regions.remove(region::equals);
	}

}