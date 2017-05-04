package com.ulfric.plugin.platform.text;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.plugin.platform.text.color.ColorContainer;

public class TextContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(TextService.class);
		this.install(ColorContainer.class);
	}

}