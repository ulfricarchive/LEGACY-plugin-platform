package com.ulfric.plugin.platform.control;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.control.ClearChat;

@Name("clearchat")
@Permission("control-clearchat")
@Alias("cc")
class ClearChatCommand implements Command {

	@Override
	public void run(Context context)
	{
		ClearChat.getService().clear();
	}

}