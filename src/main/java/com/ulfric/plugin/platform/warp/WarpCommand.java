package com.ulfric.plugin.platform.warp;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.LocaleDefaults;
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
			Text.getService().sendMessage(player, "warp-not-found");
			return;
		}

		String permission = "warp-use" + warp.getName();
		if (!player.hasPermission(permission))
		{
			Text.getService().sendMessage(player, LocaleDefaults.PERMISSION_MISSING,
					MetadataDefaults.PERMISSION_FAILED, permission);
			return;
		}

		Teleport.getService().teleport(player, warp.getLocation());
	}
	
}