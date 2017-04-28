package com.ulfric.plugin.platform.droptable;

public class Drop {

	private Object value;
	private int weight;
	private Bounds bounds = null;

	public Drop(Object value, int weight)
	{
		this.value = value;
		this.weight = weight;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
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

		public int getMin()
		{
			return min;
		}

		public int getMax()
		{
			return max;
		}
	}

}
