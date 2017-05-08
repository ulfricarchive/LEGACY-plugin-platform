package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ulfric.commons.exception.Try;

final class YamlPersistentData extends PersistentBukkitConfigurationDelegator<FileConfiguration> {

	private static FileConfiguration getFileConfiguration(Path file)
	{
		return Try.toWithResources(() -> Files.newBufferedReader(file),
				YamlConfiguration::loadConfiguration);
	}

	private final String name;
	private final Path file;

	YamlPersistentData(Path file)
	{
		super(YamlPersistentData.getFileConfiguration(file));
		this.file = file;
		this.name = this.resolveName();
	}

	private String resolveName()
	{
		String name = this.file.getFileName().toString();
		int lastIndex = name.lastIndexOf('.');
		if (lastIndex == -1)
		{
			return name;
		}
		return name.substring(0, lastIndex);
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void save()
	{
		if (this.modified)
		{
			this.unmarkForWrite();
			this.forceSave();
		}
	}

	private void forceSave()
	{
		Try.toWithResources(() -> Files.newBufferedWriter(this.file), writer ->
		{
			writer.write(this.data.saveToString());
			return null;
		});
	}

}