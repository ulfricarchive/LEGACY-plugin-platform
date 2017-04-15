package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.spigot.data.PersistentData;

public final class YamlPersistentData implements PersistentData {

	private final FileConfiguration data;
	private final Path file;
	private boolean needsWrite;

	public YamlPersistentData(Path file)
	{
		this.file = file;
		this.data = Try.toWithResources(() -> Files.newBufferedReader(file),
				reader -> YamlConfiguration.loadConfiguration(reader));
	}

	@Override
	public void markForWrite()
	{
		this.needsWrite = true;
	}

	@Override
	public void unmarkForWrite()
	{
		this.needsWrite = false;
	}

	@Override
	public void save()
	{
		if (this.needsWrite)
		{
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

	@Override
	public String getName()
	{
		return this.data.getName();
	}

	@Override
	public void set(String path, Object value)
	{
		this.markForWrite();
		this.data.set(path, value);
	}

	@Override
	public PersistentData getSection(String path)
	{
		ConfigurationSection data = this.data.getConfigurationSection(path);
		if (data == null)
		{
			return null;
		}
		return new PersistentDataSubsection(this, data);
	}

	@Override
	public String getString(String path)
	{
		return this.data.getString(path);
	}

	@Override
	public int getInt(String path)
	{
		return this.data.getInt(path);
	}

	@Override
	public Set<String> getKeys()
	{
		return this.data.getKeys(false);
	}

}