package com.ulfric.plugin.platform.message;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.message.Messaging;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;

public class MessageService implements Messaging {
	
	private final Map<UUID, UUID> contacts = new ConcurrentHashMap<>();
	
	@Override
	public void send(Player sender, Player receiver, String content)
	{
		Text text = Text.getService();
		
		if (sender.getUniqueId().equals(receiver.getUniqueId()))
		{
			text.sendMessage(sender, "message-send-self",
					MetadataDefaults.LAST_MESSAGE_CONTENT, content,
					MetadataDefaults.LAST_MESSAGE_SENDER, sender.getName(),
					MetadataDefaults.LAST_MESSAGE_RECEIVER, sender.getName());
		}
		else
		{
			text.sendMessage(receiver, "message-receive",
					MetadataDefaults.LAST_MESSAGE_CONTENT, content,
					MetadataDefaults.LAST_MESSAGE_SENDER, sender.getName());
			
			text.sendMessage(sender, "message-send",
					MetadataDefaults.LAST_MESSAGE_CONTENT, content,
					MetadataDefaults.LAST_MESSAGE_RECEIVER, receiver.getName());
		}
		
		this.setLastRecipients(sender.getUniqueId(), receiver.getUniqueId());
	}
	
	@Override
	public void reply(Player sender, Player receiver, String content)
	{
		Text text = Text.getService();
		
		if (sender.getUniqueId().equals(receiver.getUniqueId()))
		{
			text.sendMessage(sender, "reply-send-self",
					MetadataDefaults.LAST_MESSAGE_REPLY_CONTENT, content,
					MetadataDefaults.LAST_MESSAGE_REPLY_SENDER, sender.getName(),
					MetadataDefaults.LAST_MESSAGE_REPLY_RECEIVER, sender.getName());
		}
		else
		{
			text.sendMessage(receiver, "reply-receive",
					MetadataDefaults.LAST_MESSAGE_REPLY_CONTENT, content,
					MetadataDefaults.LAST_MESSAGE_REPLY_SENDER, sender.getName());
			
			text.sendMessage(sender, "reply-send",
					MetadataDefaults.LAST_MESSAGE_REPLY_CONTENT, content,
					MetadataDefaults.LAST_MESSAGE_REPLY_RECEIVER, receiver.getName());
		}
		
	}
	
	private void setLastRecipients(UUID uniqueId, UUID recipient)
	{
		this.contacts.put(uniqueId, recipient);
	}
	
	@Override
	public UUID getLastRecipient(UUID uniqueId)
	{
		return this.contacts.get(uniqueId);
	}
	
	@Override
	public boolean hasLastRecipient(UUID uniqueId)
	{
		return this.contacts.containsKey(uniqueId);
	}
	
	@Override
	public void clear(UUID uuid)
	{
		this.contacts.remove(uuid);
	}
	
}
