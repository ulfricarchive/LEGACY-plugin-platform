package com.ulfric.plugin.platform.data;

import org.bukkit.configuration.ConfigurationSection;

import com.ulfric.commons.data.Persistent;

final class PersistentDataSubsection extends PersistentBukkitConfigurationDelegator<ConfigurationSection> {

	PersistentDataSubsection(Persistent delegate, ConfigurationSection data)
	{
		super(data);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markForWrite() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unmarkForWrite() {
		// TODO Auto-generated method stub
		
	}

}