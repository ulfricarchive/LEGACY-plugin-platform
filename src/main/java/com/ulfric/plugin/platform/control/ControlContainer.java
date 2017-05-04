package com.ulfric.plugin.platform.control;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ControlContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(UniqueIdOfContainer.class);
		this.install(ClearChatContainer.class);
	}

}
