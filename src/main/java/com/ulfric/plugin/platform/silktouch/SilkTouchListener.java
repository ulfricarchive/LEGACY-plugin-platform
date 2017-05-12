package com.ulfric.plugin.platform.silktouch;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ulfric.commons.spigot.intercept.RequirePermission;
import com.ulfric.commons.spigot.item.ItemBuilder;
import com.ulfric.commons.spigot.item.ItemUtils;
import com.ulfric.commons.spigot.item.MaterialType;
import com.ulfric.commons.spigot.text.ChatUtils;

class SilkTouchListener implements Listener {
	
	@EventHandler
	@RequirePermission(permission = "silktouch-mine")
	private void on(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack itemStack = player.getEquipment().getItemInMainHand();
		
		if (!this.isSpawner(block) || !this.containsSilktouch(itemStack))
		{
			return;
		}
		
		event.setCancelled(true);
		
		CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
		EntityType type = creatureSpawner.getSpawnedType();
		
		String name = this.getFriendlyName(type);
		
		block.setType(Material.AIR);
		
		ItemUtils.giveItems(player, this.getMobSpawnerItem(type, name));
	}
	
	@EventHandler
	private void on(BlockPlaceEvent event)
	{
		Block block = event.getBlock();
		
		if (!this.isSpawner(block))
		{
			return;
		}
		
		ItemStack itemStack = event.getItemInHand();
		
		if (!this.isSpawner(itemStack))
		{
			return;
		}
		
		String name = this.getEntityNameFromMeta(itemStack.getItemMeta());
		
		if (name == null)
		{
			return;
		}
		
		EntityType type = this.getEntityType(name);
		
		if (type != null)
		{
			CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
			creatureSpawner.setSpawnedType(type);
			creatureSpawner.update();
		}
	}
	
	private boolean containsSilktouch(ItemStack itemStack)
	{
		return itemStack != null && itemStack.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack getMobSpawnerItem(EntityType type, String name)
	{
		return new ItemBuilder()
				.setMaterialType(MaterialType.getType(Material.MOB_SPAWNER, type.getTypeId()))
				.setDisplayName(ChatUtils.format("&e" + name + " Spawner"))
				.build();
	}
	
	private String getEntityNameFromMeta(ItemMeta meta)
	{
		if (!meta.hasDisplayName())
		{
			return null;
		}
		
		String name = ChatColor.stripColor(meta.getDisplayName());
		String[] splitName = name.split(" ");
		
		if (splitName.length > 1)
		{
			return splitName[0];
		}
		
		return null;
	}
	
	private boolean isSpawner(ItemStack itemStack)
	{
		return itemStack != null && itemStack.getType() == Material.MOB_SPAWNER
				&& itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName();
	}
	
	private boolean isSpawner(Block block)
	{
		return block.getType() == Material.MOB_SPAWNER &&
				block.getState() instanceof CreatureSpawner;
	}
	
	@SuppressWarnings("deprecation")
	private String getFriendlyName(EntityType type)
	{
		return type.getName();
	}
	
	@SuppressWarnings("deprecation")
	private EntityType getEntityType(String name)
	{
		return EntityType.fromName(name);
	}
	
}
