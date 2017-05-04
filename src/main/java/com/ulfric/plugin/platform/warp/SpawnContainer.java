package com.ulfric.plugin.platform.warp;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class SpawnContainer extends Container {

	@Initialize
	public void setup()
	{
		this.install(SpawnService.class);
		this.install(SpawnCommand.class);
		this.install(SpawnSetCommand.class);
	}

}