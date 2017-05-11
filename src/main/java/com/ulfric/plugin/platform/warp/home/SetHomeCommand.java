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
import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.WarpAccount;
import com.ulfric.commons.spigot.warp.Warps;
import com.ulfric.commons.spigot.warp.home.Homes;

@Name("sethome")
@Alias({"createhome", "newhome", "addhome", "shome"})
@Permission("sethome-use")
@MustBePlayer
class SetHomeCommand implements Command {
	
	@Argument
	private String name;
	
	@Override
	public void run(Context context)
	{
		Text text = Text.getService();
		Player player = (Player) context.getSender();
		String name = this.name;

		if (!Warp.isValidName(name))
		{
			text.sendMessage(player, "sethome-invalid-name",
					MetadataDefaults.LAST_HOME_SET, name);
			return;
		}

		WarpAccount account = Warps.getService().getAccount(player.getUniqueId());
		
		if (Homes.getService().getTotalHomes(player) <= account.getWarps().size())
		{
			text.sendMessage(player, "sethome-max");
		}
		else if (account.isWarp(name))
		{
			text.sendMessage(player, "sethome-already-set");
		}
		else
		{
			Warp home = Warp.builder()
					.setName(name)
					.setLocation(player.getLocation())
					.build();

			account.setWarp(home);

			text.sendMessage(player, "sethome-set",
					MetadataDefaults.LAST_HOME_SET, name);
		}
	}
	
}
