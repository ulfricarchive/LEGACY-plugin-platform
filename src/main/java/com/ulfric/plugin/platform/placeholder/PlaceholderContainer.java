package com.ulfric.plugin.platform.placeholder;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PlaceholderContainer extends Container {

	@Initialize
	public void setup()
	{
		this.install(SenderNamePlaceholder.class);
		this.install(LastTargetedByPlaceholder.class);
		this.install(NoPermissionPlaceholder.class);
	}

}