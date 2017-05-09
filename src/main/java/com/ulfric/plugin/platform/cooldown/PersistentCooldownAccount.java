package com.ulfric.plugin.platform.cooldown;

import com.ulfric.commons.spigot.cooldown.Cooldown;
import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.PersistentData;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

final class PersistentCooldownAccount extends SimpleCooldownAccount {
	
	private final Object lock = new Object();
	private final DataSection data;
	
	PersistentCooldownAccount(UUID uniqueId, PersistentData data)
	{
		super(uniqueId);
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
				.setUniqueId(this.getUniqueId())
				.setStart(Instant.ofEpochMilli(new Date(data.getLong("start")).getTime()))
				.setExpiry(Instant.ofEpochMilli(new Date(data.getLong("expiry")).getTime()))
				.build();
		
		super.setCooldown(cooldown);
	}
	
	@Override
	protected void removeCooldown(String name)
	{
		synchronized (this.lock)
		{
			this.data.set(name, null);
			super.removeCooldown(name);
		}
	}
	
	@Override
	public void setCooldown(Cooldown cooldown)
	{
		synchronized (this.lock)
		{
			DataSection data = this.data.createSection(cooldown.getName());
			
			data.set("start", cooldown.getStart().toEpochMilli());
			data.set("expiry", cooldown.getExpiry().toEpochMilli());
			
			super.setCooldown(cooldown);
		}
	}
	
}
