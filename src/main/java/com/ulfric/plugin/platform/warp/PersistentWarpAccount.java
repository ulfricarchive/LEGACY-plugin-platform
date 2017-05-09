package com.ulfric.plugin.platform.warp;

import org.bukkit.Location;

import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.location.LocationUtils;
import com.ulfric.commons.spigot.warp.Warp;

public class PersistentWarpAccount extends SimpleWarpAccount {

	private final DataStore account;

	public PersistentWarpAccount(DataStore account)
	{
		this.account = account;
		this.loadWarps();
	}

	private void loadWarps()
	{
		this.account.loadAllData().forEach(this::loadWarp);
	}

	private void loadWarp(DataSection data)
	{
		String name = this.readWarpName(data);
		Location location = this.readLocation(data);
		Warp warp = Warp.builder()
				.setName(name)
				.setLocation(location)
				.build();
		super.setWarp(warp);
	}

	private String readWarpName(DataSection data)
	{
		String name = data.getString("name");
		return name == null ? data.getName() : name;
	}

	private Location readLocation(DataSection data)
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
		this.deleteWarp(warp.getName());
		super.setWarp(warp);

		String name = warp.getName();
		PersistentData data = this.account.getData(name);
		data.set("name", name);
		data.set("location", LocationUtils.toString(warp.getLocation()));
		data.save();
	}

	@Override
	public void deleteWarp(String name)
	{
		super.deleteWarp(name);
		this.account.deleteData(name);
	}

}