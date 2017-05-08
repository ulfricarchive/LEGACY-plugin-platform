package com.ulfric.plugin.platform.economy;

import java.util.UUID;

import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.economy.BalanceChangeResult;
import com.ulfric.commons.spigot.economy.BalanceDeductionResult;
import com.ulfric.commons.spigot.economy.BalanceUpdateEvent;
import com.ulfric.commons.spigot.economy.BankAccount;
import com.ulfric.commons.spigot.economy.Currency;
import com.ulfric.commons.spigot.economy.CurrencyAmount;
import com.ulfric.commons.spigot.event.Events;

final class PersistentBankAccount implements BankAccount {

	private final Object lock = new Object();
	private final UUID uniqueId;
	private final DataSection data;

	PersistentBankAccount(UUID uniqueId, PersistentData data)
	{
		this.uniqueId = uniqueId;
		this.data = data.getSection("balance");
	}

	@Override
	public UUID getUniqueId()
	{
		return this.uniqueId;
	}

	@Override
	public long getBalance(Currency currency)
	{
		synchronized(this.lock)
		{
			return this.data.getLong(currency.getName());
		}
	}

	@Override
	public void setBalance(CurrencyAmount amount)
	{
		synchronized(this.lock)
		{
			this.setBalance(amount.getCurrency(), amount.getAmount());
		}
	}

	@Override
	public BalanceDeductionResult deduct(CurrencyAmount amount)
	{
		synchronized(this.lock)
		{
			long balance = this.getBalance(amount.getCurrency());

			long newBalance = balance - amount.getAmount();
			if (newBalance <= 0)
			{
				return new BalanceDeductionResult(Math.abs(newBalance));
			}

			this.setBalance(amount.getCurrency(), newBalance);
			return BalanceDeductionResult.SUCCESS;
		}
	}

	@Override
	public BalanceChangeResult deposit(CurrencyAmount amount)
	{
		synchronized(this.lock)
		{
			long balance = this.getBalance(amount.getCurrency());
			long newBalance = balance + amount.getAmount();
			if (newBalance <= balance)
			{
				return BalanceChangeResult.LargerThanMaxLong.INSTANCE;
			}

			this.setBalance(amount.getCurrency(), newBalance);
			return BalanceChangeResult.SUCCESS;
		}
	}

	private void setBalance(Currency currency, long amount)
	{
		this.fireBalanaceChangeEvent(CurrencyAmount.valueOf(currency, amount));
		this.data.set(currency.getName(), amount);
	}

	private void fireBalanaceChangeEvent(CurrencyAmount newBalance)
	{
		Events.fire(new BalanceUpdateEvent(this, newBalance));
	}

}