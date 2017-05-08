package com.ulfric.plugin.platform.cooldown;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.ulfric.commons.spigot.cooldown.CooldownAccount;
import com.ulfric.commons.spigot.cooldown.Cooldowns;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;

class CooldownService implements Cooldowns {
	
	@Inject
	private Container owner;
	
	private DataStore playerData;
	
	private final Map<UUID, CooldownAccount> cooldowns = new ConcurrentHashMap<>();
	private CooldownAccount global;
	
	@Initialize
	private void initialize()
	{
		this.playerData = PlayerData.getPlayerData(this.owner);
		
		DataStore dataStore = Data.getDataStore(this.owner).getDataStore("global");
		this.global = new PersistentGlobalAccount(dataStore.getData("cooldowns"));
	}
	
	@Override
	public CooldownAccount getAccount(UUID uniqueId)
	{
		return this.cooldowns.computeIfAbsent(uniqueId, this::createCooldownAccount);
	}
	
	@Override
	public CooldownAccount getGlobalAccount()
	{
		return this.global;
	}
	
	private CooldownAccount createCooldownAccount(UUID uniqueId)
	{
		return new PersistentCooldownAccount(uniqueId, this.getCooldownData(uniqueId));
	}
	
	private PersistentData getCooldownData(UUID uniqueId)
	{
		return this.playerData.getData(String.valueOf(uniqueId));
	}
	
}
