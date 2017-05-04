package com.ulfric.plugin.platform.event.player;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

class AsyncPlayerQuitEventContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(AsyncPlayerQuitEventListener.class);
	}

}