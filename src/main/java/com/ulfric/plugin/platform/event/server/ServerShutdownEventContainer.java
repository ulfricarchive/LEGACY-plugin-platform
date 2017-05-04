package com.ulfric.plugin.platform.event.server;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

class ServerShutdownEventContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(ServerShutdownEventListener.class);
	}

}