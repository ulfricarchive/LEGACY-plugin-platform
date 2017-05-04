package com.ulfric.plugin.platform.combattag;

import java.util.UUID;

import com.ulfric.commons.spigot.combattag.CombatTag;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.metadata.Metadata;
import com.ulfric.commons.spigot.server.TickUtils;
import com.ulfric.commons.spigot.task.Task;
import com.ulfric.commons.spigot.task.Tasks;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class CombatTagService implements CombatTag {

	private static final long DEFAULT_TAG_TICKS = TickUtils.ticksFromSeconds(15);

	@Inject
	private Container owner;

	private String combatTagMetadataKey;
	private long durationInTicks;

	@Initialize
	private void initialize()
	{
		PersistentData config = Data.getDataStore(this.owner).getDefault();
		this.combatTagMetadataKey = config.getString("combat-tag-metadata-key", "CombatTag");
		this.durationInTicks = config.getLong("combat-tag-ticks", CombatTagService.DEFAULT_TAG_TICKS);
	}

	@Override
	public void mark(UUID uniqueId)
	{
		Task untag = Tasks.runLater(() ->
		{
			Object oldTask = Metadata.delete(uniqueId, this.combatTagMetadataKey);

			if (oldTask instanceof Task)
			{
				Task task = (Task) oldTask;
				task.cancel();
			}
		}, this.durationInTicks);
		Metadata.write(uniqueId, this.combatTagMetadataKey, untag);
	}

	@Override
	public void unmark(UUID uniqueId)
	{
		Metadata.delete(uniqueId, this.combatTagMetadataKey);
	}

	@Override
	public boolean isMarked(UUID uniqueId)
	{
		return Metadata.isPresent(uniqueId, this.combatTagMetadataKey);
	}

}