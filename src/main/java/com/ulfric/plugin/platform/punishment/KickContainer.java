package com.ulfric.plugin.platform.punishment;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class KickContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(KickService.class);
		this.install(KickContainer.class);
	}

}