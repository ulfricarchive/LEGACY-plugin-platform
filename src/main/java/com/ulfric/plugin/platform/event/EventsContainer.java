package com.ulfric.plugin.platform.event;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.event.player.PlayerEventsContainer;
import com.ulfric.plugin.platform.event.server.ServerEventsContainer;

public class EventsContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(PlayerEventsContainer.class);
		this.install(ServerEventsContainer.class);
	}

}