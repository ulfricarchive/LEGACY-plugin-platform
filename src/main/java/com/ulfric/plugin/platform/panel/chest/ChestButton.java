package com.ulfric.plugin.platform.panel.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.click.Button;
import com.ulfric.commons.spigot.panel.click.CancelledClick;
import com.ulfric.commons.spigot.panel.click.Click;
import com.ulfric.commons.spigot.panel.click.ClickResult;

public class ChestButton implements Button {

	static Builder builder(ChestPanelBuilder builder)
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
		private final ChestPanelBuilder builder;

		private ItemStack item;
		private int[] slots;

		Builder(ChestPanelBuilder builder)
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
			this.add(this.buildButton());

			return this.builder.addButton();
		}

		@Override
		public ChestTemplate template(String... rows)
		{
			this.add(this.buildButton());

			return this.builder.template(rows);
		}

		@Override
		public ChestPanelBuilder setTitle(String title)
		{
			this.add(this.buildButton());

			return this.builder.setTitle(title);
		}

		@Override
		public ChestPanel build()
		{
			ChestButton button = this.buildButton();

			this.add(button);

			return this.builder.build();
		}

		ChestButton buildButton()
		{
			return new ChestButton(this.clicks, this.item, this.slots);
		}

		private void add(ChestButton button)
		{
			this.builder.addBuiltButton(button);
		}

	}

}
