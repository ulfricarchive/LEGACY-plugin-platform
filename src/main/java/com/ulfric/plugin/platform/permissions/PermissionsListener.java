package com.ulfric.plugin.platform.permissions;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permissible;

import com.ulfric.commons.data.PersistentGroup;
import com.ulfric.commons.exception.Try;
import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.event.server.ServerShutdownEvent;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;

class PermissionsListener implements Listener {

	private Field permissionInjection;

	public Field getPermissionInjection(Player player)
	{
		if (this.permissionInjection != null)
		{
			return this.permissionInjection;
		}

		Field permissionInjection = Try.to(() -> player.getClass().getField("perm"));
		permissionInjection.setAccessible(true);
		this.permissionInjection = permissionInjection;
		return permissionInjection;
	}

	@EventHandler
	private void onPreJoin(AsyncPlayerPreLoginEvent event)
	{
		this.getEntity(event.getUniqueId());
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		PermissionEntity entity = this.getEntity(player.getUniqueId());
		Permissible oldPermissions = (Permissible) Try.to(() -> this.permissionInjection.get(player));
		BukkitPermissible permissible = new BukkitPermissible(oldPermissions, entity);
		Try.to(() -> this.permissionInjection.set(player, permissible));
	}

	@EventHandler
	private void onShutdown(ServerShutdownEvent event)
	{
		Permissions permissions = Permissions.getService();
		if (permissions instanceof PersistentGroup)
		{
			((PersistentGroup) permissions).saveAll();
		}
	}

	private PermissionEntity getEntity(UUID uniqueId)
	{
		Identity identity = Identity.of(uniqueId);
		Permissions permissions = Permissions.getService();
		return permissions.getPermissionEntity(identity);
	}

}