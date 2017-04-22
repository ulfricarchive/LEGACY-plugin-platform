package com.ulfric.plugin.platform.economy;

import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
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
import com.ulfric.dragoon.Dynamic;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;

public class BalanceCommand implements Command {

	public static Class<?> createBalanceCommand(Currency currency)
	{
		DynamicType.Builder<?> command = new ByteBuddy()
				.redefine(BalanceCommand.class)
				.implement(Dynamic.class)
				.annotateType(BalanceCommand.createDynamicNamed(currency.getBalanceCommandName()))
				.annotateType(BalanceCommand.createDynamicAlias(currency.getBalanceCommandAliases()))
				.annotateType(BalanceCommand.createDynamicCurrencyType(currency.getName()));

		return command.make().load(currency.getClass().getClassLoader()).getLoaded();
	}

	private static Name createDynamicNamed(String name)
	{
		return new Name()
		{
			@Override
			public Class<Name> annotationType()
			{
				return Name.class;
			}

			@Override
			public String value()
			{
				return name;
			}
		};
	}

	private static Alias createDynamicAlias(List<String> aliases)
	{
		String[] aliasesArray = aliases.toArray(new String[aliases.size()]);
		return new Alias()
		{
			@Override
			public Class<Alias> annotationType()
			{
				return Alias.class;
			}

			@Override
			public String[] value()
			{
				return aliasesArray;
			}
		};
	}

	private static CurrencyType createDynamicCurrencyType(String name)
	{
		return new CurrencyType()
		{
			@Override
			public Class<CurrencyType> annotationType()
			{
				return CurrencyType.class;
			}

			@Override
			public String value()
			{
				return name;
			}
		};
	}

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
		return currency.getSymbol() + FormatUtils.formatLong(account.getBalance(currency));
	}

}