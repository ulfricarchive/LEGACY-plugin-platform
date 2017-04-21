package com.ulfric.plugin.platform.warp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.bukkit.Location;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.location.LocationUtils;
import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.Warps;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

public final class WarpsService implements Warps {

	public static DataStore getWarpData(Container container)
	{
		return Data.getDataStore(container).getDataStore("warps");
	}

	@Inject
	private Container container;

	private final Map<String, Warp> warps = new CaseInsensitiveMap<>();
	private DataStore folder;

	@Initialize
	private void initialize()
	{
		this.folder = WarpsService.getWarpData(this.container).getDataStore("warps");
		this.loadWarps();
	}

	private void loadWarps()
	{
		this.folder.loadAllData().forEach(this::loadWarpFromData);
	}

	private void loadWarpFromData(PersistentData data)
	{
		String name = this.loadWarpName(data);
		Location location = this.loadLocation(data);
		Warp warp = Warp.builder()
				.setName(name)
				.setLocation(location)
				.build();
		this.activateWarp(warp);
	}

	private String loadWarpName(PersistentData data)
	{
		String name = data.getString("name");
		return name == null ? data.getName() : name;
	}

	private Location loadLocation(PersistentData data)
	{
		String locationString = data.getString("location");
		if (locationString == null)
		{
			return LocationUtils.getDefaultLocation();
		}
		return LocationUtils.fromString(locationString);
	}

	@Override
	public void setWarp(Warp warp)
	{
		Objects.requireNonNull(warp, "warp");

		String name = warp.getName();
		this.deleteWarp(name);
		this.activateWarp(warp);
		PersistentData data = this.folder.getData(name);
		data.set("name", name);
		data.set("location", LocationUtils.toString(warp.getLocation()));
		data.save();
	}

	@Override
	public void deleteWarp(String name)
	{
		Warp deleted = this.warps.remove(name);
		if (deleted == null)
		{
			return;
		}

		this.folder.deleteData(deleted.getName());
	}

	@Override
	public Warp getWarp(String name)
	{
		return this.warps.get(name);
	}

	@Override
	public List<Warp> getWarps()
	{
		return Collections.unmodifiableList(new ArrayList<>(this.warps.values()));
	}

	private void activateWarp(Warp warp)
	{
		this.warps.put(warp.getName(), warp);
	}

}