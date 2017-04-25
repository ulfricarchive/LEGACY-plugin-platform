package com.ulfric.plugin.platform.control;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;

@Name("uniqueidof")
@Permission("uniqueidof-use")
@Alias("uuidof")
public class UniqueIdOfCommand implements Command {
	
	@Argument
	private OfflinePlayer target;
	
	@Override
	public void run(Context context)
	{
		CommandSender sender = context.getSender();
		
		UUID uniqueId = this.target.getUniqueId();
		
		if (uniqueId == null)
		{
			Text.getService().sendMessage(sender, "uniqueidof-invalid");
			return;
		}
		
		Text.getService().sendMessage(sender, "uniqueidof-use",
				MetadataDefaults.LAST_UNIQUEIDOF_VIEW_ID, uniqueId.toString(),
				MetadataDefaults.LAST_UNIQUEIDOF_VIEW_NAME, this.target.getName());
	}
	
}
