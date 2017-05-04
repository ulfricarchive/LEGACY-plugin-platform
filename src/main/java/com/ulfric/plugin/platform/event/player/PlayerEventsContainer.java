package com.ulfric.plugin.platform.event.player;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PlayerEventsContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(AsyncPlayerQuitEventContainer.class);
		this.install(PlayerMoveBlockEventContainer.class);
	}

}