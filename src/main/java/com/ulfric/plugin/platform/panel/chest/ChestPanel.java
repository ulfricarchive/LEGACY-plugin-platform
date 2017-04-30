package com.ulfric.plugin.platform.panel.chest;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.browser.Browser;
import com.ulfric.commons.spigot.panel.click.ClickResult;
import com.ulfric.plugin.platform.panel.PanelBase;
import com.ulfric.plugin.platform.panel.browser.BukkitBrowser;

public class ChestPanel extends PanelBase {

	private static final int BROWSER_ESTATE = 18;
	private static final int ROW_LENGTH = 9;

	public static ChestPanel create(Browser browser)
	{
		return new ChestPanel(browser);
	}

	private final Inventory inventory;
	private final Map<Integer, ChestButton> buttons = new HashMap<>();

	private ChestPanel(Browser browser)
	{
		super(browser);

		this.inventory = Bukkit.createInventory(null, ChestPanel.BROWSER_ESTATE);

		this.injectBrowser();
	}

	@Override
	public ChestButton.Builder buildButton()
	{
		return ChestButton.builder(this);
	}

	ChestBrowserButton.Builder buildBrowserButton()
	{
		return ChestBrowserButton.browserBuilder(this);
	}

	@Override
	public void open()
	{
		this.buttons.forEach((slot, button) ->
				this.setItem(slot, button.getItem()));

		this.browser().owner().openInventory(this.inventory);
	}

	public void setTitle(String title)
	{
		ChestPanelUtils.setTitle(this.inventory, title);
	}

	void setButton(int slot, ChestButton button)
	{
		if (button instanceof ChestBrowserButton)
		{
			this.buttons.put(slot, button);
		}
		else
		{
			int adjustedSlot = this.getAdjustedSlot(slot);
			this.buttons.put(adjustedSlot, button);
		}
	}

	void onClick(InventoryClickEvent event)
	{
		ChestButton button = this.buttons.get(event.getSlot());

		if (button != null)
		{
			ClickResult result = button.handle(new ChestClickData(event));

			if (result == ClickResult.CANCEL)
			{
				event.setCancelled(true);
			}
		}
	}

	void onClose(InventoryCloseEvent event)
	{
		((BukkitBrowser) this.browser()).panelClosed();
	}

	private void injectBrowser()
	{
		ChestBrowser ui = new ChestBrowser(this.browser());

		ui.insertInto(this);
	}

	private void setItem(int slot, ItemStack item)
	{
		this.ensureSpace(slot);

		this.inventory.setItem(slot, item);
	}

	private int getAdjustedSlot(int slot)
	{
		return slot + ChestPanel.BROWSER_ESTATE;
	}

	private void ensureSpace(int slot)
	{
		if (this.inventory.getSize() <= slot)
		{
			int newSize = ((slot / ChestPanel.ROW_LENGTH) + 1) * ChestPanel.ROW_LENGTH;

			ChestPanelUtils.setSize(this.inventory, newSize);
		}
	}
}