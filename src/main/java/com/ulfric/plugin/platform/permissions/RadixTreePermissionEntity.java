package com.ulfric.plugin.platform.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.permissions.PermissionEntity;

public final class RadixTreePermissionEntity extends SkeletalPermissionEntity {

	public static final char PREFIX_SEPERATOR = '-';

	public static RadixTreePermissionEntity create(Identity identity)
	{
		Objects.requireNonNull(identity);

		return new RadixTreePermissionEntity(identity);
	}

	private final List<RadixTreePermissionEntity> parents;
	private final Set<String> permissions;
	private final Map<String, Boolean> stateCache;

	private RadixTreePermissionEntity(Identity identity)
	{
		super(identity);

		this.parents = new ArrayList<>();
		this.stateCache = new HashMap<>();
		this.permissions = new HashSet<>();
	}

	@Override
	public void add(String node)
	{
		this.permissions.add(node);
	}

	@Override
	public void add(PermissionEntity parent)
	{
		if (parent instanceof RadixTreePermissionEntity)
		{
			this.parents.add((RadixTreePermissionEntity) parent);
			return;
		}

		throw new UnsupportedOperationException("RadixTreePermissionEntity is incompatible with " + parent.getClass());
	}

	@Override
	public boolean test(String node)
	{
		return this.stateCache.computeIfAbsent(node, this::forceLookup);
	}

	private boolean forceLookup(String node)
	{
		int split = node.indexOf(RadixTreePermissionEntity.PREFIX_SEPERATOR);
		if (split == -1)
		{
			return this.containsExact(node);
		}

		String subnode = node.substring(0, split);
		return this.forceLookup(subnode);
	}

	private boolean containsExact(String node)
	{
		if (this.permissions.contains(node))
		{
			return true;
		}

		for (RadixTreePermissionEntity parent : this.parents)
		{
			if (parent.containsExact(node))
			{
				return true;
			}
		}

		return false;
	}

}