package com.ulfric.plugin.platform.panel.chest;

import java.util.Optional;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.ulfric.commons.spigot.panel.Panel;
import com.ulfric.commons.spigot.panel.Panels;
import com.ulfric.commons.spigot.panel.browser.Browser;

class ChestPanelListener implements Listener {

	private final Panels panels = Panels.getService();

	@EventHandler
	public void on(InventoryClickEvent event)
	{
		if (event.getClickedInventory() != event.getView().getTopInventory())
		{
			return;
		}

		Browser browser = this.getBrowser(event.getWhoClicked());

		this.currentPanel(browser).ifPresent(panel ->
				panel.onClick(event));
	}

	@EventHandler
	public void on(InventoryCloseEvent event)
	{
		Browser browser = this.getBrowser(event.getPlayer());

		this.currentPanel(browser).ifPresent(panel ->
				panel.onClose(event));
	}

	private Browser getBrowser(HumanEntity player)
	{
		return this.panels.getBrowser((Player) player);
	}

	private Optional<ChestPanel> currentPanel(Browser browser)
	{
		if (!browser.isOpen() || browser.tabs().isEmpty())
		{
			return Optional.empty();
		}

		Panel panel = browser.currentTab();

		if (!(panel instanceof ChestPanel))
		{
			return Optional.empty();
		}

		return Optional.of((ChestPanel) panel);
	}

}
