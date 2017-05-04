package com.ulfric.plugin.platform.panel.chest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.ulfric.commons.bean.Bean;

public class ChestTemplate extends Bean implements ChestPanelBuilder {

	private final ChestPanel.Builder builder;
	private final String[] template;
	private final Map<String, ChestTemplateMapping> mappings = new HashMap<>();

	ChestTemplate(ChestPanel.Builder builder, String... template)
	{
		this.builder = builder;
		this.template = template;
	}

	private void buildTemplate()
	{
		int templateLength = this.template.length;

		for (int row = 0; row < templateLength; row++)
		{
			String line = this.template[row];

			int lineLength = line.length();

			if (lineLength != 9)
			{
				throw new IllegalStateException("Line must have 9 characters in template");
			}

			final int finalRow = row;
			this.mappings.forEach((key, mapping) ->
			{
				List<Integer> slots = new ArrayList<>();

				for (int i = 0; i < lineLength; i++)
				{
					String character = line.substring(i, i + 1);

					if (key.equalsIgnoreCase(character))
					{
						slots.add((finalRow * 9) + i);
					}
				}

				mapping.getButton().setSlots(ArrayUtils.toPrimitive(slots.toArray(new Integer[0])));

				this.builder.addBuiltButton(mapping.getButton().buildButton());
			});
		}
	}

	@Override
	public ChestPanelBuilder setTitle(String title)
	{
		this.buildTemplate();

		return this.builder.setTitle(title);
	}

	@Override
	public ChestButton.Builder addButton()
	{
		this.buildTemplate();

		return this.builder.addButton();
	}

	@Override
	public void addBuiltButton(ChestButton button)
	{
		this.builder.addBuiltButton(button);
	}

	@Override
	public ChestTemplate template(String... rows)
	{
		this.buildTemplate();

		return this.builder.template(rows);
	}

	public ChestTemplateMapping map(String key)
	{
		ChestTemplateMapping mapping = new ChestTemplateMapping(this);

		this.mappings.put(key, mapping);

		return mapping;
	}

	@Override
	public ChestPanel build()
	{
		this.buildTemplate();

		return this.builder.build();
	}

}
