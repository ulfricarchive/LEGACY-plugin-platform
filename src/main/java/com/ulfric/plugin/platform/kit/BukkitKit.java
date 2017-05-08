package com.ulfric.plugin.platform.kit;

import java.util.List;
import java.util.Objects;

import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.spigot.kit.Kit;

class BukkitKit implements Kit {

	static Builder builder()
	{
		return new Builder();
	}

	private final String kitName;
	private final List<ItemStack> contents;
	private final long cooldown;

	BukkitKit(String kitName, List<ItemStack> contents, long cooldown)
	{
		this.kitName = kitName;
		this.contents = contents;
		this.cooldown = cooldown;
	}

	@Override
	public String kitName()
	{
		return this.kitName;
	}

	@Override
	public List<ItemStack> contents()
	{
		return this.contents;
	}

	@Override
	public long cooldown()
	{
		return this.cooldown;
	}

	static final class Builder implements org.apache.commons.lang3.builder.Builder<BukkitKit>
	{

		private String kitName;
		private List<ItemStack> contents;
		private long cooldown;

		Builder setKitName(String kitName)
		{
			this.kitName = kitName;

			return this;
		}

		Builder setContents(List<ItemStack> contents)
		{
			this.contents = contents;

			return this;
		}

		Builder setCooldown(long cooldown)
		{
			this.cooldown = cooldown;

			return this;
		}

		@Override
		public BukkitKit build()
		{
			Objects.requireNonNull(this.kitName);
			Objects.requireNonNull(this.contents);

			return new BukkitKit(this.kitName, this.contents, this.cooldown);
		}

	}

}
