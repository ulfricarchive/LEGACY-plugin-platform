package com.ulfric.plugin.platform.network;

import com.ulfric.commons.spigot.network.InetAddressHash;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class InetAddressHashContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(InetAddressHashService.class);
	}

	@Override
	public void onDisable()
	{
		InetAddressHash genericService = InetAddressHash.getService();

		if (genericService instanceof InetAddressHashService)
		{
			((InetAddressHashService) genericService).save();
		}
	}

}