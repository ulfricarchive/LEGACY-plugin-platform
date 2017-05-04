package com.ulfric.plugin.platform.event.server;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ServerEventsContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(UlfricPluginDisableEventContainer.class);
		this.install(ServerShutdownEventContainer.class);
	}

}