package com.ulfric.plugin.platform.permissions;

import java.util.UUID;

import com.ulfric.commons.data.Persistent;
import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.dragoon.interceptors.Cache;
import com.ulfric.plugin.platform.data.PlayerData;

class PermissionsService implements Permissions {

	@Inject
	private Container owner;

	private DataStore playerData;
	private DataStore groupData;

	@Initialize
	private void initialize()
	{
		this.playerData = PlayerData.getPlayerData(this.owner);
		this.groupData = Data.getDataStore(this.owner).getDataStore("groups");
	}

	@Override
	@Cache
	public PermissionEntity getPermissionEntity(Identity identity)
	{
		PersistentData data = this.getData(identity);
		return new PersistentRadixTreePermissionEntity(identity, data);
	}

	private PersistentData getData(Identity identity)
	{
		Object object = identity.getObject();
		String pointer = object.toString();
		if (object instanceof UUID)
		{
			return this.playerData.getData(pointer);
		}
		return this.groupData.getData(pointer);
	}

	@Override
	public void writePermissionEntity(PermissionEntity entity)
	{
		if (entity instanceof Persistent)
		{
			((Persistent) entity).save();
		}
		this.getData(entity.getIdentity()).save();
	}

}