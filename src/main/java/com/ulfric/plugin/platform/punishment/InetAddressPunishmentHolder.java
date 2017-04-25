package com.ulfric.plugin.platform.punishment;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.network.InetAddressHash;

final class InetAddressPunishmentHolder extends BeanPunishmentHolder {

	private final String hash;

	public InetAddressPunishmentHolder(String hash)
	{
		this.hash = hash;
	}

	@Override
	public String getName()
	{
		return this.hash;
	}

	@Override
	public List<Player> getOnlinePlayers()
	{
		return InetAddressHash.getService().onlinePlayersForHash(this.hash).collect(Collectors.toList());
	}

}