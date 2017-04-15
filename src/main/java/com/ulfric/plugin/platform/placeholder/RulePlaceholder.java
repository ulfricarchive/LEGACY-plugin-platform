package com.ulfric.plugin.platform.placeholder;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

@Name("RULE_FAILED")
public class RulePlaceholder implements Placeholder {

	@Override
	public String apply(CommandSender sender)
	{
		return Metadata.readString(sender, "rule-failed");
	}

}
