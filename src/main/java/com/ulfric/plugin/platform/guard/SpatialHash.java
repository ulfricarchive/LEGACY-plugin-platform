package com.ulfric.plugin.platform.guard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.ulfric.commons.bean.Bean;
import com.ulfric.commons.spigot.shape.Point;
import com.ulfric.commons.spigot.shape.Shape;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

final class SpatialHash<V> extends Bean {

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
				throw new IllegalArgumentException("sectionSize must be at least 1, was" + this.sectionSize);
			}

			return new SpatialHash<>(this.sectionSize);
		}

		Builder<V> setSectionSize(int sectionSize)
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
	private final TIntObjectMap<List<Entry>> data = new TIntObjectHashMap<>();

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

		TIntObjectMap<List<Entry>> data = this.data;

		for (int x = minX; x < maxX; x++)
		{
			for (int y = minY; y < maxY; y++)
			{
				for (int z = minZ; z < maxZ; z++)
				{
					int packed = this.pack(x, y, z);
					List<Entry> entries = data.get(packed);
					if (entries == null)
					{
						entries = new ArrayList<>();
						data.put(packed, entries);
					}
					entries.add(new Entry(shape, value));
				}
			}
		}
	}

	public void remove(Predicate<V> predicate)
	{
		for (List<Entry> list : this.data.valueCollection())
		{
			Iterator<Entry> iterator = list.iterator();

			while (iterator.hasNext())
			{
				Entry next = iterator.next();

				if (predicate.test(next.getValue()))
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

		for (Entry entry : entries)
		{
			if (!entry.getShape().containsPoint(x, y, z))
			{
				consumer.accept(entry.getValue());

				break;
			}
		}
	}

	void hitTestAll(int x, int y, int z, Consumer<V> consumer)
	{
		List<Entry> entries = this.getEntries(x, y, z);

		if (entries == null)
		{
			return;
		}

		for (Entry entry : entries)
		{
			if (entry.getShape().containsPoint(x, y, z))
			{
				consumer.accept(entry.getValue());
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
		return (x << 16) | ((0xFF & z) << 8) | (y); // TODO: Magic numbers?
	}

	private final class Entry
	{
		private final Shape shape;
		private final V value;

		Entry(Shape shape, V value)
		{
			this.shape = shape;
			this.value = value;
		}

		private Shape getShape()
		{
			return this.shape;
		}

		private V getValue()
		{
			return this.value;
		}
	}

}