package com.ulfric.plugin.platform.panel.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.click.Button;
import com.ulfric.commons.spigot.panel.click.CancelledClick;
import com.ulfric.commons.spigot.panel.click.Click;
import com.ulfric.commons.spigot.panel.click.ClickResult;

public class ChestButton implements Button {

	static Builder builder(ChestPanel.Builder builder)
	{
		return new Builder(builder);
	}

	private final List<Click<ChestClickData>> clicks;
	private final ItemStack item;
	private final int[] slots;

	ChestButton(List<Click<ChestClickData>> clicks, ItemStack item, int[] slots)
	{
		this.clicks = clicks;
		this.item = item;
		this.slots = slots;
	}

	ItemStack getItem()
	{
		return this.item;
	}

	int[] getSlots()
	{
		return this.slots;
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

	public static class Builder extends ChestPanel.Builder {

		private final List<Click<ChestClickData>> clicks = new ArrayList<>();
		private final ChestPanel.Builder builder;

		private ItemStack item;
		private int[] slots;

		private Builder(ChestPanel.Builder builder)
		{
			this.builder = builder;
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
		public Builder addButton()
		{
			ChestButton button = new ChestButton(this.clicks, this.item, this.slots);

			this.add(button);

			return this.builder.addButton();
		}

		@Override
		public ChestBrowserButton.Builder addBrowserButton()
		{
			ChestButton button = new ChestButton(this.clicks, this.item, this.slots);

			this.add(button);

			return this.builder.addBrowserButton();
		}

		@Override
		public ChestPanel build()
		{
			ChestButton button = new ChestButton(this.clicks, this.item, this.slots);

			this.add(button);

			return this.builder.build();
		}

		@Override
		void add(ChestButton button)
		{
			this.builder.add(button);
		}

	}

}
