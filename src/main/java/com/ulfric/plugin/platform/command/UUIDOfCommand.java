package com.ulfric.plugin.platform.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;

@Name("uuidof")
@Permission("uuidof-use")
public class UUIDOfCommand implements Command {
	
	@Argument
	private String name;
	
	@Override
	public void run(Context context)
	{
		CommandSender sender = context.getSender();
		
		UUID uuid = this.getUUID(this.name);
		
		if (uuid == null)
		{
			Text.getService().sendMessage(sender, "uuidof-invalid");
			return;
		}
		
		Metadata.write(sender, MetadataDefaults.LAST_UNIQUEIDOF_VIEW, uuid);
		Text.getService().sendMessage(sender, "uuidof-use");
	}
	
	private UUID getUUID(String username)
	{
		OfflinePlayer player;
		
		if ((player = Bukkit.getPlayer(username)) == null)
		{
			player = Bukkit.getOfflinePlayer(username);
		
			if (player == null || !player.hasPlayedBefore())
			{
				return null;
			}
		}
		
		return player.getUniqueId();
	}
	
}
