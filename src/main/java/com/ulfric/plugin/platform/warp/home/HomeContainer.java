package com.ulfric.plugin.platform.warp.home;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class HomeContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(HomeService.class);
		this.install(HomeCommand.class);
		this.install(SetHomeCommand.class);
		this.install(DeleteHomeCommand.class);
		this.install(HomesCommand.class);
	}
	
}
