package com.ulfric.plugin.platform.warp;

import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.commons.spigot.warp.Warps;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

public class WarpContainer extends Container {
	
	@Inject
	private ObjectFactory factory;
	
	private WarpsService warps;
	
	@Initialize
	private void setup()
	{
		this.factory.bind(Warps.class).to(WarpsService.class);

		this.install(WarpsService.class);
		this.install(WarpCommand.class);
		this.install(WarpsCommand.class);
		this.install(WarpSetCommand.class);
		this.install(WarpDeleteCommand.class);
		this.install(WarpsPlaceholder.class);
	}
	
	@Override
	public void onEnable()
	{
		this.warps = ServiceUtils.getService(WarpsService.class);
	}
	
	@Override
	public void onDisable()
	{
		ServiceUtils.unregister(WarpsService.class, this.warps);
	}
	
}
