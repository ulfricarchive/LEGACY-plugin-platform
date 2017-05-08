package com.ulfric.plugin.platform.home;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.data.PlayerData;

public class HomeContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(PlayerData.class);
		this.install(HomeService.class);
		this.install(HomeCommand.class);
		this.install(SetHomeCommand.class);
		this.install(DeleteHomeCommand.class);
	}
	
}
