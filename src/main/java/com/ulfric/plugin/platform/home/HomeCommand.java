package com.ulfric.plugin.platform.home;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.home.Home;
import com.ulfric.commons.spigot.home.HomeAccount;
import com.ulfric.commons.spigot.home.Homes;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Teleport;
import org.bukkit.entity.Player;

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
		HomeAccount account = Homes.getHomes().getAccount(player.getUniqueId());
		
		if (account.isHome(name))
		{
			Home home = account.getHome(name);
			text.sendMessage(player, "home-teleport", MetadataDefaults.LAST_HOME_TELEPORT, name);
			Teleport.getService().teleport(player, home.getLocation());
		}
		else
		{
			text.sendMessage(player, "home-no-set");
		}
	}
	
}
