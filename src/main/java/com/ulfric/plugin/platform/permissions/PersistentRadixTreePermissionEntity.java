package com.ulfric.plugin.platform.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ulfric.commons.data.Persistent;
import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.identity.UniqueIdUtils;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;

class PersistentRadixTreePermissionEntity extends RadixTreePermissionEntity implements Persistent {

	private final PersistentData data;
	private boolean needsWrite;

	PersistentRadixTreePermissionEntity(Identity identity, PersistentData data)
	{
		super(identity);
		this.data = data;
		this.loadPermissions();
	}

	private void loadPermissions()
	{
		this.loadExplicitPermissions();
		this.loadExplicitParents();
	}

	private void loadExplicitPermissions()
	{
		List<String> permissions = this.data.getStringList("permissions");
		permissions.forEach(this::add);
	}

	private void loadExplicitParents()
	{
		Permissions service = Permissions.getService();
		this.data.getStringList("parents")
			.stream()
			.map(parent ->
			{
				UUID uniqueId = UniqueIdUtils.parseUniqueId(parent);
				return uniqueId == null ? parent : uniqueId;
			})
			.map(Identity::of)
			.map(service::getPermissionEntity)
			.filter(Objects::nonNull)
			.forEach(this::add);
	}

	@Override
	public void save()
	{
		if (this.needsWrite)
		{
			this.unmarkForWrite();

			this.data.set("permissions", new ArrayList<>(this.permissions));

			List<String> parents = this.parents.stream()
					.map(PermissionEntity::getIdentity)
					.map(Object::toString)
					.collect(Collectors.toList());
			this.data.set("parents", parents);
		}
	}

	@Override
	public void markForWrite()
	{
		this.needsWrite = true;
	}

	@Override
	public void unmarkForWrite()
	{
		this.needsWrite = false;
	}

	@Override
	public void add(String node)
	{
		this.markForWrite();
		super.add(node);
	}

	@Override
	public void add(PermissionEntity parent)
	{
		this.markForWrite();
		super.add(parent);
	}

}