package com.ulfric.plugin.platform.panel.chest;

import java.util.function.Consumer;

import org.bukkit.inventory.ItemStack;

public class ChestTemplateMapping {

	private final ChestTemplate template;
	private final ChestButton.Builder button;

	ChestTemplateMapping(ChestTemplate template)
	{
		this.template = template;
		this.button = ChestButton.builder(template);
	}

	public ChestTemplate to(ItemStack item)
	{
		this.button.setItem(item);

		return this.template;
	}

	public ChestTemplate toButton(Consumer<ChestButton.Builder> buttonMaker)
	{
		buttonMaker.accept(this.button);

		return this.template;
	}

	ChestButton.Builder getButton()
	{
		return this.button;
	}

}
