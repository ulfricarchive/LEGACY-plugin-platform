package com.ulfric.plugin.platform.kit;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class KitContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(KitsService.class);
	}

}
