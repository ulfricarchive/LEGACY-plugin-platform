package com.ulfric.plugin.platform.data;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class DataContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(DataService.class);
	}

}