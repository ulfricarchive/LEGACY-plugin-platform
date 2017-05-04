package com.ulfric.plugin.platform.economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.economy.BalanceDeductionResult;
import com.ulfric.commons.spigot.economy.BankAccount;
import com.ulfric.commons.spigot.economy.CurrencyAmount;
import com.ulfric.commons.spigot.economy.Economy;
import com.ulfric.commons.spigot.text.Text;

@Name("pay")
class PayCommand implements Command {

	@Argument
	private OfflinePlayer target;

	@Argument
	private CurrencyAmount amount;

	@Override
	public void run(Context context)
	{
		CommandSender sender = context.getSender();
		Economy economy = Economy.getService();
		Text text = Text.getService();

		if (!(sender instanceof ConsoleCommandSender))
		{
			if (!(sender instanceof Player))
			{
				throw new IllegalArgumentException("Cannot take balance from: " + sender);
			}

			Player from = (Player) sender;

			if (from.getUniqueId().equals(this.target.getUniqueId()))
			{
				// TODO message cannot pay self
				return;
			}

			BankAccount fromAccount = economy.getAccount(from.getUniqueId());
			BalanceDeductionResult deduction = fromAccount.deduct(this.amount);
			if (!deduction.isSuccess())
			{
				// TODO placeholder for amount missing
				text.sendMessage(sender, "cannot-afford");
				return;
			}
		}

		BankAccount toAccount = economy.getAccount(this.target.getUniqueId());
		toAccount.deposit(this.amount);
		// TODO success message
	}

}