package com.ulfric.plugin.platform.server;

import java.util.logging.Logger;

import com.ulfric.commons.spigot.event.server.ServerShutdownEvent;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.dragoon.intercept.Context;
import com.ulfric.dragoon.intercept.Interceptor;

public class ShutdownAuditInterceptor implements Interceptor {

	@Inject
	private Logger logger;

	@Override
	public Object intercept(Context invocation)
	{
		ServerShutdownEvent event = this.getEvent(invocation);

		this.logger.info("Detected shutdown caused by ");
		Thread.dumpStack();
		if (event.isAsynchronous())
		{
			this.logger.warning("Shutdown was asynchronous");
		}

		return invocation.proceed();
	}

	private ServerShutdownEvent getEvent(Context context)
	{
		for (Object argument : context.getArguments())
		{
			if (argument instanceof ServerShutdownEvent)
			{
				return (ServerShutdownEvent) argument;
			}
		}

		throw new IllegalArgumentException("Cannot find ServerShutdownEvent from " + context.getDestinationExecutable());
	}

}