package com.ulfric.plugin.platform.text.color;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.text.Color;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

public class ColorService implements Color {

	@Inject
	private Container owner;

	private final Map<ColorType, String> colors = new HashMap<>();

	@Initialize
	private void initialize()
	{
		DataStore folder = Data.getDataStore(this.owner);

		this.loadColors(folder.getData("defaults"));
	}

	private void loadColors(PersistentData data)
	{
		for (ColorType color : ColorType.values())
		{
			String value = color.toString();

			this.colors.put(color, data.getString(value));
		}
	}

	@Override
	public String primary(CommandSender sender)
	{
		return this.colors.get(ColorType.PRIMARY);
	}

	@Override
	public String secondary(CommandSender sender)
	{
		return this.colors.get(ColorType.SECONDARY);
	}

	@Override
	public String tertiary(CommandSender sender)
	{
		return this.colors.get(ColorType.TERTIARY);
	}

	@Override
	public String warning(CommandSender sender)
	{
		return this.colors.get(ColorType.WARNING);
	}

	private enum ColorType
	{
		PRIMARY("primary"),
		SECONDARY("secondary"),
		TERTIARY("tertiary"),
		WARNING("warning");

		private final String value;

		ColorType(String value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			return this.value;
		}
	}

}
