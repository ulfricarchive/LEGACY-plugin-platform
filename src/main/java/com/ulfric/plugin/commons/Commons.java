package com.ulfric.plugin.commons;

import java.util.logging.Logger;

import org.bukkit.event.Listener;

import com.ulfric.commons.cdi.ObjectFactory;
import com.ulfric.commons.cdi.container.Container;
import com.ulfric.commons.spigot.cdi.scope.service.Service;
import com.ulfric.commons.spigot.cdi.scope.service.ServiceScopeStrategy;
import com.ulfric.commons.spigot.container.ContainerLogger;
import com.ulfric.commons.spigot.container.ListenerComponent;
import com.ulfric.commons.spigot.container.ServiceComponent;
import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.commons.spigot.service.ServiceUtils;

public final class Commons extends UlfricPlugin {

	private ObjectFactory global;

	@Override
	protected void setupPlatform()
	{
		this.registerGlobalObjectFactory();
		this.registerScopes();
		this.registerBindings();
		this.registerComponents();

		super.setupPlatform();
	}

	private void registerGlobalObjectFactory()
	{
		this.global = ServiceUtils.registerIfAbsent(ObjectFactory.class, ObjectFactory::newInstance);
	}

	private void registerScopes()
	{
		this.global.bindScope(Service.class).to(ServiceScopeStrategy.class);
	}

	private void registerBindings()
	{
		this.global.bind(Logger.class).to(ContainerLogger.class);
	}

	private void registerComponents()
	{
		Container.registerComponentWrapper(Listener.class, ListenerComponent::new);
		Container.registerComponentWrapper(com.ulfric.commons.service.Service.class, ServiceComponent::new);
	}

}
