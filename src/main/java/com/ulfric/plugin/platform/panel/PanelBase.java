package com.ulfric.plugin.platform.panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ulfric.commons.spigot.panel.Panel;
import com.ulfric.commons.spigot.panel.PanelExtension;
import com.ulfric.commons.spigot.panel.PanelInventory;

public abstract class PanelBase implements Panel {

	private final List<PanelExtension> extensions = new ArrayList<>();
	private final Player holder;

	public PanelBase(Player holder)
	{
		this.holder = holder;
		this.extensions.add(this::buildInventory);
	}

	protected abstract void buildInventory(PanelInventory inventory);

	@Override
	public Player holder()
	{
		return this.holder;
	}

	@Override
	public List<PanelExtension> extensions()
	{
		return Collections.unmodifiableList(this.extensions);
	}

	@Override
	public void extend(PanelExtension extension)
	{
		this.extensions.add(extension);
	}

	@Override
	public Inventory build()
	{
		BukkitPanelInventory inventory = new BukkitPanelInventory();

		this.extensions.forEach(extension -> extension.manipulate(inventory));

		return inventory.finalInventory();
	}

}
