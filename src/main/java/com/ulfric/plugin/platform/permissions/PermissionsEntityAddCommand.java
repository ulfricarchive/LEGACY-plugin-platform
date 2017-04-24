package com.ulfric.plugin.platform.permissions;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;

@Name("add")
@Permission("permissions-entity-add")
public class PermissionsEntityAddCommand extends PermissionsEntityCommand {

	@Override
	public void run(Context context)
	{
		Text.getService().sendMessage(context.getSender(), "permissions-entity-add-help",
				MetadataDefaults.LAST_PERMISSION_TOUCH, this.target.getIdentity().toString());
	}

}