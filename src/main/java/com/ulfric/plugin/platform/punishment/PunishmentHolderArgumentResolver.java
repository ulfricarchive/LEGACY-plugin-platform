package com.ulfric.plugin.platform.punishment;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import com.google.common.net.InetAddresses;
import com.ulfric.commons.identity.UniqueIdUtils;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.argument.ArgumentResolver;
import com.ulfric.commons.spigot.network.InetAddressHash;
import com.ulfric.commons.spigot.player.PlayerUtils;
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

		OfflinePlayer player = PlayerUtils.getOfflinePlayer(argument);
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
			return InetAddresses.forString(host);
		}
		catch (IllegalArgumentException thatsOk)
		{
			return null;
		}
	}

}