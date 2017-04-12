package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.data.PlayerData;
import com.ulfric.commons.spigot.plugin.PluginUtils;

class PlayerDataService implements PlayerData {

	private final Map<String, DataStore> datastores = new HashMap<>();
	private final DataStore playerData = this.getDataStore("players");

	@Override
	public PersistentData getData(UUID pointer)
	{
		return this.playerData.getData(pointer);
	}

	@Override
	public DataStore getDataStore()
	{
		return this.playerData;
	}

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