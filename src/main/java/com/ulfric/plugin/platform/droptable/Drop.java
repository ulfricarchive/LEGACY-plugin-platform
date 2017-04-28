package com.ulfric.plugin.platform.droptable;

public class Drop<T> {

	private final T value;
	private final int weight;
	private Bounds bounds = null;

	public Drop(T value, int weight)
	{
		this.value = value;
		this.weight = weight;
	}

	public T getValue()
	{
		return value;
	}

	public int getWeight()
	{
		return weight;
	}

	public Bounds getBounds()
	{
		return bounds;
	}

	public void setBounds(Bounds bounds)
	{
		this.bounds = bounds;
	}

	public void setBounds(int min, int max)
	{
		this.bounds = new Bounds(min, max);
	}

	class Bounds {

		private int min;
		private int max;

		Bounds(int min, int max)
		{
			this.min = min;
			this.max = max;
		}

		int getMin()
		{
			return min;
		}

		int getMax()
		{
			return max;
		}
	}

}
