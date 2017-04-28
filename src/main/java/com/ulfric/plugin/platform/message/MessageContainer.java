package com.ulfric.plugin.platform.message;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class MessageContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(MessageService.class);
		this.install(MessageCommand.class);
		this.install(ReplyCommand.class);
	}
	
}
