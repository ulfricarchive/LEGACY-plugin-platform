package com.ulfric.plugin.platform.service;

import com.ulfric.commons.service.Service;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.dragoon.scope.ScopeStrategy;
import com.ulfric.dragoon.scope.Scoped;

public final class BukkitServiceScopeStrategy implements ScopeStrategy {

	@Override
	public <T> Scoped<T> getOrCreate(Class<T> request)
	{
		return this.getOrEmpty(request);
	}

	@Override
	public <T> Scoped<T> getOrEmpty(Class<T> request)
	{
		if (!Service.class.isAssignableFrom(request))
		{
			return Scoped.createEmptyScope(request);
		}

		Service service = this.getServiceFromRequest(request);

		@SuppressWarnings("unchecked")
		Scoped<T> scoped = (Scoped<T>) new Scoped<>(request, service);

		return scoped;
	}

	private Service getServiceFromRequest(Class<?> request)
	{
		@SuppressWarnings("unchecked")
		Class<Service> serviceClass = (Class<Service>) request;

		return ServiceUtils.getService(serviceClass);

	}

}
