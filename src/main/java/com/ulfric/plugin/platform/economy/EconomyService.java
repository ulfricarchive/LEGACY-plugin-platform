package com.ulfric.plugin.platform.economy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.NotImplementedException;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.economy.Currency;
import com.ulfric.commons.spigot.economy.CurrencyAmount;
import com.ulfric.commons.spigot.economy.Economy;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class EconomyService implements Economy {

	private static final Pattern NUMBER = Pattern.compile("[^0-9]+");

	@Inject
	private Container owner;

	private DataStore folder;

	private final List<Currency> currencies = new ArrayList<>();
	private final Map<String, Currency> currenciesByName = new HashMap<>();

	@Initialize
	private void initialize()
	{
		this.folder = Data.getDataStore(this.owner).getDataStore("currencies");
		this.folder.loadAllData().forEach(this::loadCurrency);
	}

	private void loadCurrency(PersistentData data)
	{
		Currency currency = Currency.builder()
				.setName(data.getString("name"))
				.setSymbol(data.getString("symbol"))
				.setPattern(data.getString("pattern"))
				.setBalanceCommandName(data.getString("balance-command-name"))
				.setBalanceCommandAliases(data.getStringList("balance-command-aliases"))
				.build();

		this.currencies.add(currency);
		this.currenciesByName.put(currency.getName(), currency);
		this.owner.install(DynamicBalanceCommand.createBalanceCommand(currency));
	}

	@Override
	public Currency getCurrency(String name)
	{
		return this.currenciesByName.get(name);
	}

	@Override
	public CurrencyAmount getCurrencyAmount(String text)
	{
		Currency currency = this.matchCurrency(text);

		if (currency == null)
		{
			return null;
		}

		// TODO utility to do this faster
		String parsable = EconomyService.NUMBER.matcher(text).replaceAll("");

		if (parsable.isEmpty())
		{
			return null;
		}

		long amount = Long.valueOf(parsable);

		if (amount <= 0)
		{
			return null;
		}

		return CurrencyAmount.valueOf(currency, amount);
	}

	private Currency matchCurrency(String text)
	{
		for (Currency currency : this.currencies)
		{
			if (currency.getPattern().matcher(text).matches())
			{
				return currency;
			}
		}

		return null;
	}

	@Override
	public long getBalance(UUID uniqueId, Currency currency)
	{
		// TODO
		throw new NotImplementedException("TODO");
	}

	@Override
	public void setBalance(UUID uniqueId, CurrencyAmount amount)
	{
		// TODO
		throw new NotImplementedException("TODO");
	}

}