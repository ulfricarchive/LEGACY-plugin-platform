package com.ulfric.plugin.platform.control;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

class UniqueIdOfContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(UniqueIdOfCommand.class);
	}
	
}
