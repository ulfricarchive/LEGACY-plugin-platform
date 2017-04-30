package com.ulfric.plugin.platform.panel.chest;

import com.ulfric.commons.spigot.panel.browser.Browser;
import com.ulfric.commons.spigot.panel.browser.BrowserUI;

class ChestBrowser implements BrowserUI<ChestPanel> {

	private final Browser browser;

	ChestBrowser(Browser browser)
	{
		this.browser = browser;
	}

	@Override
	public void insertInto(ChestPanel panel)
	{
		// BROWSER UI
		// Uses BrowserButton(s)
		panel.buildBrowserButton();
	}

}
