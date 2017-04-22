package com.ulfric.plugin.platform.economy;

import java.util.UUID;

import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.economy.BalanceChangeResult;
import com.ulfric.commons.spigot.economy.BalanceDeductionResult;
import com.ulfric.commons.spigot.economy.BankAccount;

final class PersistentBankAccount implements BankAccount {

	private static final String BALANCE_DATA_PATH = "balance";

	private final Object lock = new Object();
	private final UUID uniqueId;
	private final PersistentData data;

	public PersistentBankAccount(UUID uniqueId, PersistentData data)
	{
		this.uniqueId = uniqueId;
		this.data = data;
	}

	@Override
	public UUID getUniqueId()
	{
		return this.uniqueId;
	}

	@Override
	public long getBalance()
	{
		synchronized(this.lock)
		{
			return this.data.getLong(PersistentBankAccount.BALANCE_DATA_PATH);
		}
	}

	@Override
	public void setBalance(long balance)
	{
		synchronized(this.lock)
		{
			long value = Math.abs(balance);
			this.data.set(PersistentBankAccount.BALANCE_DATA_PATH, value);
		}
	}

	@Override
	public BalanceDeductionResult deduct(long amount)
	{
		synchronized(this.lock)
		{
			long value = Math.abs(amount);
			if (value == 0)
			{
				return new BalanceDeductionResult(value);
			}

			long balance = this.getBalance();

			long newBalance = balance - value;
			if (newBalance <= 0)
			{
				return new BalanceDeductionResult(Math.abs(newBalance));
			}

			this.setBalance(newBalance);
			return BalanceDeductionResult.SUCCESS;
		}
	}

	@Override
	public BalanceChangeResult deposit(long amount)
	{
		synchronized(this.lock)
		{
			long value = Math.abs(amount);

			if (value == 0)
			{
				return BalanceChangeResult.FAILURE;
			}

			long balance = this.getBalance();
			long newBalance = balance + value;
			if (newBalance <= balance)
			{
				return BalanceChangeResult.LargerThanMaxLong.INSTANCE;
			}

			this.setBalance(newBalance);
			return BalanceChangeResult.SUCCESS;
		}
	}

}