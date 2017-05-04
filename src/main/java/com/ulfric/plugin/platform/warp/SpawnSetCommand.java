package com.ulfric.plugin.platform.warp;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Spawn;

@Name("set")
@Permission("spawn-set")
@MustBePlayer
class SpawnSetCommand extends SpawnCommand {

	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		Spawn.getService().setSpawn(player.getLocation());
		Text.getService().sendMessage(player, "spawn-set");
	}

}