package com.ulfric.plugin.commons;

import com.ulfric.commons.cdi.construct.BeanFactory;
import com.ulfric.commons.logging.Logger;
import com.ulfric.commons.spigot.cdi.intercept.concurrent.OnMainThread;
import com.ulfric.commons.spigot.cdi.intercept.concurrent.OnMainThreadInterceptor;
import com.ulfric.commons.spigot.cdi.intercept.player.MustBeAlive;
import com.ulfric.commons.spigot.cdi.intercept.player.MustBeAliveInterceptor;
import com.ulfric.commons.spigot.cdi.intercept.player.MustBeDead;
import com.ulfric.commons.spigot.cdi.intercept.player.MustBeDeadInterceptor;
import com.ulfric.commons.spigot.cdi.intercept.server.Broadcast;
import com.ulfric.commons.spigot.cdi.intercept.server.BroadcastInterceptor;
import com.ulfric.commons.spigot.cdi.scope.service.Service;
import com.ulfric.commons.spigot.cdi.scope.service.ServiceScopeStrategy;
import com.ulfric.commons.spigot.logging.ConsoleLogger;
import com.ulfric.commons.spigot.module.Disable;
import com.ulfric.commons.spigot.module.DisableInterceptor;
import com.ulfric.commons.spigot.module.Enable;
import com.ulfric.commons.spigot.module.EnableInterceptor;
import com.ulfric.commons.spigot.module.Load;
import com.ulfric.commons.spigot.module.LoadInterceptor;
import com.ulfric.commons.spigot.permission.RequirePermission;
import com.ulfric.commons.spigot.permission.RequirePermissionInterceptor;
import com.ulfric.commons.spigot.permission.RequirePermissions;
import com.ulfric.commons.spigot.permission.RequirePermissionsInterceptor;
import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.commons.spigot.service.ServiceUtils;

public final class Commons extends UlfricPlugin {

	@Override
	protected void setupPlatform()
	{
		this.setupAllPlugins();
		super.setupPlatform();
	}

	private void setupAllPlugins()
	{
		BeanFactory globalBeanFactory = this.registerBeanFactoryService();
		this.registerScopes(globalBeanFactory);
		this.registerInterceptors(globalBeanFactory);
		this.registerBindings(globalBeanFactory);
	}

	private BeanFactory registerBeanFactoryService()
	{
		return ServiceUtils.registerIfAbsent(BeanFactory.class, BeanFactory::newInstance);
	}

	private void registerScopes(BeanFactory factory)
	{
		factory.bind(Service.class).toScope(ServiceScopeStrategy.class);
	}

	private void registerInterceptors(BeanFactory factory)
	{
		factory.bind(Load.class).toInterceptor(LoadInterceptor.class);
		factory.bind(Enable.class).toInterceptor(EnableInterceptor.class);
		factory.bind(Disable.class).toInterceptor(DisableInterceptor.class);

		factory.bind(OnMainThread.class).toInterceptor(OnMainThreadInterceptor.class);

		factory.bind(Broadcast.class).toInterceptor(BroadcastInterceptor.class);

		factory.bind(RequirePermission.class).toInterceptor(RequirePermissionInterceptor.class);
		factory.bind(RequirePermissions.class).toInterceptor(RequirePermissionsInterceptor.class);

		factory.bind(MustBeAlive.class).toInterceptor(MustBeAliveInterceptor.class);
		factory.bind(MustBeDead.class).toInterceptor(MustBeDeadInterceptor.class);
	}

	private void registerBindings(BeanFactory factory)
	{
		factory.bind(Logger.class).to(ConsoleLogger.class);
	}

}