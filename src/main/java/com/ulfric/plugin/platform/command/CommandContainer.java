package com.ulfric.plugin.platform.command;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class CommandContainer extends Container {
	
	@Initialize
	public void initialize()
	{
		this.install(UUIDOfCommand.class);
	}
	
}
