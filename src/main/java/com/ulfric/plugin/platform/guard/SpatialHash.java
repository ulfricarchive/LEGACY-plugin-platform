package com.ulfric.plugin.platform.guard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.ulfric.commons.bean.Bean;
import com.ulfric.commons.spigot.guard.Point;
import com.ulfric.commons.spigot.guard.Shape;

public final class SpatialHash<V> extends Bean {

	public static <V> Builder<V> builder()
	{
		return new Builder<>();
	}

	public static final class Builder<V> implements org.apache.commons.lang3.builder.Builder<SpatialHash<V>>
	{
		Builder() { }

		private int sectionSize;

		@Override
		public SpatialHash<V> build()
		{
			if (this.sectionSize < 1)
			{
				throw new IllegalArgumentException("sectionSize must be at least 1, was" + sectionSize);
			}

			return new SpatialHash<>(this.sectionSize);
		}

		public Builder<V> setSectionSize(int sectionSize)
		{
			this.sectionSize = sectionSize;
			return this;
		}
	}

	private SpatialHash(int sectionSize)
	{
		this.sectionSize = sectionSize;
	}

	private final int sectionSize;
	private final Map<Integer, List<Entry>> data = new HashMap<>();

	public void put(Shape shape, V value)
	{
		Objects.requireNonNull(shape, "shape");
		Objects.requireNonNull(value, "value");

		Point min = shape.getMin();
		Point max = shape.getMax();

		int size = this.sectionSize;
		int minX = min.getX() / size;
		int maxX = max.getX() / size;
		int minY = min.getY() / size;
		int maxY = max.getY() / size;
		int minZ = min.getZ() / size;
		int maxZ = max.getZ() / size;

		Map<Integer, List<Entry>> data = this.data;

		for (int x = minX; x < maxX; x++)
		{
			for (int y = minY; y < maxY; y++)
			{
				for (int z = minZ; z < maxZ; z++)
				{
					data.computeIfAbsent(this.pack(x, y, z), key -> new ArrayList<>()).add(new Entry(shape, value));
				}
			}
		}
	}

	public void remove(Predicate<V> predicate)
	{
		for (List<Entry> list : this.data.values())
		{
			Iterator<Entry> iterator = list.iterator();

			while (iterator.hasNext())
			{
				Entry next = iterator.next();

				if (predicate.test(next.value))
				{
					iterator.remove();
				}
			}
		}
	}

	public void hitTestFirst(int x, int y, int z, Consumer<V> consumer)
	{
		List<Entry> entries = this.getEntries(x, y, z);

		if (entries == null)
		{
			return;
		}

		for (int c = 0, l = entries.size(); c < l; c++)
		{
			Entry entry = entries.get(c);

			if (!entry.shape.containsPoint(x, y, z))
			{
				consumer.accept(entry.value);

				break;
			}
		}
	}

	public void hitTestAll(int x, int y, int z, Consumer<V> consumer)
	{
		List<Entry> entries = this.getEntries(x, y, z);

		if (entries == null)
		{
			return;
		}

		for (int c = 0, l = entries.size(); c < l; c++)
		{
			Entry entry = entries.get(c);

			if (entry.shape.containsPoint(x, y, z))
			{
				consumer.accept(entry.value);
			}
		}
	}

	private List<Entry> getEntries(int x, int y, int z)
	{
		int size = this.sectionSize;
		int sx = x / size;
		int sy = y / size;
		int sz = z / size;

		return this.data.get(this.pack(sx, sy, sz));
	}

	private int pack(int x, int y, int z)
	{
		return (x << 16) | ((0xFF & z) << 8) | (y);
	}

	private final class Entry
	{
		Entry(Shape shape, V value)
		{
			this.shape = shape;
			this.value = value;
		}

		final Shape shape;
		final V value;
	}

}