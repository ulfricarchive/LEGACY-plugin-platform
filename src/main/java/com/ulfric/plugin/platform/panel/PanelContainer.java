package com.ulfric.plugin.platform.panel;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.panel.chest.ChestPanelContainer;

public class PanelContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(PanelsService.class);
		this.install(ChestPanelContainer.class);
	}

}
