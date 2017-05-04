package com.ulfric.plugin.platform.panel.chest;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ulfric.commons.spigot.panel.Panel;
import com.ulfric.commons.spigot.panel.Panels;
import com.ulfric.commons.spigot.panel.browser.Browser;
import com.ulfric.commons.spigot.panel.click.ClickResult;
import com.ulfric.commons.spigot.text.Text;
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
				this.setItem(browser.owner(), slot, button.getItem()));

		browser.owner().openInventory(this.inventory);
	}

	void onClick(InventoryClickEvent event)
	{
		event.setCancelled(true);

		ChestButton button = this.buttons.get(event.getSlot());

		if (button != null)
		{
			ClickResult result = button.handle(new ChestClickData(event));

			if (result == ClickResult.ALLOW)
			{
				event.setCancelled(false);
			}
		}
	}

	void onClose(InventoryCloseEvent event)
	{
		Player player = (Player) event.getPlayer();

		BukkitBrowser browser = (BukkitBrowser) Panels.getService().getBrowser(player);

		browser.panelClosed();
	}

	private void setItem(Player player, int slot, ItemStack item)
	{
		this.ensureSpace(slot);

		this.inventory.setItem(slot, this.localizeName(player, item));
	}

	private void ensureSpace(int slot)
	{
		if (this.inventory.getSize() <= slot)
		{
			int newSize = ((slot / ChestPanel.ROW_LENGTH) + 1) * ChestPanel.ROW_LENGTH;

			ChestPanelUtils.setSize(this.inventory, newSize);
		}
	}

	private ItemStack localizeName(Player player, ItemStack item)
	{
		if (!item.hasItemMeta())
		{
			return item;
		}

		item = item.clone();

		ItemMeta meta = item.getItemMeta();

		if (!meta.hasDisplayName())
		{
			return item;
		}

		String localizedName = Text.getService().getPlainMessage(player, meta.getDisplayName());

		if (!localizedName.equals(meta.getDisplayName()))
		{
			meta.setDisplayName(localizedName);
			item.setItemMeta(meta);
		}

		return item;
	}

	public static class Builder implements ChestPanelBuilder {

		private final Map<Integer, ChestButton> buttons = new HashMap<>();
		private String title = "Inventory";

		public ChestPanelBuilder setTitle(String title)
		{
			this.title = title;

			return this;
		}

		public ChestButton.Builder addButton()
		{
			return ChestButton.builder(this);
		}

		ChestBrowserButton.Builder addBrowserButton()
		{
			return ChestBrowserButton.browserBuilder(this);
		}

		@Override
		public void addBuiltButton(ChestButton button)
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
		public ChestTemplate template(String... rows)
		{
			return new ChestTemplate(this, rows);
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