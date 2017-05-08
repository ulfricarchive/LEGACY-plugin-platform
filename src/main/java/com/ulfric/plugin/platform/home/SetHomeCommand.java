package com.ulfric.plugin.platform.home;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
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
import org.bukkit.entity.Player;

@Name("sethome")
@Alias("createhome")
@Permission("sethome-use")
@MustBePlayer
class SetHomeCommand implements Command {
	
	@Argument
	private String name;
	
	// todo check if the user can set more homes
	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		String name = this.name;
		
		HomeAccount account = Homes.getHomes().getAccount(player.getUniqueId());
		Text text = Text.getService();
		
		if (account.isHome(name))
		{
			text.sendMessage(player, "sethome-already-set");
		}
		else
		{
			Home home = Home.builder()
					.setName(name)
					.setLocation(player.getLocation())
					.build();
			
			account.setHome(home);
			
			text.sendMessage(player, "sethome-set", MetadataDefaults.LAST_HOME_SET, name);
		}
	}
	
}
