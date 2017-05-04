package com.ulfric.plugin.platform.warp;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.server.TickUtils;
import com.ulfric.commons.spigot.task.Task;
import com.ulfric.commons.spigot.task.Tasks;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Teleport;
import com.ulfric.commons.text.FormatUtils;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class TeleportService implements Teleport {

	@Inject
	private Container container;

	private String secondsDelay;
	private long tickDelay;

	@Initialize
	private void initialize()
	{
		PersistentData config = Data.getDataStore(this.container).getDefault();
		this.tickDelay = TickUtils.ticksFromSeconds(config.getInt("delay", 5));
		this.secondsDelay = FormatUtils.formatDouble(TickUtils.secondsFromTicks(this.tickDelay));
	}

	@Override
	public void teleport(Entity entity, Location to)
	{
		this.cancelTeleport(entity, TeleportTell.SILENT);
		Text.getService().sendMessage(entity, "teleport-starting",
				MetadataDefaults.TELEPORT_DELAY, this.secondsDelay);
		Tasks.runLater(() -> entity.teleport(to), this.tickDelay);
	}

	@Override
	public void cancelTeleport(Entity entity)
	{
		this.cancelTeleport(entity, TeleportTell.SEND_MESSAGE);
	}

	private void cancelTeleport(Entity entity, TeleportTell message)
	{
		Task teleport = this.getTeleportTask(entity);
		if (teleport != null)
		{
			if (teleport.isQueued())
			{
				teleport.cancel();

				if (message != TeleportTell.SILENT)
				{
					Text.getService().sendMessage(entity, "teleport-cancelled");
				}
			}
		}
	}

	@Override
	public boolean isTeleporting(Entity entity)
	{
		Task teleport = this.getTeleportTask(entity);

		return teleport != null && !teleport.isComplete();
	}

	private Task getTeleportTask(Entity entity)
	{
		Object teleport = Metadata.delete(entity, "teleport");
		if (teleport instanceof Task)
		{
			return (Task) teleport;
		}
		return null;
	}

	private enum TeleportTell
	{
		SEND_MESSAGE,
		SILENT
	}

}