package com.ulfric.plugin.platform.data;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import com.ulfric.commons.spigot.data.PersistentData;

abstract class BukkitConfigurationDelegator implements PersistentData {

	protected final ConfigurationSection data;

	public BukkitConfigurationDelegator(ConfigurationSection data)
	{
		this.data = data;
	}

	@Override
	public final String getName()
	{
		return this.data.getName();
	}

	@Override
	public final void set(String path, Object value)
	{
		this.markForWrite();
		this.data.set(path, value);
	}

	@Override
	public final PersistentData getSection(String path)
	{
		ConfigurationSection data = this.data.getConfigurationSection(path);
		if (data == null)
		{
			return null;
		}
		return new PersistentDataSubsection(this, data);
	}

	@Override
	public final Object getObject(String path)
	{
		return this.data.get(path);
	}

	@Override
	public final String getString(String path)
	{
		return this.data.getString(path);
	}

	@Override
	public final List<String> getStringList(String path)
	{
		return this.data.getStringList(path);
	}

	@Override
	public final int getInt(String path)
	{
		return this.data.getInt(path);
	}

	@Override
	public final int getInt(String path, int defaultValue)
	{
		return this.data.getInt(path, defaultValue);
	}

	@Override
	public final boolean getBoolean(String path)
	{
		return this.data.getBoolean(path);
	}

	@Override
	public final Set<String> getKeys()
	{
		return this.data.getKeys(false);
	}

}