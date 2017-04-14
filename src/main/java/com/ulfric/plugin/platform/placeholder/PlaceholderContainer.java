package com.ulfric.plugin.platform.placeholder;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PlaceholderContainer extends Container {

	@Initialize
	private void setup()
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
	}

}