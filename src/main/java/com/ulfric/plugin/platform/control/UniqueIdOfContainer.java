package com.ulfric.plugin.platform.control;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class UniqueIdOfContainer extends Container {
	
	@Initialize
	public void setup()
	{
		this.install(UniqueIdOfCommand.class);
	}
	
}
