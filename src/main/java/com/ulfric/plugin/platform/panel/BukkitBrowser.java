package com.ulfric.plugin.platform.panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ulfric.commons.spigot.panel.Browser;
import com.ulfric.commons.spigot.panel.Panel;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.inject.Inject;

class BukkitBrowser implements Browser {

	private final List<Panel> tabs = new ArrayList<>();

	private Player owner;
	private int index;

	@Inject
	private ObjectFactory factory;

	void setOwner(Player owner)
	{
		this.owner = owner;
	}

	@Override
	public List<Panel> tabs()
	{
		return Collections.unmodifiableList(this.tabs);
	}

	@Override
	public int currentIndex()
	{
		return this.index;
	}

	@Override
	public void nextTab()
	{
		this.ensureNonEmpty();
		this.index++;

		if (this.index == this.tabs.size())
		{
			this.index = 0;
		}
	}

	@Override
	public void previousTab()
	{
		this.ensureNonEmpty();
		this.index--;

		if (this.index == -1)
		{
			this.index = this.tabs.size() - 1;
		}
	}

	@Override
	public void resetSession()
	{
		this.tabs.clear();
	}

	@Override
	public <T extends Panel> T addTab(Class<T> panelClass)
	{
		T panel = this.factory.requestExact(panelClass);

		this.index++;

		this.tabs.add(this.index, panel);

		return panel;
	}

	@Override
	public void display()
	{
		this.ensureNonEmpty();
		this.ensureOwned();

		Panel panel = this.currentTab();

		Inventory inventory = panel.build();

		this.owner.openInventory(inventory);
	}

	private void ensureNonEmpty()
	{
		if (this.tabs().isEmpty())
		{
			throw new IllegalStateException("Browser has no tabs");
		}
	}

	private void ensureOwned()
	{
		if (this.owner == null || !this.owner.isOnline())
		{
			throw new IllegalStateException("Owning player not online (" + this.owner + ")");
		}
	}

}
