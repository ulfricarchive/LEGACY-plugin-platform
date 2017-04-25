package com.ulfric.plugin.platform.text;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class TextContainer extends Container {

	@Initialize
	private void setup()
	{
		this.install(TextService.class);
	}

}