package com.ulfric.plugin.platform.warp;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class TeleportContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(TeleportService.class);
		this.install(TimedTeleportCancelListener.class);
		this.install(TeleportDelayPlaceholder.class);
	}

}