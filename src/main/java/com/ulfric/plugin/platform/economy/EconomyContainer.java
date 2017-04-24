package com.ulfric.plugin.platform.economy;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.data.PlayerData;

public class EconomyContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(PlayerData.class);
		this.install(EconomyService.class);
		this.install(PayCommand.class);
	}

}