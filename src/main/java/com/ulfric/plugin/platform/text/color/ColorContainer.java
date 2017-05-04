package com.ulfric.plugin.platform.text.color;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class ColorContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(ColorService.class);

		this.install(PrimaryColorPlaceholder.class);
		this.install(DetailColorPlaceholder.class);
		this.install(ButtonColorPlaceholder.class);
		this.install(WarningColorPlaceholder.class);
	}

}
