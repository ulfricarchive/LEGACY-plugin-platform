package com.ulfric.plugin.platform.warp;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.task.Task;
import com.ulfric.commons.spigot.task.Tasks;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Teleport;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class TeleportService implements Teleport {

	@Inject
	private Container container;

	private long tickDelay;

	@Initialize
	private void initialize()
	{
		PersistentData config = Data.getDataStore(this.container).getDefault();
		this.tickDelay = Tasks.secondsToTicks(config.getInt("delay", 5));
	}

	@Override
	public void teleport(Entity entity, Location to)
	{
		this.cancelTeleport(entity, TeleportTell.SILENT);
		Text.getService().sendMessage(entity, "teleport-starting");
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
		if (teleport != null)
		{
			return !teleport.isComplete();
		}
		return false;
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

	enum TeleportTell
	{
		SEND_MESSAGE,
		SILENT;
	}

}