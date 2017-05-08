package com.ulfric.plugin.platform.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.PersistentData;

abstract class PersistentBukkitConfigurationDelegator<T extends ConfigurationSection> extends BukkitConfigurationDelegator<T> implements PersistentData {

	protected boolean modified;

	PersistentBukkitConfigurationDelegator(T data)
	{
		super(data);
	}

	@Override
	public void set(String path, Object value)
	{
		super.set(path, value);
		this.markForWrite();
	}

	@Override
	public DataSection getSection(String path)
	{
		ConfigurationSection data = this.data.getConfigurationSection(path);
		if (data == null)
		{
			return null;
		}
		return new PersistentDataSubsection(this, data);
	}

	@Override
	public List<DataSection> getSections()
	{
		List<DataSection> data = new ArrayList<>();
		for (String key : this.getKeys())
		{
			Object value = this.getObject(key);

			if (value instanceof ConfigurationSection)
			{
				data.add(new PersistentDataSubsection(this, (ConfigurationSection) value));
			}
		}
		return data;
	}

	@Override
	public DataSection createSection(String key)
	{
		this.markForWrite();
		return new PersistentDataSubsection(this, this.data.createSection(key));
	}

	@Override
	public void markForWrite()
	{
		this.modified = true;
	}

	@Override
	public void unmarkForWrite()
	{
		this.modified = false;
	}

}
