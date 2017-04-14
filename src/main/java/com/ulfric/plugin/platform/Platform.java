package com.ulfric.plugin.platform;

import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.plugin.platform.data.DataContainer;
import com.ulfric.plugin.platform.event.EventsContainer;
import com.ulfric.plugin.platform.permissions.PermissionsContainer;
import com.ulfric.plugin.platform.placeholder.PlaceholderContainer;
import com.ulfric.plugin.platform.server.ShutdownContainer;
import com.ulfric.plugin.platform.service.ServiceContainer;
import com.ulfric.plugin.platform.text.TextContainer;

public final class Platform extends UlfricPlugin {

	@Override
	public void init()
	{
		this.install(ServiceContainer.class);
		this.install(DataContainer.class);
		this.install(EventsContainer.class);
		this.install(PermissionsContainer.class);
		this.install(TextContainer.class);
		this.install(PlaceholderContainer.class);
		this.install(ShutdownContainer.class);
	}

}