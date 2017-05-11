package com.ulfric.plugin.platform.warp.home;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataSection;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.item.ItemUtils;
import com.ulfric.commons.spigot.metadata.MetadataDefaults;
import com.ulfric.commons.spigot.panel.Panels;
import com.ulfric.commons.spigot.panel.browser.Browser;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.warp.Teleport;
import com.ulfric.commons.spigot.warp.Warp;
import com.ulfric.commons.spigot.warp.WarpAccount;
import com.ulfric.commons.spigot.warp.Warps;
import com.ulfric.commons.spigot.warp.home.Homes;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;
import com.ulfric.plugin.platform.panel.chest.ChestPanel;
import com.ulfric.plugin.platform.panel.chest.ChestPanelBuilder;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class HomeService implements Homes {
	
	private static final String HOME_NAME_PLACEHOLDER = Text.getService().getPlainMessage("home-name-placeholder");
	
	@Inject
	private Container owner;
	
	private final Map<String, Integer> permissionHomes = new CaseInsensitiveMap<>();
	private ItemStack displayItem;
	
	@Initialize
	private void initialize()
	{
		PersistentData data = Data.getDataStore(this.owner).getDefault();
		this.loadSettings(data);
	}
	
	private void loadSettings(PersistentData data)
	{
		data.createSection("homes").getSections().forEach(this::loadHome);
		this.displayItem = ItemUtils.deserializeItem(data.getString("home-display-item"));
	}
	
	private void loadHome(DataSection section)
	{
		String permission = section.getString("permission");
		int amount = section.getInt("total-homes");
		this.permissionHomes.put(permission, amount);
	}
	
	@Override
	public int getTotalHomes(Player player)
	{
		return this.locateTotalHomes(player);
	}
	
	private int locateTotalHomes(Player player)
	{
		return this.permissionHomes.keySet().stream()
				.filter(player::hasPermission)
				.mapToInt(this.permissionHomes::get)
				.sorted()
				.findFirst().orElse(-1);
	}
	
	@Override
	public void openHomesPanel(Player player)
	{
		Browser browser = Panels.getService().getBrowser(player);
		Text text = Text.getService();
		
		ChestPanelBuilder builder = ChestPanel.builder()
				.setTitle(text.getPlainMessage("homes-panel"));
		
		WarpAccount account = Warps.getService().getAccount(player.getUniqueId());
		
		int slot = 0;
		
		for (Warp warp : account.getWarps())
		{
			String name = warp.getName();
			
			builder.addButton()
					.setItem(this.getDisplayItem(name))
					.setSlots(slot++)
					.handle(chestClickData ->
					{
						Player user = chestClickData.getPlayer();
						text.sendMessage(user, "home-teleport", MetadataDefaults.LAST_HOME_TELEPORT, name);
						Teleport.getService().teleport(user, warp.getLocation());
						user.closeInventory();
					}).build();
		}
		
		builder.build().open(browser);
	}
	
	private ItemStack getDisplayItem(String name)
	{
		ItemStack clone = this.displayItem.clone();
		
		if (clone.hasItemMeta())
		{
			ItemMeta meta = clone.getItemMeta();
			
			if (meta.hasDisplayName())
			{
				meta.setDisplayName(meta.getDisplayName().replace(HomeService.HOME_NAME_PLACEHOLDER, name));
			}
			
			if (meta.hasLore())
			{
				meta.setLore(
						new ArrayList<>(meta.getLore()).stream()
								.map(current -> this.homeNameProvider().apply(current, name))
								.collect(Collectors.toList())
				);
			}
			
			clone.setItemMeta(meta);
		}
		
		return clone;
	}
	
	private BiFunction<String, String, String> homeNameProvider()
	{
		return (current, home) -> current.replace(HomeService.HOME_NAME_PLACEHOLDER, home);
	}
	
}
