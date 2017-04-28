package com.ulfric.plugin.platform.droptable;

import com.ulfric.plugin.platform.droptable.Drop.Bounds;
import java.util.ArrayList;
import java.util.List;

public class DropTable {

	private final XORShiftRandom random = new XORShiftRandom();
	private final List<Drop> dropList;
	private int totalWeight = 0;

	DropTable()
	{
		this.dropList = new ArrayList<>();
	}

	public void add(Drop drop)
	{
		dropList.add(drop);
		weights();
	}

	public void addAll(List<Drop> drops)
	{
		dropList.addAll(drops);
		weights();
	}

	public void weights()
	{
		Bounds lastBounds = null;

		for (Drop drop : this.dropList)
		{
			if (lastBounds == null)
			{
				drop.setBounds(0, drop.getWeight());
			} else
			{
				drop.setBounds(lastBounds.getMax(), lastBounds.getMax() + drop.getWeight());
			}

			lastBounds = drop.getBounds();
		}

		this.totalWeight = lastBounds == null ? 0 : lastBounds.getMax();
	}

	public Drop nextDrop()
	{
		int roll = this.random.nextInt(this.totalWeight);

		for (Drop drop : this.dropList)
		{
			if (roll >= drop.getBounds().getMin() && roll <= drop.getBounds().getMax())
			{
				return drop;
			}
		}

		return null;
	}

	private class XORShiftRandom {

		private long last;

		XORShiftRandom()
		{
			this(System.currentTimeMillis());
		}

		XORShiftRandom(long seed)
		{
			this.last = seed;
		}

		int nextInt(int max)
		{
			last ^= (last << 21);
			last ^= (last >>> 35);
			last ^= (last << 4);
			int out = (int) last % max;
			return (out < 0) ? -out : out;
		}

	}

}
