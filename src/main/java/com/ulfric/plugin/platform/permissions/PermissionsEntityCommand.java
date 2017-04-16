package com.ulfric.plugin.platform.permissions;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.text.Text;

@Name("entity")
@Alias({"ent", "user", "group"})
@Permission("permissions-entity")
public class PermissionsEntityCommand extends PermissionsCommand {

	@Argument
	protected PermissionEntity target;

	@Override
	public void run(Context context)
	{
		Metadata.write(context.getSender(), MetadataDefaults.LAST_PERMISSION_TOUCH, this.target.getIdentity());
		Text.getService().sendMessage(context.getSender(), "permissions-entity");
	}

}