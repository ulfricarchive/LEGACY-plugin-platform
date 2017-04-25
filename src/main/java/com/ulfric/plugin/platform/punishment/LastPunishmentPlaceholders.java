package com.ulfric.plugin.platform.punishment;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.punishment.Punishment;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

final class LastPunishmentPlaceholders {

	private static Punishment lastPunishment;
	private static String lastPunished;

	public static void setLastPunishment(Punishment punishment)
	{
		LastPunishmentPlaceholders.lastPunishment = punishment;
	}

	public static void setLastPunished(String lastPunished)
	{
		LastPunishmentPlaceholders.lastPunished = lastPunished;
	}

	@Name("LAST_PUNISHED_NAME")
	static final class LastPunishedPlaceholder implements Placeholder
	{
		@Override
		public String apply(CommandSender to)
		{
			return LastPunishmentPlaceholders.lastPunished;
		}
	}

	@Name("LAST_PUNISHED_ID")
	static final class LastPunishedIdPlaceholder implements Placeholder
	{
		@Override
		public String apply(CommandSender to)
		{
			return LastPunishmentPlaceholders.lastPunishment.getPunishmentId().toString();
		}
	}

	@Name("LAST_PUNISHED_REASON")
	static final class LastPunishedReasonPlaceholder implements Placeholder
	{
		@Override
		public String apply(CommandSender to)
		{
			return LastPunishmentPlaceholders.lastPunishment.getReason();
		}
	}

}