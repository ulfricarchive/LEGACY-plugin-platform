package com.ulfric.plugin.commons;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

import com.ulfric.commons.cdi.ObjectFactory;
import com.ulfric.commons.cdi.scope.Supplied;
import com.ulfric.commons.cdi.scope.SuppliedScopeStrategy;
import com.ulfric.commons.spigot.cdi.scope.service.Service;
import com.ulfric.commons.spigot.cdi.scope.service.ServiceScopeStrategy;
import com.ulfric.commons.spigot.container.ContainerLogger;
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
		SuppliedScopeStrategy scope = (SuppliedScopeStrategy) this.global.request(Supplied.class);
		scope.register(ContainerLogger.class, () -> new ContainerLogger(Bukkit.getLogger()));
	}

}