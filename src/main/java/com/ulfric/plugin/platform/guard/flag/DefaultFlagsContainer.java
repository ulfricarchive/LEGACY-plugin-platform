package com.ulfric.plugin.platform.guard.flag;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class DefaultFlagsContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(BreakFlag.class);
		this.install(PlaceFlag.class);
	}

}