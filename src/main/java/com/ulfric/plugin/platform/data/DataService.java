package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.plugin.PluginUtils;
import com.ulfric.dragoon.scope.Shared;

@Shared
class DataService implements Data {

	private final Map<String, DataStore> datastores = new HashMap<>();

	@Override
	public DataStore getDataStore(String category)
	{
		return this.datastores.computeIfAbsent(category, this::createDataStore);
	}

	private DataStore createDataStore(String category)
	{
		Path directory = this.getDirectory(category);
		return new YamlDataStore(directory);
	}

	private Path getDirectory(String category)
	{
		Path directory = this.getPlugin().getDataFolder().toPath().resolve(category);

		if (Files.exists(directory))
		{
			if (!Files.isDirectory(directory))
			{
				throw new IllegalStateException(directory + " must be a directory!");
			}
		}
		else
		{
			Try.to(() -> Files.createDirectories(directory));
		}

		return directory;
	}

	private Plugin getPlugin()
	{
		return PluginUtils.getProvidingPlugin(this.getClass()).orElseThrow(NullPointerException::new);
	}

}