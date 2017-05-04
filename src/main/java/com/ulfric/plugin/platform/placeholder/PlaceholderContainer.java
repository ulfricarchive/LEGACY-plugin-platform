package com.ulfric.plugin.platform.placeholder;

import com.ulfric.commons.spigot.command.PermissionFailedPlaceholder;
import com.ulfric.commons.spigot.command.RuleFailedPlaceholder;
import com.ulfric.commons.spigot.command.argument.ArgumentFailedPlaceholder;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PlaceholderContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(RulePlaceholder.class);
		this.install(BukkitNamePlaceholder.class);
		this.install(BukkitShutdownMessagePlaceholder.class);
		this.install(BukkitVersionPlaceholder.class);
		this.install(CurrentHealthNumericPlaceholder.class);
		this.install(LastTargetedByPlaceholder.class);
		this.install(MaxHealthNumericPlaceholder.class);
		this.install(MaxPlayersNumericPlaceholder.class);
		this.install(NoPermissionPlaceholder.class);
		this.install(SenderNamePlaceholder.class);
		this.install(ServerIdPlaceholder.class);
		this.install(ServerNamePlaceholder.class);
		this.install(ServerVersionPlaceholder.class);
		this.install(LastBalanceViewPlaceholder.class);
		this.install(LastBalanceViewUserPlaceholder.class);
		this.install(ArgumentFailedPlaceholder.class);
		this.install(PermissionFailedPlaceholder.class);
		this.install(RuleFailedPlaceholder.class);
	}

}