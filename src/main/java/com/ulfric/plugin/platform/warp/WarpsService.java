package com.ulfric.plugin.platform.warp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.warp.WarpAccount;
import com.ulfric.commons.spigot.warp.Warps;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;

final class WarpsService implements Warps {

	@Inject
	private Container container;

	private DataStore accountsFolder;
	private DataStore globalFolder;

	private final Map<UUID, WarpAccount> accounts = new HashMap<>();
	private WarpAccount global;

	@Initialize
	private void initialize()
	{
		this.accountsFolder = PlayerData.getPlayerData(this.container);
		this.globalFolder = Data.getDataStore(this.container).getDataStore("global");
		this.loadAccounts();
	}

	private void loadAccounts()
	{
		this.global = new PersistentWarpAccount(this.globalFolder);
	}

	@Override
	public WarpAccount getGlobalAccount()
	{
		return this.global;
	}

	@Override
	public WarpAccount getAccount(UUID uniqueId)
	{
		return this.accounts.computeIfAbsent(uniqueId, this::createWarpAccount);
	}

	private WarpAccount createWarpAccount(UUID uniqueId)
	{
		return new PersistentWarpAccount(this.accountsFolder.getDataStore(uniqueId.toString()));
	}

}