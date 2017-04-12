package com.ulfric.plugin.platform.data;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PlayerDataContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(PlayerDataService.class);
	}

}