package com.ulfric.plugin.platform.control;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.control.ClearChat;
import com.ulfric.commons.spigot.text.Text;

class ClearChatService implements ClearChat {

	private static final String EMPTY_CHAT = StringUtils.repeat('\n', 100);

	@Override
	public void clear()
	{
		Text text = Text.getService();
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if (!player.hasPermission("control-clearchat"))
			{
				player.sendMessage(ClearChatService.EMPTY_CHAT);
			}

			text.sendMessage(player, "control-chat-cleared");
		}
	}

}