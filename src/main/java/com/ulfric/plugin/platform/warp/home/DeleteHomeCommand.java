package com.ulfric.plugin.platform.warp.home;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.WarpAccount;
import com.ulfric.commons.spigot.warp.Warps;

@Name("deletehome")
@Alias("removehome")
@Permission("deletehome-use")
@MustBePlayer
class DeleteHomeCommand implements Command {
	
	@Argument
	private String name;
	
	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		String name = this.name;

		WarpAccount account = Warps.getService().getAccount(player.getUniqueId());
		Text text = Text.getService();

		if (account.isWarp(name))
		{
			account.deleteWarp(name);

			text.sendMessage(player, "deletehome-use",
					MetadataDefaults.LAST_HOME_DELETED, name);
		}
		else
		{
			text.sendMessage(player, "deletehome-not-set",
					MetadataDefaults.LAST_HOME_DELETED, name);
		}
	}
	
}
