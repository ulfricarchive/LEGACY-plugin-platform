package com.ulfric.plugin.platform.panel.chest;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ChestPanelContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(ChestPanelListener.class);
	}

}
