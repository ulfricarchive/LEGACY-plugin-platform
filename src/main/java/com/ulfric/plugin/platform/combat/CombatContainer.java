package com.ulfric.plugin.platform.combat;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class CombatContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(CombatService.class);
		this.install(CombatPlaceholder.class);
		this.install(CombatListener.class);
	}
	
}
