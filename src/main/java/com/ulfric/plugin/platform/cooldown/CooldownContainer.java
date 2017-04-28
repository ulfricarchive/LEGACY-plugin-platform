package com.ulfric.plugin.platform.cooldown;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.data.PlayerData;

public class CooldownContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(PlayerData.class);
		this.install(CooldownService.class);
	}
	
}
