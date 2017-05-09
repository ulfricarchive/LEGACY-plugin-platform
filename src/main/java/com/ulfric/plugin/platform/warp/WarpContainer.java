package com.ulfric.plugin.platform.warp;

import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;
import com.ulfric.plugin.platform.warp.home.HomeContainer;

public class WarpContainer extends Container {
	
	@Inject
	private ObjectFactory factory;

	@Initialize
	private void initialize()
	{
		this.install(PlayerData.class);
		this.install(WarpsService.class);
		this.install(WarpCommand.class);
		this.install(WarpsCommand.class);
		this.install(WarpSetCommand.class);
		this.install(WarpDeleteCommand.class);
		this.install(LastWarpPlaceholder.class);
		this.install(WarpsPlaceholder.class);
		this.install(SpawnContainer.class);
		this.install(HomeContainer.class);
	}
	
}
