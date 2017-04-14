package com.ulfric.plugin.platform.data;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.dragoon.scope.Shared;

@Shared
class DataService implements Data {

	@Inject
	private Plugin plugin;

	private final Map<String, DataStore> datastores = new HashMap<>();

	@Override
	public DataStore getDataStore(String category)
	{
		return this.datastores.computeIfAbsent(category, this::createDataStore);
	}

	private DataStore createDataStore(String category)
	{
		Path directory = this.getDirectory(category);
		return YamlDataStore.newInstance(directory);
	}

	private Path getDirectory(String category)
	{
		return this.plugin.getDataFolder().toPath().resolve(category);
	}

}