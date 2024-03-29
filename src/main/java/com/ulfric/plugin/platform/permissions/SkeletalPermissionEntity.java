package com.ulfric.plugin.platform.permissions;

import java.util.UUID;

import com.ulfric.commons.bean.Bean;
import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.permissions.PermissionEntity;

abstract class SkeletalPermissionEntity extends Bean implements PermissionEntity {

	private final Identity identity;

	SkeletalPermissionEntity()
	{
		this(Identity.of(UUID.randomUUID()));
	}

	SkeletalPermissionEntity(Identity identity)
	{
		this.identity = identity;
	}

	@Override
	public final Identity getIdentity()
	{
		return this.identity;
	}

}