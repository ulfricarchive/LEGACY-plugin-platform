package com.ulfric.plugin.platform.permissions;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;
import com.ulfric.commons.spigot.text.Text;

@Name("node")
@Alias({"permission", "perm"})
@Permission("permissions-entity-add-node")
public class PermissionsEntityAddParentCommand extends PermissionsEntityAddCommand {

	@Argument
	private PermissionEntity parent;

	@Override
	public void run(Context context)
	{
		this.target.add(this.parent);
		Permissions.getService().writePermissionEntity(this.target);
		CommandSender sender = context.getSender();
		Text.getService().sendMessage(sender, "permissions-entity-add-parent",
				MetadataDefaults.LAST_PERMISSION_TOUCH, this.target.getIdentity().toString(),
				MetadataDefaults.LAST_PERMISSION_TOUCH_PARENT, this.parent.getIdentity().toString());
	}

}