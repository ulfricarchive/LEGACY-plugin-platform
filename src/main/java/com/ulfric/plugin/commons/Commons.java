package com.ulfric.plugin.commons;

import com.ulfric.commons.cdi.construct.BeanFactory;
import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.commons.spigot.service.ServiceUtils;

public final class Commons extends UlfricPlugin {

	@Override
	public void init()
	{
		ServiceUtils.registerIfAbsent(BeanFactory.class, BeanFactory::newInstance);
	}

}