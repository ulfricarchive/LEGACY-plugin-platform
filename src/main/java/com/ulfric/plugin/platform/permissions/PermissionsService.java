package com.ulfric.plugin.platform.permissions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ulfric.commons.data.Persistent;
import com.ulfric.commons.data.PersistentGroup;
import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;

class PermissionsService implements Permissions, PersistentGroup {

	@Inject
	private Container container;

	private DataStore playerData;
	private DataStore groupData;

	private final Map<Identity, PermissionEntity> entities = new HashMap<>();

	@Initialize
	private void initialize()
	{
		this.playerData = PlayerData.getPlayerData(this.container);
		this.groupData = Data.getDataStore(this.container).getDataStore("groups");
	}

	@Override
	public PermissionEntity getPermissionEntity(Identity identity)
	{
		return this.entities.computeIfAbsent(identity,
				key -> new PersistentRadixTreePermissionEntity(key, this.getData(identity)));
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
	public void saveAll()
	{
		for (PermissionEntity entity : this.entities.values())
		{
			if (entity instanceof Persistent)
			{
				((Persistent) entity).save();
			}
		}
	}

}