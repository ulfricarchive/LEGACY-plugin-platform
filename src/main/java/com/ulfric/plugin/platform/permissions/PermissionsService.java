package com.ulfric.plugin.platform.permissions;

import java.util.HashMap;
import java.util.Map;

import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;

class PermissionsService implements Permissions {

	private final Map<Identity, PermissionEntity> entities = new HashMap<>();

	@Override
	public PermissionEntity getPermissionEntity(Identity identity)
	{
		return this.entities.computeIfAbsent(identity, RadixTreePermissionEntity::create);
	}

}