package com.ulfric.plugin.platform.server;

import java.util.logging.Logger;

import com.ulfric.commons.spigot.event.server.ServerShutdownEvent;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.dragoon.intercept.Context;
import com.ulfric.dragoon.intercept.Interceptor;

class ShutdownAuditInterceptor implements Interceptor {

	private static final int TRACE_DEPTH = 13;

	@Inject
	private Logger logger;

	@Override
	public Object intercept(Context invocation)
	{
		ServerShutdownEvent event = this.getEvent(invocation);

		this.logger.info("Detected shutdown caused by " + this.getShutdownCause());
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

	private String getShutdownCause()
	{
		return Thread.currentThread().getStackTrace()[ShutdownAuditInterceptor.TRACE_DEPTH].toString();
	}

}