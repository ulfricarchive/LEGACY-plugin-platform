package com.ulfric.plugin.platform.warp;

import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

public class WarpContainer extends Container {
	
	@Inject
	private ObjectFactory factory;

	@Initialize
	private void initialize()
	{
		this.install(WarpsService.class);
		this.install(WarpCommand.class);
		this.install(WarpsCommand.class);
		this.install(WarpSetCommand.class);
		this.install(WarpDeleteCommand.class);
		this.install(LastWarpPlaceholder.class);
		this.install(WarpsPlaceholder.class);
		this.install(SpawnContainer.class);
	}
	
}
