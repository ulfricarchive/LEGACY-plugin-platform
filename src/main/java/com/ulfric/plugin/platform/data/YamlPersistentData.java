package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ulfric.commons.exception.Try;

public final class YamlPersistentData extends BukkitConfigurationDelegator {

	private static FileConfiguration getFileConfiguration(Path file)
	{
		return Try.toWithResources(() -> Files.newBufferedReader(file),
				reader -> YamlConfiguration.loadConfiguration(reader));
	}

	private final FileConfiguration data;
	private final String name;
	private final Path file;
	private boolean needsWrite;

	public YamlPersistentData(Path file)
	{
		super(YamlPersistentData.getFileConfiguration(file));
		this.file = file;
		this.data = (FileConfiguration) super.data;
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