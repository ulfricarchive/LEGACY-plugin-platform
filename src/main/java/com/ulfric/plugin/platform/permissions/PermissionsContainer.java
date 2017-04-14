package com.ulfric.plugin.platform.permissions;

import com.ulfric.commons.spigot.permissions.Permissions;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.data.PlayerData;

public class PermissionsContainer extends Container {

	@Initialize
	private void setup()
	{
		ServiceUtils.getService(ObjectFactory.class).bind(Permissions.class).to(PermissionsService.class);
		this.install(PermissionsService.class);
		this.install(PermissionsListener.class);
		this.install(PlayerData.class);
	}

}