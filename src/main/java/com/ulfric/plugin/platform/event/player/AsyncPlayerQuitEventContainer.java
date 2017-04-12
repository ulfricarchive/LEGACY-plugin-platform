package com.ulfric.plugin.platform.event.player;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class AsyncPlayerQuitEventContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(AsyncPlayerQuitEventListener.class);
	}

}