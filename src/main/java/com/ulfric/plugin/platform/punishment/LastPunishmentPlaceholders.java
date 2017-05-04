package com.ulfric.plugin.platform.punishment;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

final class LastPunishmentPlaceholders {

	@Name("LAST_PUNISHER_NAME")
	static class LastPunisherPlaceholder implements Placeholder
	{
		@Override
		public String apply(CommandSender to)
		{
			return Metadata.readString(to, MetadataDefaults.PUNISHMENT_PUNISHER);
		}
	}

	@Name("LAST_PUNISHED_NAME")
	static class LastPunishedPlaceholder implements Placeholder
	{
		@Override
		public String apply(CommandSender to)
		{
			return Metadata.readString(to, MetadataDefaults.PUNISHMENT_PUNISHED);
		}
	}

	@Name("LAST_PUNISHED_ID")
	static class LastPunishedIdPlaceholder implements Placeholder
	{
		@Override
		public String apply(CommandSender to)
		{
			return Metadata.readString(to, MetadataDefaults.PUNISHMENT_ID);
		}
	}

	@Name("LAST_PUNISHED_REASON")
	static class LastPunishedReasonPlaceholder implements Placeholder
	{
		@Override
		public String apply(CommandSender to)
		{
			return Metadata.readString(to, MetadataDefaults.PUNISHMENT_REASON);
		}
	}

}