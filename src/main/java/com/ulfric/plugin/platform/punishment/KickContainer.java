package com.ulfric.plugin.platform.punishment;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

class KickContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(KickService.class);
		this.install(KickCommand.class);
	}

}