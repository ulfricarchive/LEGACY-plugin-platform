package com.ulfric.plugin.platform.economy;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.economy.BankAccount;
import com.ulfric.commons.spigot.economy.Currency;
import com.ulfric.commons.spigot.economy.Economy;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.text.FormatUtils;

public class BalanceCommand implements Command {

	@Argument(optional = true)
	private OfflinePlayer target;

	@Override
	public void run(Context context)
	{
		CommandSender sender = context.getSender();
		Text text = Text.getService();

		if (this.target != null)
		{
			String balance = this.getBalance(this.target);
			Metadata.write(sender, MetadataDefaults.LAST_BALANCE_VIEW, balance);
			Metadata.write(sender, MetadataDefaults.LAST_BALANCE_VIEW_USER, this.target.getName());
			text.sendMessage(sender, "balance-view-other");
			return;
		}

		String balance = this.getBalance(sender);
		Metadata.write(sender, MetadataDefaults.LAST_BALANCE_VIEW, balance);
		text.sendMessage(sender, "balance-view");
	}

	private String getBalance(Object target)
	{
		Economy economy = Economy.getService();

		CurrencyType type = this.getClass().getAnnotation(CurrencyType.class);
		Currency currency = economy.getCurrency(type.value());

		if (!(target instanceof OfflinePlayer))
		{
			return currency.getSymbol() + '0';
		}

		UUID uniqueId = ((OfflinePlayer) target).getUniqueId();
		BankAccount account = economy.getAccount(uniqueId);
		return currency.getSymbol() + FormatUtils.formatLong(account.getBalance());
	}

}