package com.ulfric.plugin.platform.punishment;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.punishment.Punishment;
import com.ulfric.commons.spigot.text.Text;

enum PunishmentUtils {

	;

	static String format(CommandSender to, Punishment punishment, String code)
	{
		return Text.getService().getPlainMessage(to, code,
				MetadataDefaults.PUNISHMENT_ID, punishment.getPunishmentId().toString(),
				MetadataDefaults.PUNISHMENT_PUNISHER, punishment.getPunisher().getName(),
				MetadataDefaults.PUNISHMENT_REASON, punishment.getReason());
	}

	static void broadcast(Logger console, Punishment punishment, String type)
	{
		String identifier = punishment.getPunishmentId().toString();
		String punisher = punishment.getPunisher().getName();
		String reason = punishment.getReason();

		String broadcast = type + "-broadcast";
		Text text = Text.getService();
		for (Player player : Bukkit.getOnlinePlayers())
		{
			text.sendMessage(player, broadcast,
					MetadataDefaults.PUNISHMENT_ID, identifier,
					MetadataDefaults.PUNISHMENT_PUNISHER, punisher,
					MetadataDefaults.PUNISHMENT_REASON, reason);
		}

		String consoleMessage =
				Text.getService().getPlainMessage(broadcast,
						MetadataDefaults.PUNISHMENT_ID, identifier,
						MetadataDefaults.PUNISHMENT_PUNISHER, punisher,
						MetadataDefaults.PUNISHMENT_REASON, reason);
		console.info(consoleMessage);
	}

}