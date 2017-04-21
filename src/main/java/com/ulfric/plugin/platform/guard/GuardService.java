package com.ulfric.plugin.platform.guard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.lang3.ClassUtils;
import org.bukkit.Location;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.guard.EmptyRegionList;
import com.ulfric.commons.spigot.guard.Flag;
import com.ulfric.commons.spigot.guard.Guard;
import com.ulfric.commons.spigot.guard.Region;
import com.ulfric.commons.spigot.guard.RegionColl;
import com.ulfric.commons.spigot.guard.RegionList;
import com.ulfric.commons.spigot.guard.Shape;
import com.ulfric.dragoon.construct.InstanceUtils;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class GuardService implements Guard {

	@Inject
	private Container owner;

	private DataStore folder;

	private Map<String, Flag<?>> flags;
	private Map<String, Region> regionsByName;
	private Map<UUID, RegionColl> regionsByWorld;

	@Initialize
	private void initialize()
	{
		this.flags = new HashMap<>();
		this.regionsByName = new CaseInsensitiveMap<>();
		this.regionsByWorld = new HashMap<>();
		this.loadRegionsFromDataStore();
	}

	private void loadRegionsFromDataStore()
	{
		this.folder = Data.getDataStore(this.owner).getDataStore("regions");
		this.folder.loadAllData().forEach(this::loadRegion);
	}

	private void loadRegion(PersistentData data)
	{
		Region region = Region.builder()
			.setName(data.getString("name"))
			.setWorld(data.getUniqueId("world"))
			.setWeight(data.getInt("weight"))
			.setFlags(this.loadFlags(data.getSection("flags")))
			.setBounds(this.getShape(data.getSection("bounds")))
			.setGlobal(data.getBoolean("global"))
			.build();

		this.addActiveRegion(region);
	}

	private Map<Flag<?>, Object> loadFlags(PersistentData data)
	{
		Map<Flag<?>, Object> flags = new HashMap<>();

		for (PersistentData flagData : data.getSections())
		{
			String flagName = flagData.getString("name");
			Flag<?> flag = this.getFlag(flagName);
			Objects.requireNonNull(flag, flagName);
			Object value = flag.parseData(flagData.getObject("data"));
			flags.put(flag, value);
		}

		return flags;
	}

	private Shape getShape(PersistentData data)
	{
		String type = data.getString("type");
		if (type == null)
		{
			return null;
		}

		Class<?> javaType = Try.to(() -> ClassUtils.getClass(type));
		Object creator = InstanceUtils.createOrNull(javaType);
		if (!(creator instanceof Shape))
		{
			return null;
		}

		Shape shapeCreator = (Shape) creator;

		Map<String, Object> shapeData = new HashMap<>();
		PersistentData shapeConfig = data.getSection("shape");
		if (shapeConfig != null)
		{
			for (String shapePart : shapeConfig.getKeys())
			{
				shapeData.put(shapePart, shapeConfig.getObject(shapePart));
			}
		}

		return shapeCreator.fromMap(shapeData);
	}

	@Override
	public Region getRegion(String name)
	{
		return this.regionsByName.get(name);
	}

	@Override
	public void addRegion(Region region)
	{
		if (this.addActiveRegion(region))
		{
			// TODO save file
		}
	}

	private boolean addActiveRegion(Region region)
	{
		if (this.regionsByName.putIfAbsent(region.getName(), region) == null)
		{
			this.regionsByWorld.computeIfAbsent(region.getWorld(), key -> new SpatialHashedRegionColl())
				.addRegion(region);
		}

		return false;
	}

	@Override
	public void removeRegion(Region region)
	{
		// TODO delete file
		if (this.regionsByName.remove(region.getName(), region))
		{
			RegionColl world = this.regionsByWorld.get(region.getWorld());
			if (world != null)
			{
				world.removeRegion(region);
			}
		}
	}

	@Override
	public RegionList getRegions(Location location)
	{
		RegionColl regions = this.regionsByWorld.get(location.getWorld().getUID());
		if (regions == null)
		{
			return EmptyRegionList.INSTANCE;
		}

		return regions.getRegions(location);
	}

	@Override
	public boolean isAllowed(Location location, Flag<Boolean> flag)
	{
		return this.getRegions(location).getDominantFlag(flag);
	}

	@Override
	public Flag<?> getFlag(String name)
	{
		return this.flags.get(name);
	}

	@Override
	public void registerFlag(Flag<?> flag)
	{
		this.flags.put(flag.getName(), flag);
	}

	@Override
	public void unregisterFlag(Flag<?> flag)
	{
		this.flags.remove(flag.getName(), flag);
	}

}