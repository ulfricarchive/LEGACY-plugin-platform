package com.ulfric.plugin.platform.cooldown;

import com.ulfric.commons.naming.Named;
import com.ulfric.commons.spigot.cooldown.Cooldown;
import com.ulfric.commons.spigot.cooldown.CooldownAccount;
import com.ulfric.commons.spigot.data.PersistentData;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

final class PersistentCooldownAccount implements CooldownAccount {
	
	private final Object lock = new Object();
	private final UUID uniqueId;
	private final PersistentData data;
	
	private final Map<Class<? extends Named>, Cooldown> cooldowns = new IdentityHashMap<>();
	
	public PersistentCooldownAccount(UUID uniqueId, PersistentData data)
	{
		this.uniqueId = uniqueId;
		this.data = data.getSection("cooldown");
	}
	
	@Override
	public boolean isCooldown(Class<? extends Named> owner)
	{
		return this.getCooldown(owner) != null;
	}
	
	@Override
	public Cooldown getCooldown(Class<? extends Named> owner)
	{
		String name = Named.getNameFromAnnotation(owner);
		
		Cooldown cooldown;
		
		if (this.cooldowns.containsKey(owner))
		{
			cooldown = this.cooldowns.get(owner);
		}
		else
		{
			cooldown = this.getCooldown(name, owner);
		}
		
		if (cooldown == null)
		{
			return null;
		}
		
		if (cooldown.isExpired())
		{
			this.removeCooldown(name);
			return null;
		}
		
		return this.cooldowns.computeIfAbsent(owner, ignore -> cooldown);
	}
	
	private Cooldown getCooldown(String name, Class<? extends Named> owner)
	{
		synchronized (this.lock)
		{
			PersistentData data = this.data.getSection(name);
			
			if (data == null)
			{
				return null;
			}
			
			return Cooldown.builder()
					.setOwner(owner)
					.setUniqueId(this.uniqueId)
					.setStart(Instant.ofEpochMilli(new Date(data.getLong("start")).getTime()))
					.setExpiry(Instant.ofEpochMilli(new Date(data.getLong("expiry")).getTime()))
					.build();
		}
	}
	
	private void removeCooldown(String name)
	{
		synchronized (this.lock)
		{
			this.data.set(name, null);
		}
	}
	
	@Override
	public Stream<Cooldown> getCooldowns()
	{
		return new ArrayList<>(this.cooldowns.values()).stream();
	}
	
	@Override
	public void setCooldown(Cooldown cooldown)
	{
		Class<? extends Named> owner = cooldown.getOwner();
		
		this.cooldowns.put(owner, cooldown);
		
		synchronized (this.lock)
		{
			PersistentData data = this.data.getSection(Named.getNameFromAnnotation(owner));
			
			data.set("start", cooldown.getStart().get(ChronoField.MILLI_OF_SECOND));
			data.set("expiry", cooldown.getExpiry().get(ChronoField.MILLI_OF_SECOND));
		}
	}
	
	@Override
	public UUID getUniqueId()
	{
		return this.uniqueId;
	}
	
}
