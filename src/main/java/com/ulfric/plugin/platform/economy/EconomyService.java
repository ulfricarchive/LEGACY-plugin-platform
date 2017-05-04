package com.ulfric.plugin.platform.economy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.economy.BankAccount;
import com.ulfric.commons.spigot.economy.Currency;
import com.ulfric.commons.spigot.economy.Economy;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;

class EconomyService implements Economy {

	@Inject
	private Container owner;

	private DataStore playerData;

	private final List<Currency> currencies = new ArrayList<>();
	private final Map<String, Currency> currenciesByName = new HashMap<>();
	private final Map<UUID, BankAccount> accounts = new ConcurrentHashMap<>();

	@Initialize
	private void initialize()
	{
		DataStore folder = Data.getDataStore(this.owner);
		this.loadCurrencies(folder.getDataStore("currencies"));
		this.playerData = PlayerData.getPlayerData(this.owner);
	}

	private void loadCurrencies(DataStore folder)
	{
		folder.loadAllData().forEach(this::loadCurrency);
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
		this.owner.install(BalanceCommand.createBalanceCommand(currency));
	}

	@Override
	public Currency getCurrency(String name)
	{
		return this.currenciesByName.get(name);
	}

	@Override
	public List<Currency> getCurrencies()
	{
		return Collections.unmodifiableList(this.currencies);
	}

	@Override
	public BankAccount getAccount(UUID uniqueId)
	{
		return this.accounts.computeIfAbsent(uniqueId, this::createBankAccount);
	}

	private BankAccount createBankAccount(UUID uniqueId)
	{
		return new PersistentBankAccount(uniqueId, this.getBankAccountData(uniqueId));
	}

	private PersistentData getBankAccountData(UUID uniqueId)
	{
		return this.playerData.getData(String.valueOf(uniqueId));
	}

}