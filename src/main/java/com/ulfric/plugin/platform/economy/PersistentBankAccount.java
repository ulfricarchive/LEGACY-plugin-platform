package com.ulfric.plugin.platform.economy;

import java.util.UUID;

import com.ulfric.commons.spigot.data.PersistentData;
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
			this.data.set(PersistentBankAccount.BALANCE_DATA_PATH, Math.abs(balance));
		}
	}

}