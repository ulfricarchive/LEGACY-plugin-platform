package com.ulfric.plugin.platform.warp;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

class SpawnContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(SpawnService.class);
		this.install(SpawnCommand.class);
		this.install(SpawnSetCommand.class);
	}

}