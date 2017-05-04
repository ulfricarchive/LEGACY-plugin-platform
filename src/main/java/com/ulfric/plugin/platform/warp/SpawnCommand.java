package com.ulfric.plugin.platform.warp;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Spawn;

@Name("spawn")
@MustBePlayer
class SpawnCommand implements Command {

	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();

		Spawn spawn = Spawn.getService();

		if (spawn.isSpawnSet())
		{
			spawn.teleport(player);
			return;
		}

		Text.getService().sendMessage(player, "spawn-not-set");
	}

}