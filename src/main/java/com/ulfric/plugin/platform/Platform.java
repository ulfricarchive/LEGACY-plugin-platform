package com.ulfric.plugin.platform;

import java.util.logging.Logger;

import org.bukkit.event.Listener;

import com.ulfric.commons.cdi.ObjectFactory;
import com.ulfric.commons.cdi.container.Container;
import com.ulfric.commons.cdi.container.Feature;
import com.ulfric.commons.spigot.cdi.scope.service.Service;
import com.ulfric.commons.spigot.cdi.scope.service.ServiceScopeStrategy;
import com.ulfric.commons.spigot.container.ContainerLogger;
import com.ulfric.commons.spigot.container.ListenerFeature;
import com.ulfric.commons.spigot.container.ServiceFeature;
import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.commons.spigot.service.ServiceUtils;

public final class Platform extends UlfricPlugin {

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
		Container.registerFeatureWrapper(Listener.class, ListenerFeature::new);
		Container.registerFeatureWrapper(com.ulfric.commons.service.Service.class, this::featureWrapper);
	}

	private <S extends com.ulfric.commons.service.Service> ServiceFeature<S> featureWrapper(Feature parent, S implementation)
	{
		return new ServiceFeature<>(parent, implementation);
	}

}