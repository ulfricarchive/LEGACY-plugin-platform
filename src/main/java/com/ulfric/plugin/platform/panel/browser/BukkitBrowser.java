package com.ulfric.plugin.platform.panel.browser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import com.ulfric.commons.spigot.panel.Panel;
import com.ulfric.commons.spigot.panel.browser.Browser;

public class BukkitBrowser implements Browser {

	private final List<Panel> tabs = new ArrayList<>();
	private final Player owner;

	private int index;
	private boolean open = false;

	public BukkitBrowser(Player owner)
	{
		this.owner = owner;
	}

	@Override
	public Player owner()
	{
		return this.owner;
	}

	@Override
	public boolean isOpen()
	{
		return this.open;
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
		this.ensureIndexValid();
	}

	@Override
	public void previousTab()
	{
		this.ensureNonEmpty();
		this.index--;
		this.ensureIndexValid();
	}

	@Override
	public void resetSession()
	{
		this.tabs.clear();
	}

	@Override
	public void addTab(Panel panel)
	{
		this.index++;
		this.ensureIndexValid();
		this.tabs.add(this.index, panel);
	}

	@Override
	public void display()
	{
		this.ensureNonEmpty();
		this.ensureOwned();

		Panel panel = this.currentTab();

		this.open = true;
		panel.open(this);
	}

	public void panelClosed()
	{
		this.open = false;
	}

	private void ensureIndexValid()
	{
		if (this.index >= this.tabs.size())
		{
			this.index = 0;
		}

		if (this.index < 0)
		{
			if (!this.tabs.isEmpty())
			{
				this.index = this.tabs.size() - 1;
			}
		}
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
