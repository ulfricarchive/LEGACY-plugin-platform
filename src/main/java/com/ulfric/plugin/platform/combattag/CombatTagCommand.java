package com.ulfric.plugin.platform.combattag;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.combattag.CombatTag;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.text.Text;

@Name("combattag")
@Alias("ct")
@MustBePlayer
public class CombatTagCommand implements Command {

	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		Text text = Text.getService();
		if (CombatTag.getService().isMarked(player.getUniqueId()))
		{
			text.sendMessage(player, "combat-tag-currently-marked");
			return;
		}
		text.sendMessage(player, "combat-tag-not-currently-marked");
	}

}