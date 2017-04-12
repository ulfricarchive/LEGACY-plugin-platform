package com.ulfric.plugin.platform.permissions;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PermissionsContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(PermissionsService.class);
	}

}