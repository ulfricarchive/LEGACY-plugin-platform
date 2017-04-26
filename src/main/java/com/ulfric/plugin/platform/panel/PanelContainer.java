package com.ulfric.plugin.platform.panel;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PanelContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(PanelsService.class);
	}

}
