package com.ulfric.plugin.platform.text;

import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class TextContainer extends Container {

	private TextService service;

	@Initialize
	private void setup()
	{
		ServiceUtils.getService(ObjectFactory.class).bind(Text.class).to(TextService.class);
		this.install(TextService.class);
	}

	@Override
	public void onEnable()
	{
		this.service = ServiceUtils.getService(TextService.class);
	}

	@Override
	public void onDisable()	
	{
		ServiceUtils.unregister(TextService.class, this.service);
	}

}