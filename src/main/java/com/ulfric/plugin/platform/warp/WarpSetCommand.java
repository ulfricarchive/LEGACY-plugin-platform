package com.ulfric.plugin.platform.warp;

import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.WarpAccount;
import com.ulfric.commons.spigot.warp.Warps;

@Name("set")
@Alias({"add", "create"})
@Permission("warp-set")
@MustBePlayer
class WarpSetCommand extends WarpCommand {

	@Override
	public void run(Context context)
	{
		Player player = (Player) context.getSender();
		WarpAccount warps = Warps.getService().getGlobalAccount();

		Warp warp = Warp.builder()
				.setName(this.name)
				.setLocation(player.getLocation())
				.build();
		warps.setWarp(warp);

		Text.getService().sendMessage(player, "warp-set",
				MetadataDefaults.LAST_WARP, this.name);
	}

}