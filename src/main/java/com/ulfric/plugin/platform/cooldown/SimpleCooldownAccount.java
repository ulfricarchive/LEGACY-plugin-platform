package com.ulfric.plugin.platform.cooldown;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.ulfric.commons.spigot.cooldown.Cooldown;
import com.ulfric.commons.spigot.cooldown.CooldownAccount;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

class SimpleCooldownAccount implements CooldownAccount {
	
	private final UUID uniqueId;
	private final Map<String, Cooldown> cooldowns = new CaseInsensitiveMap<>();
	
	SimpleCooldownAccount(UUID uniqueId)
	{
		this.uniqueId = uniqueId;
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
	
	@Override
	public Stream<Cooldown> getCooldowns()
	{
		return new ArrayList<>(this.cooldowns.values()).stream();
	}
	
	@Override
	public void setCooldown(Cooldown cooldown)
	{
		this.cooldowns.put(cooldown.getName(), cooldown);
	}
	
	@Override
	public UUID getUniqueId()
	{
		return this.uniqueId;
	}
	
	protected void removeCooldown(String name)
	{
		this.cooldowns.remove(name);
	}
	
}
