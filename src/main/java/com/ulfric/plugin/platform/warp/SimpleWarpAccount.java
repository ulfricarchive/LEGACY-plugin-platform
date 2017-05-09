package com.ulfric.plugin.platform.warp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.WarpAccount;

public class SimpleWarpAccount implements WarpAccount {

	private final Map<String, Warp> warps = new CaseInsensitiveMap<>();

	@Override
	public boolean isWarp(String name)
	{
		return this.warps.containsKey(name);
	}

	@Override
	public Warp getWarp(String name)
	{
		return this.warps.get(name);
	}

	@Override
	public List<Warp> getWarps()
	{
		return Collections.unmodifiableList(new ArrayList<>(this.warps.values()));
	}

	@Override
	public void setWarp(Warp warp)
	{
		this.warps.put(warp.getName(), warp);
	}

	@Override
	public void deleteWarp(String name)
	{
		this.warps.remove(name);
	}

}