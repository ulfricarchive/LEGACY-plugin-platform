package com.ulfric.plugin.platform.panel;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.PanelInventory;

public class BukkitPanelInventory implements PanelInventory {

	private static final int BROWSER_ESTATE = 18;
	private static final int ROW_LENGTH = 9;

	private final Inventory inventory;

	public BukkitPanelInventory()
	{
		this.inventory = Bukkit.createInventory(null, BukkitPanelInventory.BROWSER_ESTATE);
	}

	@Override
	public void setTitle(String title)
	{
		PanelUtils.setTitle(this.inventory, title);
	}

	@Override
	public void setItem(int slot, ItemStack item)
	{
		int adjustedSlot = this.getAdjustedSlot(slot);

		this.ensureSpace(adjustedSlot);

		this.inventory.setItem(adjustedSlot, item);
	}

	@Override
	public Inventory finalInventory()
	{
		return this.inventory;
	}

	private int getAdjustedSlot(int slot)
	{
		return slot + BukkitPanelInventory.BROWSER_ESTATE;
	}

	private void ensureSpace(int slot)
	{
		if (this.inventory.getSize() <= slot)
		{
			int newSize = ((slot / BukkitPanelInventory.ROW_LENGTH) + 1) * BukkitPanelInventory.ROW_LENGTH;

			PanelUtils.setSize(this.inventory, newSize);
		}
	}

}