package com.ulfric.plugin.platform.permissions;

import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permissible;

import com.ulfric.commons.data.Persistent;
import com.ulfric.commons.exception.Try;
import com.ulfric.commons.identity.Identity;
import com.ulfric.commons.spigot.event.player.AsyncPlayerQuitEvent;
import com.ulfric.commons.spigot.permissions.PermissionEntity;
import com.ulfric.commons.spigot.permissions.Permissions;

class PermissionsListener implements Listener {

	private Field permissionInjection;

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
		Permissible oldPermissions = this.getOldPermissible(player);
		BukkitPermissible permissible = new BukkitPermissible(oldPermissions, entity);
		Try.to(() -> this.permissionInjection.set(player, permissible));
	}

	@EventHandler
	private void onQuit(AsyncPlayerQuitEvent event)
	{
		PermissionEntity entity = this.getEntity(event.getPlayer().getUniqueId());
		if (entity instanceof Persistent)
		{
			((Persistent) entity).save();
		}
	}

	private PermissionEntity getEntity(UUID uniqueId)
	{
		Identity identity = Identity.of(uniqueId);
		Permissions permissions = Permissions.getService();
		return permissions.getPermissionEntity(identity);
	}

	private Permissible getOldPermissible(Player player)
	{
		return (Permissible) Try.to(() -> this.getPermissionInjection(player).get(player));
	}

	private Field getPermissionInjection(Player player)
	{
		if (this.permissionInjection != null)
		{
			return this.permissionInjection;
		}

		Field permissionInjection = FieldUtils.getField(player.getClass(), "perm", true);
		this.permissionInjection = permissionInjection;
		return permissionInjection;
	}

}