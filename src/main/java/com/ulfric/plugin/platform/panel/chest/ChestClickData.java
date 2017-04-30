package com.ulfric.plugin.platform.panel.chest;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.click.ClickData;

public class ChestClickData implements ClickData {

	private final int slot;
	private final ClickType clickType;
	private final InventoryAction action;
	private final ItemStack currentItem;
	private final ItemStack cursor;

	ChestClickData(InventoryClickEvent event)
	{
		this.slot = event.getSlot();
		this.clickType = event.getClick();
		this.action = event.getAction();
		this.currentItem = event.getCurrentItem();
		this.cursor = event.getCursor();
	}

	public int getSlot()
	{
		return this.slot;
	}

	public ClickType getClickType()
	{
		return this.clickType;
	}

	public InventoryAction getAction()
	{
		return this.action;
	}

	public ItemStack getCurrentItem()
	{
		return this.currentItem;
	}

	public ItemStack getCursor()
	{
		return this.cursor;
	}

}
