package com.ulfric.plugin.platform.guard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ulfric.commons.spigot.guard.Flag;
import com.ulfric.commons.spigot.guard.Region;
import com.ulfric.commons.spigot.guard.RegionList;

public final class SortedRegionList implements RegionList {

	private final List<Region> regions = new ArrayList<>();

	public void addRegion(Region region)
	{
		this.regions.add(region);
	}

	public void sort()
	{
		Collections.sort(this.regions);
	}

	@Override
	public Iterator<Region> iterator()
	{
		return this.regions.iterator();
	}

	@Override
	public <T> T getDominantFlag(Flag<T> flag)
	{
		for (Region region : this.regions)
		{
			T value = region.readFlag(flag);

			if (value != null)
			{
				return value;
			}
		}

		return flag.getDefaultData();
	}

}