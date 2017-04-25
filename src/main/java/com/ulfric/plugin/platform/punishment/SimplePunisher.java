package com.ulfric.plugin.platform.punishment;

import java.util.Objects;

import com.ulfric.commons.bean.Bean;
import com.ulfric.commons.spigot.punishment.Punisher;

final class SimplePunisher extends Bean implements Punisher {

	public static final SimplePunisher of(String name)
	{
		Objects.requireNonNull(name);
		return new SimplePunisher(name);
	}

	private final String name;

	private SimplePunisher(String name)
	{
		this.name = name;
	}

	@Override
	public final String getName()
	{
		return this.name;
	}

}