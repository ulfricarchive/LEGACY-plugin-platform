package com.ulfric.plugin.platform.event.server;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class UlfricPluginDisableEventContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(UlfricPluginDisableEventListener.class);
	}

}