package com.ulfric.plugin.platform.home;

import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.home.HomeAccount;
import com.ulfric.commons.spigot.home.Homes;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class HomeService implements Homes {
	
	@Inject
	private Container owner;
	
	private DataStore playerData;
	
	private final Map<UUID, HomeAccount> homes = new ConcurrentHashMap<>();
	
	@Initialize
	private void initialize()
	{
		this.playerData = PlayerData.getPlayerData(this.owner);
	}
	
	@Override
	public HomeAccount getAccount(UUID uuid)
	{
		return this.homes.computeIfAbsent(uuid, this::createHomeAccount);
	}
	
	private PersistentHomeAccount createHomeAccount(UUID uniqueId)
	{
		return new PersistentHomeAccount(uniqueId, this.getHomeData(uniqueId));
	}
	
	private PersistentData getHomeData(UUID uniqueId)
	{
		return this.playerData.getData(String.valueOf(uniqueId));
	}
	
}
