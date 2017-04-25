package com.ulfric.plugin.platform.text;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

final class TemporaryCommandSender implements CommandSender {

	public static final TemporaryCommandSender SHARED = new TemporaryCommandSender();

	private final String name = "TEMP#" + UUID.randomUUID().toString();

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void sendMessage(String message)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendMessage(String[] messages)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Server getServer()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPermissionSet(String name)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPermissionSet(Permission perm)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasPermission(String name)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasPermission(Permission perm)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void recalculatePermissions()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOp()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setOp(boolean op)
	{
		throw new UnsupportedOperationException();
	}

}