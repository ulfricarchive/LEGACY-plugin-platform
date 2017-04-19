package com.ulfric.plugin.platform.guard;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.guard.flag.FlagsContainer;

public class GuardContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(GuardService.class);
		this.install(FlagsContainer.class);
	}

}