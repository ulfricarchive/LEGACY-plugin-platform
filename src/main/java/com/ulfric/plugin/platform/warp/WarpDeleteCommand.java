package com.ulfric.plugin.platform.warp;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.MustBePlayer;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.WarpAccount;

@Name("delete")
@Alias("remove")
@Permission("warp-delete")
@MustBePlayer
class WarpDeleteCommand extends WarpCommand {
	
	@Override
	public void run(Context context)
	{
		WarpAccount warps = ServiceUtils.getService(WarpsService.class).getGlobalAccount();
		warps.deleteWarp(this.name);
		Text.getService().sendMessage(context.getSender(), "warp-delete",
				MetadataDefaults.LAST_WARP, this.name);
	}
	
}
