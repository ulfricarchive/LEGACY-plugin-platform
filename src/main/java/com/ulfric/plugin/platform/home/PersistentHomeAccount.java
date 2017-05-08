package com.ulfric.plugin.platform.home;

import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.home.Home;
import com.ulfric.commons.spigot.home.HomeAccount;
import com.ulfric.commons.spigot.location.LocationUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class PersistentHomeAccount implements HomeAccount {
	
	private final Object lock = new Object();
	private final UUID uniqueId;
	private final DataSection dataSection;
	
	private final Map<String, Home> homes = new CaseInsensitiveMap<>();
	
	PersistentHomeAccount(UUID uniqueId, PersistentData data)
	{
		this.uniqueId = uniqueId;
		this.dataSection = data.getSection("homes");
		this.loadHomes();
	}
	
	private void loadHomes()
	{
		this.dataSection.getSections().forEach(this::loadHome);
	}
	
	private void loadHome(DataSection section)
	{
		String name = section.getString("name");
		Location location = LocationUtils.fromString(section.getString("location"));
		
		Home home = Home.builder()
				.setName(name)
				.setLocation(location)
				.build();
		
		this.homes.putIfAbsent(name, home);
	}
	
	@Override
	public boolean isHome(String name)
	{
		return this.getHome(name) != null;
	}
	
	@Override
	public Home getHome(String name)
	{
		return this.homes.get(name);
	}
	
	@Override
	public List<Home> getHomes()
	{
		return new ArrayList<>(this.homes.values());
	}
	
	@Override
	public void setHome(Home home)
	{
		String name = home.getName();
		
		this.homes.put(name, home);
		
		synchronized (this.lock)
		{
			DataSection section = this.dataSection.createSection(name);
			
			section.set("name", name);
			section.set("location", LocationUtils.toString(home.getLocation()));
		}
	}
	
	@Override
	public void deleteHome(String name)
	{
		this.homes.remove(name);
		
		synchronized (this.lock)
		{
			this.dataSection.set(name, null);
		}
	}
	
	@Override
	public UUID getUniqueId()
	{
		return this.uniqueId;
	}
	
}
