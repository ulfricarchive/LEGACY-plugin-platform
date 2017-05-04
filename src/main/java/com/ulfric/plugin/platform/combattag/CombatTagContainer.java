package com.ulfric.plugin.platform.combattag;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class CombatTagContainer extends Container {
	
	@Initialize
	private void initialize()
	{
		this.install(CombatTagService.class);
		this.install(CombatTaggedPlaceholder.class);
		this.install(CombatTagListener.class);
		this.install(CombatTagCommand.class);
	}
	
}
