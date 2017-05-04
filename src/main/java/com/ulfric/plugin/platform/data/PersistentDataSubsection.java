package com.ulfric.plugin.platform.data;

import org.bukkit.configuration.ConfigurationSection;

import com.ulfric.commons.spigot.data.PersistentData;

final class PersistentDataSubsection extends BukkitConfigurationDelegator {

	private final PersistentData parent;

	PersistentDataSubsection(PersistentData parent, ConfigurationSection data)
	{
		super(data);
		this.parent = parent;
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

}