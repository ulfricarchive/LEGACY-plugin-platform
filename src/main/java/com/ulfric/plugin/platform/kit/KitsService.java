package com.ulfric.plugin.platform.kit;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.identity.UniqueIdUtils;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.item.ItemUtils;
import com.ulfric.commons.spigot.item.parts.ItemParts;
import com.ulfric.commons.spigot.kit.Kit;
import com.ulfric.commons.spigot.kit.Kits;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.data.PlayerData;

class KitsService implements Kits {

	private final Map<String, Kit> kits = new HashMap<>();
	private final Map<UUID, Map<Kit, Instant>> cooldowns = new HashMap<>();

	@Inject
	private Container owner;

	private PersistentData cooldownData;

	@Initialize
	private void initialize()
	{
		DataStore folder = Data.getDataStore(this.owner);

		this.loadKits(folder.getData("kits"));

		this.cooldownData = PlayerData.getPlayerData(this.owner).getData("cooldowns");
		this.loadCooldowns();
	}

	private void loadKits(PersistentData data)
	{
		data.getSections().forEach(section ->
		{
			String name = section.getName();
			long cooldown = section.getLong("cooldown");

			List<ItemStack> contents = section.getStringList("items")
					.stream()
					.map(ItemParts::deserialize)
					.collect(Collectors.toList());

			Kit kit = BukkitKit.builder()
					.setKitName(name)
					.setCooldown(cooldown)
					.setContents(contents)
					.build();

			this.kits.put(name, kit);
		});
	}

	private void loadCooldowns()
	{
		this.cooldownData.getSections().forEach(section ->
		{
			UUID uniqueId = UniqueIdUtils.parseUniqueId(section.getName());
			Map<Kit, Instant> playerCooldowns = new HashMap<>();

			section.getSections().forEach(kitSection ->
			{
				Kit kit = this.getKit(kitSection.getName());

				Instant lastUsed = Instant.ofEpochMilli(kitSection.getLong("lastUsed"));

				playerCooldowns.put(kit, lastUsed);
			});

			this.cooldowns.put(uniqueId, playerCooldowns);
		});
	}

	@Override
	public Kit getKit(String kitName)
	{
		return this.kits.get(kitName);
	}

	@Override
	public long cooldownFor(Kit kit, Player player)
	{
		Objects.requireNonNull(kit);
		Objects.requireNonNull(player);

		Instant lastUsed = this.cooldowns.get(player.getUniqueId()).computeIfAbsent(kit, ignored -> Instant.MIN);

		long timeBetween = Instant.now().toEpochMilli() - lastUsed.toEpochMilli();

		return Math.max(0, kit.cooldown() - timeBetween);
	}

	@Override
	public void giveKit(Kit kit, Player player)
	{
		Validate.isTrue(this.cooldownFor(kit, player) == 0);

		ItemUtils.giveItems(player, kit.contents());

		Instant used = Instant.now();

		this.cooldowns.computeIfAbsent(player.getUniqueId(), ignored -> new HashMap<>())
				.put(kit, used);

		this.kitUsed(kit, player, used);
	}

	private void kitUsed(Kit kit, Player player, Instant used)
	{
		String uniqueId = player.getUniqueId().toString();

		if (!this.cooldownData.contains(uniqueId))
		{
			this.cooldownData.createSection(uniqueId);
		}

		this.cooldownData.getSection(uniqueId).set("lastUsed", used.toEpochMilli());
	}

}
