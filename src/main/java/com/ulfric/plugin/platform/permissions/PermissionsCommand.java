package com.ulfric.plugin.platform.permissions;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.text.Text;

@Name("permissions")
@Alias({"permission", "perms", "perm"})
@Permission("permissions")
class PermissionsCommand implements Command {

	@Override
	public void run(Context context)
	{
		Text.getService().sendMessage(context.getSender(), "permissions-help");
	}

}