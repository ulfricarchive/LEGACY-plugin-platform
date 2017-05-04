package com.ulfric.plugin.platform.data;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class DataContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(DataService.class);
	}

}