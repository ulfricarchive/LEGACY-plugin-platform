package com.ulfric.plugin.platform.cooldown;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import com.ulfric.commons.spigot.cooldown.Cooldown;
import com.ulfric.commons.spigot.cooldown.CooldownAccount;
import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.PersistentData;

final class PersistentCooldownAccount implements CooldownAccount {
	
	private final Object lock = new Object();
	private final UUID uniqueId;
	private final DataSection data;
	
	private final Map<String, Cooldown> cooldowns = new IdentityHashMap<>();
	
	PersistentCooldownAccount(UUID uniqueId, PersistentData data)
	{
		this.uniqueId = uniqueId;
		this.data = data.getSection("cooldown");
		this.loadCooldowns();
	}
	
	private void loadCooldowns()
	{
		this.data.getSections().forEach(this::loadCooldown);
	}
	
	private void loadCooldown(DataSection section)
	{
		String name = section.getString("name");
		
		Cooldown cooldown = Cooldown.builder()
				.setName(name)
				.setUniqueId(this.uniqueId)
				.setStart(Instant.ofEpochMilli(new Date(data.getLong("start")).getTime()))
				.setExpiry(Instant.ofEpochMilli(new Date(data.getLong("expiry")).getTime()))
				.build();
		
		this.cooldowns.put(name, cooldown);
	}
	
	@Override
	public boolean isCooldown(String name)
	{
		return this.getCooldown(name) != null;
	}
	
	@Override
	public Cooldown getCooldown(String name)
	{
		Cooldown cooldown = this.cooldowns.get(name);
		
		if (cooldown != null && cooldown.isExpired())
		{
			this.removeCooldown(name);
			cooldown = null;
		}
		
		return cooldown;
	}
	
	private void removeCooldown(String name)
	{
		synchronized (this.lock)
		{
			this.data.set(name, null);
			this.cooldowns.remove(name);
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
		String name = cooldown.getName();
		
		this.cooldowns.put(name, cooldown);
		
		synchronized (this.lock)
		{
			DataSection data = this.data.createSection(name);
			
			data.set("start", cooldown.getStart().toEpochMilli());
			data.set("expiry", cooldown.getExpiry().toEpochMilli());
		}
	}
	
	@Override
	public UUID getUniqueId()
	{
		return this.uniqueId;
	}
	
}
