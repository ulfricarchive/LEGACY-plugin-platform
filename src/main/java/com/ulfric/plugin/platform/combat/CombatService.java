package com.ulfric.plugin.platform.combat;

import com.ulfric.commons.spigot.combat.Combat;
import com.ulfric.commons.spigot.combat.Encounter;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class CombatService implements Combat {
	
	@Inject
	private Container owner;
	
	private final Map<UUID, Encounter> encounters = new ConcurrentHashMap<>();
	
	private long length;
	
	@Initialize
	private void setup()
	{
		this.length = Data.getDataStore(this.owner).getData("config").getLong("combat-log-length");
	}
	
	@Override
	public void setCombat(UUID uniqueId, Encounter encounter)
	{
		this.encounters.put(uniqueId, encounter);
	}
	
	@Override
	public boolean inCombat(UUID uniqueId)
	{
		return this.getEncounter(uniqueId) != null;
	}
	
	@Override
	public Encounter getEncounter(UUID uniqueId)
	{
		Encounter encounter = this.encounters.get(uniqueId);
		
		if (encounter != null && encounter.isExpired())
		{
			this.encounters.remove(uniqueId);
			return null;
		}
		
		return encounter;
	}
	
	@Override
	public void removeCombat(UUID uniqueId)
	{
		this.encounters.remove(uniqueId);
	}
	
	@Override
	public Stream<Encounter> getEncounters()
	{
		return new ArrayList<>(this.encounters.values()).stream();
	}
	
	public long getLength()
	{
		return this.length;
	}
	
}
