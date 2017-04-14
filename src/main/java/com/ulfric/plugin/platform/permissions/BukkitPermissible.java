package com.ulfric.plugin.platform.permissions;

import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.ServerOperator;

import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.permissions.PermissionEntity;

final class BukkitPermissible extends PermissibleBase {

	private final PermissionEntity entity;
	private final Object metadata;

	public BukkitPermissible(ServerOperator opable, PermissionEntity entity)
	{
		super(opable);
		this.entity = entity;
		this.metadata = entity.getIdentity().getObject();
	}

	@Override
	public boolean hasPermission(Permission permission)
	{
		return this.hasPermission(permission.getName());
	}

	@Override
	public boolean hasPermission(String node)
	{
		boolean result = this.isOp() || this.entity.test(node);
		if (!result)
		{
			Metadata.write(this.metadata, MetadataDefaults.NO_PERMISSION, node);
		}
		return result;
	}

}