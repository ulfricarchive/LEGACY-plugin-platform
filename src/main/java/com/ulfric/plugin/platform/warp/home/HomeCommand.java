package com.ulfric.plugin.platform.warp.home;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Teleport;
import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.WarpAccount;
import com.ulfric.commons.spigot.warp.Warps;

@Name("home")
@Permission("home-use")
@MustBePlayer
class HomeCommand implements Command {
	
	@Argument
	private String name;
	
	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		String name = this.name;
		
		Text text = Text.getService();
		WarpAccount account = Warps.getService().getAccount(player.getUniqueId());
		Warp home = account.getWarp(name);

		if (home != null)
		{
			name = home.getName();
			text.sendMessage(player, "home-teleport", MetadataDefaults.LAST_HOME_TELEPORT, name);
			Teleport.getService().teleport(player, home.getLocation());
		}
		else
		{
			text.sendMessage(player, "home-not-set",
					MetadataDefaults.LAST_HOME_TELEPORT, name);
		}
	}
	
}
