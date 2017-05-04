package com.ulfric.plugin.platform.panel.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.panel.click.CancelledClick;
import com.ulfric.commons.spigot.panel.click.Click;

class ChestBrowserButton extends ChestButton {

	static Builder browserBuilder(ChestPanel.Builder builder)
	{
		return new Builder(builder);
	}

	private ChestBrowserButton(List<Click<ChestClickData>> clicks, ItemStack item, int[] slots)
	{
		super(clicks, item, slots);
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

		public ChestBrowserButton.Builder handle(Click<ChestClickData> click)
		{
			this.clicks.add(click);

			return this;
		}

		public ChestBrowserButton.Builder handle(CancelledClick<ChestClickData> click)
		{
			return this.handle((Click<ChestClickData>) click);
		}

		public ChestBrowserButton.Builder setItem(ItemStack item)
		{
			this.item = item;

			return this;
		}

		public ChestBrowserButton.Builder setSlots(int... slots)
		{
			this.slots = slots;

			return this;
		}

		@Override
		public ChestButton.Builder addButton()
		{
			this.add(this.buildButton());

			return this.builder.addButton();
		}

		@Override
		public ChestPanelBuilder setTitle(String title)
		{
			this.add(this.buildButton());

			return this.builder.setTitle(title);
		}

		@Override
		public ChestBrowserButton.Builder addBrowserButton()
		{
			this.add(this.buildButton());

			return this.builder.addBrowserButton();
		}

		@Override
		public ChestTemplate template(String... rows)
		{
			this.add(this.buildButton());

			return this.builder.template(rows);
		}

		@Override
		public ChestPanel build()
		{
			this.add(this.buildButton());

			return this.builder.build();
		}

		private ChestButton buildButton()
		{
			return new ChestBrowserButton(this.clicks, this.item, this.slots);
		}

		private void add(ChestButton button)
		{
			this.builder.addBuiltButton(button);
		}

	}

}
