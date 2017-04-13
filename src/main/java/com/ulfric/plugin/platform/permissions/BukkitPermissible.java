package com.ulfric.plugin.platform.permissions;

import java.util.Set;

import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.permissions.PermissionEntity;

final class BukkitPermissible implements Permissible {

	private final Permissible delegate;

	private final PermissionEntity entity;
	private final Object metadata;

	public BukkitPermissible(Permissible delegate, PermissionEntity entity)
	{
		this.delegate = delegate;
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

	@Override
	public boolean isOp()
	{
		return this.delegate.isOp();
	}

	@Override
	public void setOp(boolean op)
	{
		this.delegate.setOp(op);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks)
	{
		return this.delegate.addAttachment(plugin, ticks);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String node, boolean value, int ticks)
	{
		return this.delegate.addAttachment(plugin, node, value, ticks);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String node, boolean value)
	{
		return this.delegate.addAttachment(plugin, node, value);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin)
	{
		return this.delegate.addAttachment(plugin);
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment)
	{
		this.delegate.removeAttachment(attachment);
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return this.delegate.getEffectivePermissions();
	}

	@Override
	public boolean isPermissionSet(Permission permission)
	{
		return this.delegate.isPermissionSet(permission);
	}

	@Override
	public boolean isPermissionSet(String node)
	{
		return this.delegate.isPermissionSet(node);
	}

	@Override
	public void recalculatePermissions()
	{
		this.delegate.recalculatePermissions();
	}

}