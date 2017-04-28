package com.ulfric.plugin.platform.message;

import com.ulfric.commons.spigot.message.Messaging;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageService implements Messaging {
	
	private final Map<UUID, UUID> contacts = new ConcurrentHashMap<>();
	
	@Override
	public void setLastRecipients(UUID uniqueId, UUID recipient)
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
