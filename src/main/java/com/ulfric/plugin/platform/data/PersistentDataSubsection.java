package com.ulfric.plugin.platform.data;

import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import com.ulfric.commons.spigot.data.PersistentData;

final class PersistentDataSubsection implements PersistentData {

	private final PersistentData parent;
	private final ConfigurationSection data;

	public PersistentDataSubsection(PersistentData parent, ConfigurationSection data)
	{
		this.parent = parent;
		this.data = data;
	}

	@Override
	public void save()
	{
		this.parent.save();
	}

	@Override
	public void markForWrite()
	{
		this.parent.markForWrite();
	}

	@Override
	public void unmarkForWrite()
	{
		throw new UnsupportedOperationException("Cannot unmark a subsection for write");
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
