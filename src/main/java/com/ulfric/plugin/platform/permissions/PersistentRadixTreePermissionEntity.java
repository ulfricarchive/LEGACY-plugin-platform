package com.ulfric.plugin.platform.permissions;

import com.ulfric.commons.data.Persistent;
import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.permissions.PermissionEntity;

class PersistentRadixTreePermissionEntity extends RadixTreePermissionEntity implements Persistent {

	private final PersistentData data;
	private boolean needsWrite;

	PersistentRadixTreePermissionEntity(Identity identity, PersistentData data)
	{
		super(identity);
		this.data = data;
	}

	@Override
	public void save()
	{
		if (this.needsWrite)
		{
			this.data.save();
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