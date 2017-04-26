package com.ulfric.plugin.platform.punishment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.ulfric.commons.identity.UniqueIdUtils;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.argument.ArgumentResolver;
import com.ulfric.commons.spigot.network.InetAddressHash;
import com.ulfric.commons.spigot.punishment.PunishmentHolder;
import com.ulfric.commons.spigot.punishment.Punishments;

public class PunishmentHolderArgumentResolver implements ArgumentResolver<PunishmentHolder> {

	@Override
	public PunishmentHolder apply(Context context, String argument)
	{
		Punishments punishments = Punishments.getService();

		UUID uniqueId = UniqueIdUtils.parseUniqueId(argument);
		if (uniqueId != null)
		{
			return punishments.getPunishmentHolder(uniqueId);
		}

		InetAddressHash addresses = InetAddressHash.getService();
		String address = addresses.getInetAddress(argument);
		InetAddress inet = this.getAddress(address == null ? argument : address);
		if (inet != null)
		{
			return punishments.getPunishmentHolder(inet);
		}

		@SuppressWarnings("deprecation")
		OfflinePlayer player = Bukkit.getOfflinePlayer(argument);
		if (player == null)
		{
			return null;
		}
		return punishments.getPunishmentHolder(player.getUniqueId());
	}

	private InetAddress getAddress(String host)
	{
		try
		{
			return InetAddress.getByName(host);
		}
		catch (UnknownHostException e)
		{
			return null;
		}
	}

}