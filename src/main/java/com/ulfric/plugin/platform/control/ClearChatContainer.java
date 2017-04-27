package com.ulfric.plugin.platform.control;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ClearChatContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(ClearChatService.class);
		this.install(ClearChatCommand.class);
	}

}