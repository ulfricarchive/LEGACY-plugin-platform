package com.ulfric.plugin.platform.silkspawners;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class SilkTouchContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(SilkTouchListener.class);
	}
	
}
