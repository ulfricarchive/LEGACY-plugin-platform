package com.ulfric.plugin.platform.panel;

import com.ulfric.commons.spigot.panel.Panel;
import com.ulfric.commons.spigot.panel.browser.Browser;

public abstract class PanelBase implements Panel {

	private final Browser browser;

	public PanelBase(Browser browser)
	{
		this.browser = browser;
	}

	@Override
	public Browser browser()
	{
		return this.browser;
	}

}
