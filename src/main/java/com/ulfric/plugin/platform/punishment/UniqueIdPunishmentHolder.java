package com.ulfric.plugin.platform.punishment;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

final class UniqueIdPunishmentHolder extends BeanPunishmentHolder {

	private final UUID uniqueId;
	private String name;

	public UniqueIdPunishmentHolder(UUID uniqueId)
	{
		this.uniqueId = uniqueId;
	}

	@Override
	public String getName()
	{
		if (this.name == null)
		{
			this.name = Bukkit.getOfflinePlayer(uniqueId).getName();
		}
		return this.name;
	}

	@Override
	public List<Player> getOnlinePlayers()
	{
		Player onlinePlayer = Bukkit.getPlayer(this.uniqueId);
		return onlinePlayer == null ? Collections.emptyList() : Collections.singletonList(onlinePlayer);
	}

}