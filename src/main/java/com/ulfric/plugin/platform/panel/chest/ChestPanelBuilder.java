package com.ulfric.plugin.platform.panel.chest;

import org.apache.commons.lang3.builder.Builder;

public interface ChestPanelBuilder extends Builder<ChestPanel> {

	ChestPanelBuilder setTitle(String title);

	ChestButton.Builder addButton();

	void addBuiltButton(ChestButton button);

	ChestTemplate template(String... rows);

	ChestPanel build();

}
