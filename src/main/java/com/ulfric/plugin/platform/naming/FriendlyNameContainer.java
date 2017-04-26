package com.ulfric.plugin.platform.naming;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class FriendlyNameContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(FriendlyNameService.class);
	}

}