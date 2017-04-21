package com.ulfric.plugin.platform.economy;

import java.util.List;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Alias;
import com.ulfric.commons.spigot.economy.Currency;
import com.ulfric.dragoon.Dynamic;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;

enum DynamicBalanceCommand {

	;

	public static Class<?> createBalanceCommand(Currency currency)
	{
		DynamicType.Builder<?> command = new ByteBuddy()
				.redefine(BalanceCommand.class)
				.implement(Dynamic.class)
				.annotateType(DynamicBalanceCommand.createDynamicNamed(currency.getBalanceCommandName()))
				.annotateType(DynamicBalanceCommand.createDynamicAlias(currency.getBalanceCommandAliases()))
				.annotateType(DynamicBalanceCommand.createDynamicCurrencyType(currency.getName()));

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

}