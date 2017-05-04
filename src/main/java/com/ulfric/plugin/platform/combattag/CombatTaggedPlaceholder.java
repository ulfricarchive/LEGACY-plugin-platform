package com.ulfric.plugin.platform.combattag;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.combattag.CombatTag;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.text.placeholder.PlayerPlaceholder;

@Name("COMBAT_TAGGED")
final class CombatTaggedPlaceholder implements PlayerPlaceholder {
	
	@Override
	public String apply(Player player)
	{
		boolean tagged = CombatTag.getService().isMarked(player.getUniqueId());

		Text text = Text.getService();

		if (tagged)
		{
			return text.getPlainMessage(player, "combat-tag-marked-placeholder");
		}

		return text.getPlainMessage(player, "combat-tag-not-marked-placeholder");
	}
	
}
