package com.ulfric.plugin.platform.panel.chest;

import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ulfric.commons.spigot.skulls.Skulls;

class ChestBrowser {

	private static final ItemStack LEFT_ARROW = Skulls.ofTexture(
			"737648ae7a564a5287792b05fac79c6b6bd47f616a559ce8b543e6947235bce");

	private static final ItemStack RIGHT_ARROW = Skulls.ofTexture(
			"1a4f68c8fb279e50ab786f9fa54c88ca4ecfe1eb5fd5f0c38c54c9b1c7203d7a");

	private static final ItemStack MINIMIZE = Skulls.ofTexture(
			"7966f891c1546aecbfcc3baedcfb67079d7f2a6a8b739ed5bac2bb3cf308d38");

	private static final ItemStack CROSS = Skulls.ofTexture(
			"5a6787ba32564e7c2f3a0ce64498ecbb23b89845e5a66b5cec7736f729ed37");

	private static final ItemStack DIVIDER = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);

	static
	{
		ChestBrowser.setName(ChestBrowser.LEFT_ARROW, "browser-back");
		ChestBrowser.setName(ChestBrowser.RIGHT_ARROW, "browser-forward");
		ChestBrowser.setName(ChestBrowser.MINIMIZE, "browser-minimize");
		ChestBrowser.setName(ChestBrowser.CROSS, "browser-close");
		ChestBrowser.setName(ChestBrowser.DIVIDER, " ");
	}

	private static void setName(ItemStack item, String code)
	{
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(code);
		item.setItemMeta(meta);
	}

	void insertInto(ChestPanel.Builder panel)
	{
		panel.buildBrowserButton()
					.setSlots(0)
					.setItem(ChestBrowser.LEFT_ARROW)
					.handle(click ->
					{
						click.getBrowser().previousTab();
						click.getBrowser().display();
					})
				.buildBrowserButton()
					.setSlots(1)
					.setItem(ChestBrowser.RIGHT_ARROW)
					.handle(click ->
					{
						click.getBrowser().nextTab();
						click.getBrowser().display();
					})
				.buildBrowserButton()
					.setSlots(7)
					.setItem(ChestBrowser.MINIMIZE)
					.handle(click ->
							click.getPlayer().closeInventory())
				.buildBrowserButton()
					.setSlots(8)
					.setItem(ChestBrowser.CROSS)
					.handle(click ->
					{
						click.getBrowser().resetSession();
						click.getPlayer().closeInventory();
					})
				.buildBrowserButton()
					.setSlots(ArrayUtils.addAll(IntStream.range(2, 7).toArray(), IntStream.range(9, 18).toArray()))
					.setItem(ChestBrowser.DIVIDER)
					.handle(click -> {})
				.buildBrowserButton();
	}

}
