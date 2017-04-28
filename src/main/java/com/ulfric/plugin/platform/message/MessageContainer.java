package com.ulfric.plugin.platform.message;

import com.ulfric.commons.spigot.message.Messaging;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

public class MessageContainer extends Container {
	
	@Inject
	private ObjectFactory factory;
	
	private MessageService messaging;
	
	@Initialize
	private void initialize()
	{
		this.factory.bind(Messaging.class).to(MessageService.class);
		this.install(MessageService.class);
		this.install(MessageCommand.class);
		this.install(ReplyCommand.class);
	}
	
	@Override
	public void onEnable()
	{
		this.messaging = ServiceUtils.getService(MessageService.class);
	}
	
	@Override
	public void onDisable()
	{
		ServiceUtils.unregister(MessageService.class, this.messaging);
	}
	
}
