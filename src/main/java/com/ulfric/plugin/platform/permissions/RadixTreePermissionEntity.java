package com.ulfric.plugin.platform.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;

public class RadixTreePermissionEntity extends SkeletalPermissionEntity {

	public static final char PREFIX_SEPERATOR = '-';

	protected final List<RadixTreePermissionEntity> parents;
	protected final Set<String> permissions;
	private final Map<String, Boolean> stateCache;

	protected RadixTreePermissionEntity(Identity identity)
	{
		super(identity);

		this.parents = new ArrayList<>();
		this.stateCache = new HashMap<>();
		this.permissions = new HashSet<>();
	}

	@Override
	public void add(String node)
	{
		if (this.permissions.add(node))
		{
			this.clearCache();
		}
	}

	@Override
	public void add(PermissionEntity parent)
	{
		if (parent instanceof RadixTreePermissionEntity)
		{
			if (this.parents.add((RadixTreePermissionEntity) parent))
			{
				this.clearCache();
			}
			return;
		}

		throw new UnsupportedOperationException("RadixTreePermissionEntity is incompatible with " + parent.getClass());
	}
	
	@Override
	public void remove(String node)
	{
		if (this.permissions.remove(node))
		{
			this.clearCache();
		}
	}
	
	@Override
	public void remove(PermissionEntity parent)
	{
		if (parent instanceof RadixTreePermissionEntity)
		{
			if (this.parents.remove(parent))
			{
				this.clearCache();
			}
		}
	}

	private void clearCache()
	{
		this.stateCache.clear();
	}

	@Override
	public Stream<PermissionEntity> getParents()
	{
		Permissions service = Permissions.getService();
		return new ArrayList<>(this.parents).stream().map(Identity::of).map(service::getPermissionEntity);
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