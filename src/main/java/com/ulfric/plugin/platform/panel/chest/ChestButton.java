package com.ulfric.plugin.platform.panel.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.click.Button;
import com.ulfric.commons.spigot.panel.click.ButtonBuilder;
import com.ulfric.commons.spigot.panel.click.CancelledClick;
import com.ulfric.commons.spigot.panel.click.Click;
import com.ulfric.commons.spigot.panel.click.ClickResult;

public class ChestButton implements Button {

	static Builder builder(ChestPanel panel)
	{
		return new Builder(panel);
	}

	private final ChestPanel panel;
	private final List<Click<ChestClickData>> clicks;
	private final ItemStack item;
	private final int[] slots;

	ChestButton(ChestPanel panel, List<Click<ChestClickData>> clicks, ItemStack item, int[] slots)
	{
		this.panel = panel;
		this.clicks = clicks;
		this.item = item;
		this.slots = slots;

		this.create();
	}

	private void create()
	{
		for (int slot : this.slots)
		{
			this.panel.setButton(slot, this);
		}
	}

	ItemStack getItem()
	{
		return this.item;
	}

	ClickResult handle(ChestClickData data)
	{
		ClickResult result = ClickResult.ALLOW;

		for (Click<ChestClickData> click : this.clicks)
		{
			ClickResult clickResult = click.handle(data);

			if (result == ClickResult.ALLOW &&
					clickResult == ClickResult.CANCEL)
			{
				result = clickResult;
			}
		}

		return result;
	}

	public static class Builder implements ButtonBuilder<ChestButton> {

		private final ChestPanel panel;
		private final List<Click<ChestClickData>> clicks = new ArrayList<>();

		private ItemStack item;
		private int[] slots;

		private Builder(ChestPanel panel)
		{
			this.panel = panel;
		}

		public Builder handle(Click<ChestClickData> click)
		{
			this.clicks.add(click);

			return this;
		}

		public Builder handle(CancelledClick<ChestClickData> click)
		{
			return this.handle((Click<ChestClickData>) click);
		}

		public Builder setItem(ItemStack item)
		{
			this.item = item;

			return this;
		}

		public Builder setSlots(int... slots)
		{
			this.slots = slots;

			return this;
		}

		@Override
		public ChestButton build()
		{
			return new ChestButton(this.panel, this.clicks, this.item, this.slots);
		}

	}

}
