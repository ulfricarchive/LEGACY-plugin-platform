package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.spigot.data.PersistentData;

public final class YamlPersistentData implements PersistentData {

	private final FileConfiguration data;
	private final Path file;
	private boolean modified;

	public YamlPersistentData(Path file)
	{
		this.file = file;
		this.data = Try.toWithResources(() -> Files.newBufferedReader(file),
				reader -> YamlConfiguration.loadConfiguration(reader));
	}

	@Override
	public void save()
	{
		if (this.modified)
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
	public void set(String path, Object value)
	{
		this.modified = true;
		this.data.set(path, value);
	}

	@Override
	public Object get(String path)
	{
		return this.data.get(path);
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

}