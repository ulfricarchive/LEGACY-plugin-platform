package com.ulfric.plugin.platform.warp.home;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.warp.home.Homes;

@Name("homes")
@Permission("homes-use")
@MustBePlayer
public class HomesCommand implements Command {
	
	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		Homes.getService().openHomesPanel(player);
	}
	
}
