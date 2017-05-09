package com.ulfric.plugin.platform.cooldown;

import com.ulfric.commons.spigot.cooldown.Cooldown;
import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.PersistentData;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

final class PersistentGlobalAccount extends SimpleCooldownAccount {
	
	private final Object lock = new Object();
	private final DataSection dataSection;
	
	PersistentGlobalAccount(PersistentData data)
	{
		super(UUID.randomUUID());
		this.dataSection = data.createSection("cooldowns");
		this.loadCooldowns();
	}
	
	private void loadCooldowns()
	{
		this.dataSection.getSections().forEach(this::loadCooldown);
	}
	
	private void loadCooldown(DataSection section)
	{
		String name = section.getString("name");
		
		Cooldown cooldown = Cooldown.builder()
				.setName(name)
				.setUniqueId(this.getUniqueId())
				.setStart(Instant.ofEpochMilli(new Date(this.dataSection.getLong("start")).getTime()))
				.setExpiry(Instant.ofEpochMilli(new Date(this.dataSection.getLong("expiry")).getTime()))
				.build();
		
		super.setCooldown(cooldown);
	}
	
	@Override
	protected void removeCooldown(String name)
	{
		synchronized (this.lock)
		{
			this.dataSection.set(name, null);
			super.removeCooldown(name);
		}
	}

	@Override
	public void setCooldown(Cooldown cooldown)
	{
		synchronized (this.lock)
		{
			DataSection data = this.dataSection.getSection(cooldown.getName());
			
			data.set("start", cooldown.getStart().toEpochMilli());
			data.set("expiry", cooldown.getExpiry().toEpochMilli());
			
			super.setCooldown(cooldown);
		}
	}

}
