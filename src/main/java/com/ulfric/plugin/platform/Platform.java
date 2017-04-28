package com.ulfric.plugin.platform;

import com.ulfric.commons.spigot.plugin.UlfricPlugin;
import com.ulfric.plugin.platform.control.ControlContainer;
import com.ulfric.plugin.platform.data.DataContainer;
import com.ulfric.plugin.platform.economy.EconomyContainer;
import com.ulfric.plugin.platform.event.EventsContainer;
import com.ulfric.plugin.platform.guard.GuardContainer;
import com.ulfric.plugin.platform.message.MessageContainer;
import com.ulfric.plugin.platform.naming.FriendlyNameContainer;
import com.ulfric.plugin.platform.network.InetAddressHashContainer;
import com.ulfric.plugin.platform.panel.PanelContainer;
import com.ulfric.plugin.platform.permissions.PermissionsContainer;
import com.ulfric.plugin.platform.placeholder.PlaceholderContainer;
import com.ulfric.plugin.platform.punishment.PunishmentsContainer;
import com.ulfric.plugin.platform.server.ShutdownContainer;
import com.ulfric.plugin.platform.text.TextContainer;
import com.ulfric.plugin.platform.warp.TeleportContainer;
import com.ulfric.plugin.platform.warp.WarpContainer;

public final class Platform extends UlfricPlugin {

	@Override
	public void init()
	{
		this.install(DataContainer.class);
		this.install(EventsContainer.class);
		this.install(PermissionsContainer.class);
		this.install(TextContainer.class);
		this.install(PlaceholderContainer.class);
		this.install(ShutdownContainer.class);
		this.install(GuardContainer.class);
		this.install(TeleportContainer.class);
		this.install(WarpContainer.class);
		this.install(EconomyContainer.class);
		this.install(InetAddressHashContainer.class);
		this.install(ControlContainer.class);
		this.install(PunishmentsContainer.class);
		this.install(PanelContainer.class);
		this.install(FriendlyNameContainer.class);
		this.install(MessageContainer.class);
	}

}