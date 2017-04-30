package com.ulfric.plugin.platform.panel.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.click.ButtonBuilder;
import com.ulfric.commons.spigot.panel.click.CancelledClick;
import com.ulfric.commons.spigot.panel.click.Click;

class ChestBrowserButton extends ChestButton {

	static Builder browserBuilder(ChestPanel panel)
	{
		return new Builder(panel);
	}

	private ChestBrowserButton(ChestPanel panel, List<Click<ChestClickData>> clicks, ItemStack item, int[] slots)
	{
		super(panel, clicks, item, slots);
	}

	static class Builder implements ButtonBuilder<ChestBrowserButton> {

		private final ChestPanel panel;
		private final List<Click<ChestClickData>> clicks = new ArrayList<>();

		private ItemStack item;
		private int[] slots;

		private Builder(ChestPanel panel)
		{
			this.panel = panel;
		}

		Builder handle(Click<ChestClickData> click)
		{
			this.clicks.add(click);

			return this;
		}

		Builder handle(CancelledClick<ChestClickData> click)
		{
			return this.handle((Click<ChestClickData>) click);
		}

		Builder setItem(ItemStack item)
		{
			this.item = item;

			return this;
		}

		Builder setSlots(int... slots)
		{
			this.slots = slots;

			return this;
		}

		@Override
		public ChestBrowserButton build()
		{
			return new ChestBrowserButton(this.panel, this.clicks, this.item, this.slots);
		}

	}

}
