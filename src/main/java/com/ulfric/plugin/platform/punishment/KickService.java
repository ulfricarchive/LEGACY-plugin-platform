package com.ulfric.plugin.platform.punishment;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.punishment.Kick;
import com.ulfric.commons.spigot.punishment.Punishment;
import com.ulfric.commons.spigot.punishment.PunishmentHolder;
import com.ulfric.commons.spigot.text.Text;
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
		PersistentData config = Data.getDataStore(this.owner).getData("config");
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
		this.broadcastKick(holder, punishment);
	}

	@Override
	public String getDefaultReason()
	{
		return this.defaultReason;
	}

	private void kickPunished(List<Player> punished, Punishment punishment)
	{
		Text text = Text.getService();
		for (Player player : punished)
		{
			String message = text.getPlainMessage(player, "kick-disconnect",
					MetadataDefaults.PUNISHMENT_ID, punishment.getPunishmentId().toString(),
					MetadataDefaults.PUNISHMENT_PUNISHER, punishment.getPunisher().getName(),
					MetadataDefaults.PUNISHMENT_REASON, punishment.getReason());
			player.kickPlayer(message);
		}
	}

	private void broadcastKick(PunishmentHolder holder, Punishment punishment)
	{
		this.setupKickMessage(holder, punishment);

		this.broadcastKick();

		this.cleanupKickMessage();
	}

	private void setupKickMessage(PunishmentHolder holder, Punishment punishment)
	{
		LastPunishmentPlaceholders.setLastPunishment(punishment);
		LastPunishmentPlaceholders.setLastPunished(holder.getName());
	}

	private void cleanupKickMessage()
	{
		LastPunishmentPlaceholders.setLastPunishment(null);
		LastPunishmentPlaceholders.setLastPunished(null);
	}

	private void broadcastKick()
	{
		Text text = Text.getService();
		for (Player player : Bukkit.getOnlinePlayers())
		{
			text.sendMessage(player, "kick-broadcast");
		}
		this.logKickToConsole();
	}

	private void logKickToConsole()
	{
		this.logger.info(Text.getService().getPlainMessage("kick-console"));
	}

	@Override
	public List<Punishment> lift(PunishmentHolder holder)
	{
		return Collections.emptyList();
	}

}