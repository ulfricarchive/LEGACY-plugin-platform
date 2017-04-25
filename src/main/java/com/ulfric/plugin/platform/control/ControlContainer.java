package com.ulfric.plugin.platform.control;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ControlContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(UniqueIdOfContainer.class);
	}

}
