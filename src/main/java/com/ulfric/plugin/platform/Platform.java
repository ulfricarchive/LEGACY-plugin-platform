package com.ulfric.plugin.platform;

import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.dragoon.container.Container;
import com.ulfric.plugin.platform.placeholder.PlaceholderContainer;
import com.ulfric.plugin.platform.text.TextContainer;

public final class Platform extends UlfricPlugin {

	@Override
	public void init()
	{
		Container container = this.getContainer();

		container.install(TextContainer.class);
		container.install(PlaceholderContainer.class);
	}

}