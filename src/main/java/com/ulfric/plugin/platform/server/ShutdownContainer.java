package com.ulfric.plugin.platform.server;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ShutdownContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.bind(ShutdownAudit.class).to(ShutdownAuditInterceptor.class);
		this.install(ShutdownListener.class);
	}

}