package com.ulfric.plugin.platform.punishment;

import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;

public class PunishmentsContainer extends Container {

	@Initialize
	private void initialize()
	{
		this.install(PunishmentsService.class);
		this.install(PunishmentHolderArgumentResolver.class);
		this.install(LastPunishmentPlaceholders.LastPunishedPlaceholder.class);
		this.install(KickContainer.class);
	}

}