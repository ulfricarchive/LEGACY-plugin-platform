package com.ulfric.plugin.platform.text.color;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.text.ChatUtils;
import com.ulfric.commons.spigot.text.Color;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class ColorService implements Color {

	@Inject
	private Container owner;

	private Map<ColorType, String> colors = new HashMap<>();

	@Initialize
	private void initialize()
	{
		DataStore folder = Data.getDataStore(this.owner);
		this.colors = this.getColors(folder.getDefault());
	}

	private Map<ColorType, String> getColors(PersistentData data)
	{
		Map<ColorType, String> colors = new EnumMap<>(ColorType.class);
		for (ColorType color : ColorType.values())
		{
			String key = color.name().toLowerCase();
			colors.put(color, ChatUtils.format(data.getString(key, StringUtils.EMPTY)));
		}
		return colors;
	}

	@Override
	public String primary(CommandSender sender)
	{
		return this.colors.get(ColorType.PRIMARY);
	}

	@Override
	public String detail(CommandSender sender)
	{
		return this.colors.get(ColorType.DETAIL);
	}

	@Override
	public String button(CommandSender sender)
	{
		return this.colors.get(ColorType.BUTTON);
	}

	@Override
	public String warning(CommandSender sender)
	{
		return this.colors.get(ColorType.WARNING);
	}

	private enum ColorType
	{
		PRIMARY,
		DETAIL,
		BUTTON,
		WARNING;
	}

}
