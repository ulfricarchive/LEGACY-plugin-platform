package com.ulfric.plugin.platform.punishment;

import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.network.InetAddressHash;
import com.ulfric.commons.spigot.punishment.Punisher;
import com.ulfric.commons.spigot.punishment.PunishmentHolder;
import com.ulfric.commons.spigot.punishment.Punishments;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class PunishmentsService implements Punishments {

	@Inject
	private Container owner;

	private Punisher console;
	private Punisher plugin;

	private final Map<String, PunishmentHolder> addressHashToHolder = new ConcurrentHashMap<>();
	private final Map<UUID, PunishmentHolder> uniqueIdToHolder = new ConcurrentHashMap<>();

	@Initialize
	private void initialize()
	{
		PersistentData config = Data.getDataStore(this.owner).getDefault();
		this.console = SimplePunisher.of(config.getString("punisher.console", "~CONSOLE~"));
		this.plugin = SimplePunisher.of(config.getString("punisher.plugin", "Ministry of Love"));
	}

	@Override
	public Punisher getConsolePunisher()
	{
		return this.console;
	}

	@Override
	public Punisher getPluginPunisher()
	{
		return this.plugin;
	}

	@Override
	public Punisher getPunisher(UUID uniqueId)
	{
		return this.getPunishmentHolder(uniqueId);
	}

	@Override
	public PunishmentHolder getPunishmentHolder(UUID uniqueId)
	{
		return this.uniqueIdToHolder.computeIfAbsent(uniqueId, UniqueIdPunishmentHolder::new);
	}

	@Override
	public PunishmentHolder getPunishmentHolder(InetAddress address)
	{
		String hash = InetAddressHash.getService().getHash(address);
		return this.addressHashToHolder.computeIfAbsent(hash, InetAddressPunishmentHolder::new);
	}

}