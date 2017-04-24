package com.ulfric.plugin.platform.permissions;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.permissions.Permissions;
import com.ulfric.commons.spigot.text.Text;

@Name("node")
@Alias({"permission", "perm"})
@Permission("permissions-entity-add-node")
public class PermissionsEntityAddNodeCommand extends PermissionsEntityAddCommand {

	@Argument
	private String node;

	@Override
	public void run(Context context)
	{
		this.target.add(this.node);
		Permissions.getService().writePermissionEntity(this.target);
		CommandSender sender = context.getSender();
		Text.getService().sendMessage(sender, "permissions-entity-add-node",
				MetadataDefaults.LAST_PERMISSION_TOUCH, this.target.getIdentity().toString(),
				MetadataDefaults.LAST_PERMISSION_TOUCH_NODE, this.node);
	}

}