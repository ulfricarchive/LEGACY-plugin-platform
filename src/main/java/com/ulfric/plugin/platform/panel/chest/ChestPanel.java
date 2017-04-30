package com.ulfric.plugin.platform.panel.chest;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.Panel;
import com.ulfric.commons.spigot.panel.Panels;
import com.ulfric.commons.spigot.panel.browser.Browser;
import com.ulfric.commons.spigot.panel.click.ClickResult;
import com.ulfric.plugin.platform.panel.browser.BukkitBrowser;

public class ChestPanel implements Panel {

	private static final int BROWSER_ESTATE = 18;
	private static final int ROW_LENGTH = 9;

	public static ChestPanel.Builder builder()
	{
		return new ChestPanel.Builder();
	}

	private final Inventory inventory;
	private final Map<Integer, ChestButton> buttons;

	private ChestPanel(String title, Map<Integer, ChestButton> buttons)
	{
		this.inventory = Bukkit.createInventory(null, ChestPanel.BROWSER_ESTATE, title);
		this.buttons = buttons;
	}

	@Override
	public void open(Browser browser)
	{
		this.buttons.forEach((slot, button) ->
				this.setItem(slot, button.getItem()));

		browser.owner().openInventory(this.inventory);
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
		Player player = (Player) event.getPlayer();

		BukkitBrowser browser = (BukkitBrowser) Panels.getService().getBrowser(player);

		browser.panelClosed();
	}

	private void setItem(int slot, ItemStack item)
	{
		this.ensureSpace(slot);

		this.inventory.setItem(slot, item);
	}

	private void ensureSpace(int slot)
	{
		if (this.inventory.getSize() <= slot)
		{
			int newSize = ((slot / ChestPanel.ROW_LENGTH) + 1) * ChestPanel.ROW_LENGTH;

			ChestPanelUtils.setSize(this.inventory, newSize);
		}
	}

	public static class Builder implements org.apache.commons.lang3.builder.Builder<ChestPanel>
	{
		private final Map<Integer, ChestButton> buttons = new HashMap<>();
		private String title = "Inventory";

		public Builder setTitle(String title)
		{
			this.title = title;

			return this;
		}

		public ChestButton.Builder buildButton()
		{
			return ChestButton.builder(this);
		}

		ChestBrowserButton.Builder buildBrowserButton()
		{
			return ChestBrowserButton.browserBuilder();
		}

		void add(ChestButton button)
		{
			for (int slot : button.getSlots())
			{
				if (button instanceof ChestBrowserButton)
				{
					this.buttons.put(slot, button);
				}
				else
				{
					this.buttons.put(this.getAdjustedSlot(slot), button);
				}
			}
		}

		@Override
		public ChestPanel build()
		{
			ChestBrowser ui = new ChestBrowser();
			ui.insertInto(this);

			return new ChestPanel(this.title, this.buttons);
		}

		private int getAdjustedSlot(int slot)
		{
			return slot + ChestPanel.BROWSER_ESTATE;
		}

	}

}