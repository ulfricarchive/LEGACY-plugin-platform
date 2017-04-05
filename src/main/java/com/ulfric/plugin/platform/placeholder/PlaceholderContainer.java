package com.ulfric.plugin.platform.placeholder;

import com.ulfric.dragoon.container.Container;

public class PlaceholderContainer extends Container {

	@Override
	public void onLoad()
	{
		this.install(SenderNamePlaceholder.class);
		this.install(LastTargetedByPlaceholder.class);
	}

}