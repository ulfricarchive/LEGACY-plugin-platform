package com.ulfric.plugin.platform.panel.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.click.CancelledClick;
import com.ulfric.commons.spigot.panel.click.Click;

class ChestBrowserButton extends ChestButton {

	static Builder browserBuilder()
	{
		return new Builder();
	}

	private ChestBrowserButton(List<Click<ChestClickData>> clicks, ItemStack item, int[] slots)
	{
		super(clicks, item, slots);
	}

	static class Builder implements org.apache.commons.lang3.builder.Builder<ChestBrowserButton> {

		private final List<Click<ChestClickData>> clicks = new ArrayList<>();

		private ItemStack item;
		private int[] slots;

		private Builder()
		{}

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
			return new ChestBrowserButton(this.clicks, this.item, this.slots);
		}

	}

}
