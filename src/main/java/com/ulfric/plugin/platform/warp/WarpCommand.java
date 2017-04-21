package com.ulfric.plugin.platform.warp;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Teleport;
import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.Warps;

@Name("warp")
@MustBePlayer
public class WarpCommand implements Command {
	
	@Argument
	protected String name;
	
	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();

		Warp warp = Warps.getService().getWarp(this.name);
		if (warp == null)
		{
			Text.getService().sendMessage(player, "warp-invalid");
			return;
		}

		if (!player.hasPermission("warp-use-" + warp.getName()))
		{
			Text.getService().sendMessage(player, "warp-no-permission");
			return;
		}

		Teleport.getService().teleport(player, warp.getLocation());
	}
	
}