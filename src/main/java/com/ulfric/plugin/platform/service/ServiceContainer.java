package com.ulfric.plugin.platform.service;

import com.ulfric.commons.spigot.plugin.RootObjectFactory;
import com.ulfric.commons.spigot.service.BukkitService;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ServiceContainer extends Container {

	@Initialize
	private void setup()
	{
		RootObjectFactory.getRootObjectFactory().bind(BukkitService.class).to(BukkitServiceScopeStrategy.class);
	}

}
