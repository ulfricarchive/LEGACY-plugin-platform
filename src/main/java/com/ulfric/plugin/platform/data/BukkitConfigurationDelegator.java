package com.ulfric.plugin.platform.data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import com.ulfric.commons.identity.UniqueIdUtils;
import com.ulfric.commons.spigot.data.DataSection;

abstract class BukkitConfigurationDelegator<T extends ConfigurationSection> implements DataSection {

	protected final T data;

	BukkitConfigurationDelegator(T data)
	{
		this.data = data;
	}

	@Override
	public String getName()
	{
		return this.data.getName();
	}

	@Override
	public void set(String path, Object value)
	{
		this.data.set(path, value);
	}

	@Override
	public Object getObject(String path)
	{
		return this.data.get(path);
	}

	@Override
	public String getString(String path)
	{
		return this.data.getString(path);
	}

	@Override
	public String getString(String path, String defaultValue)
	{
		return this.data.getString(path, defaultValue);
	}

	@Override
	public List<String> getStringList(String path)
	{
		return this.data.getStringList(path);
	}

	@Override
	public int getInt(String path)
	{
		return this.data.getInt(path);
	}

	@Override
	public int getInt(String path, int defaultValue)
	{
		return this.data.getInt(path, defaultValue);
	}

	@Override
	public long getLong(String path)
	{
		return this.data.getLong(path);
	}

	@Override
	public long getLong(String path, long defaultValue)
	{
		return this.data.getLong(path, defaultValue);
	}

	@Override
	public boolean getBoolean(String path)
	{
		return this.data.getBoolean(path);
	}

	@Override
	public boolean getBoolean(String path, boolean defaultValue)
	{
		return this.data.getBoolean(path, defaultValue);
	}

	@Override
	public UUID getUniqueId(String path)
	{
		return UniqueIdUtils.parseUniqueId(this.getString(path));
	}

	@Override
	public Set<String> getKeys()
	{
		return this.data.getKeys(false);
	}

	@Override
	public boolean contains(String key)
	{
		return this.data.contains(key);
	}

}