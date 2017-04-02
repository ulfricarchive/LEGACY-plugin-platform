package com.ulfric.plugin.platform;

import java.util.logging.Logger;

import org.bukkit.event.Listener;

import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.CommandFeature;
import com.ulfric.commons.spigot.container.ContainerLogger;
import com.ulfric.commons.spigot.listener.ListenerFeature;
import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.commons.spigot.service.ServiceFeature;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;

public final class Platform extends UlfricPlugin {

	private ObjectFactory global;

	@Override
	protected void setupPlatform()
	{
		this.registerGlobalObjectFactory();
		this.registerBindings();
		this.registerComponents();

		super.setupPlatform();
	}

	@Override
	public void init()
	{
		this.getContainer().install(HelloCommand.class);
	}

	private void registerGlobalObjectFactory()
	{
		this.global = ServiceUtils.registerIfAbsent(ObjectFactory.class, ObjectFactory::newInstance);
	}

	private void registerBindings()
	{
		this.global.bind(Logger.class).to(ContainerLogger.class);
	}

	@SuppressWarnings("unchecked")
	private void registerComponents()
	{
		Container.registerFeatureWrapper(Listener.class, ListenerFeature::new);
		Container.registerFeatureWrapper(com.ulfric.commons.service.Service.class, ServiceFeature::new);
		Container.registerFeatureWrapper(Command.class, CommandFeature::new);
	}

}