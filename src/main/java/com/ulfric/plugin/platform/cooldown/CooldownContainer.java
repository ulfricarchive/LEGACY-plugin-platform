package com.ulfric.plugin.platform.cooldown;

import com.ulfric.commons.spigot.cooldown.Cooldowns;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;

public class CooldownContainer extends Container {
	
	@Inject
	private ObjectFactory factory;
	
	private CooldownService cooldown;
	
	@Initialize
	private void initialize()
	{
		this.factory.bind(Cooldowns.class).to(CooldownService.class);
		this.install(PlayerData.class);
		this.install(CooldownService.class);
	}
	
	@Override
	public void onEnable()
	{
		this.cooldown = ServiceUtils.getService(CooldownService.class);
	}
	
	@Override
	public void onDisable()
	{
		ServiceUtils.unregister(CooldownService.class, this.cooldown);
	}
	
}
