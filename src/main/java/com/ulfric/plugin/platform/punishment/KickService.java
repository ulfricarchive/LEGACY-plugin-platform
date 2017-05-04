package com.ulfric.plugin.platform.punishment;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.punishment.Kick;
import com.ulfric.commons.spigot.punishment.Punishment;
import com.ulfric.commons.spigot.punishment.PunishmentHolder;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class KickService implements Kick {

	@Inject
	private Container owner;

	@Inject
	private Logger logger;

	private String defaultReason;

	@Initialize
	private void initialize()
	{
		PersistentData config = Data.getDataStore(this.owner).getDefault();
		this.defaultReason = config.getString("default-reason", "The kick hammer has spoken!");
	}

	@Override
	public Punishment getActive(PunishmentHolder holder)
	{
		return null;
	}

	@Override
	public void apply(PunishmentHolder holder, Punishment punishment)
	{
		List<Player> punished = holder.getOnlinePlayers();
		if (punished.isEmpty())
		{
			return;
		}

		this.kickPunished(punished, punishment);
		PunishmentUtils.broadcast(this.logger, punishment, "kick");
	}

	@Override
	public String getDefaultReason()
	{
		return this.defaultReason;
	}

	private void kickPunished(Iterable<Player> punished, Punishment punishment)
	{
		for (Player player : punished)
		{
			String message = PunishmentUtils.format(player, punishment, "kick-disconnect");
			player.kickPlayer(message);
		}
	}

	@Override
	public List<Punishment> lift(PunishmentHolder holder)
	{
		return Collections.emptyList();
	}

}