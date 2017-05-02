package com.ulfric.plugin.platform.combat;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.combat.Combat;
import com.ulfric.commons.spigot.combat.Encounter;
import com.ulfric.commons.spigot.text.placeholder.PlayerPlaceholder;
import com.ulfric.commons.text.FormatUtils;
import org.bukkit.entity.Player;

@Name("COMBAT_TIME_REMAINING")
public final class CombatPlaceholder implements PlayerPlaceholder {
	
	@Override
	public String apply(Player player)
	{
		Encounter encounter = Combat.getService().getEncounter(player.getUniqueId());
		
		if (encounter != null)
		{
			return FormatUtils.formatLong(encounter.getRemaining());
		}
		
		return null;
	}
	
}
