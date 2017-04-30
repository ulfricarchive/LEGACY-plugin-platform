package com.ulfric.plugin.platform.warp;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.ulfric.commons.spigot.warp.Spawn;
import com.ulfric.commons.spigot.warp.Teleport;
import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.Warps;

public final class SpawnService implements Spawn {
	
	private static final String DEFAULT_SPAWN_NAME = "spawn";
	
	@Override
	public void setSpawn(Location spawn)
	{
		Warps warps = Warps.getService();
		
		if (spawn == null)
		{
			warps.deleteWarp(SpawnService.DEFAULT_SPAWN_NAME);
			return;
		}

		Warp spawnWarp = Warp.builder()
				.setName(SpawnService.DEFAULT_SPAWN_NAME)
				.setLocation(spawn)
				.build();
		warps.setWarp(spawnWarp);
	}
	
	@Override
	public boolean isSpawnSet()
	{
		return this.getSpawn() != null;
	}
	
	@Override
	public void teleport(Entity entity)
	{
		Location spawn = this.getSpawn();

		if (spawn == null)
		{
			throw new IllegalStateException("Spawn is not set!");
		}

		Teleport.getService().teleport(entity, spawn);
	}

	public Location getSpawn()
	{
		Warp warp = Warps.getService().getWarp(SpawnService.DEFAULT_SPAWN_NAME);
		return warp == null ? null : warp.getLocation();
	}
	
}
