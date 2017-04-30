package com.ulfric.plugin.platform.panel.chest;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.reflect.HandleUtils;

enum ChestPanelUtils {

	;

	private static final String CRAFT_INVENTORY_CLASS_NAME =
			"org.bukkit.craftbukkit.v1_11_R1.inventory.CraftInventoryCustom";
	private static final String MINECRAFT_INVENTORY_CLASS_NAME =
			ChestPanelUtils.CRAFT_INVENTORY_CLASS_NAME + "$MinecraftInventory";
	private static final String NON_NULL_LIST_CLASS_NAME = "net.minecraft.server.v1_11_R1.NonNullList";

	private static final Class<?> CRAFT_INVENTORY_CLASS = ChestPanelUtils.getCraftInventoryClass();
	private static final Class<?> MINECRAFT_INVENTORY_CLASS = ChestPanelUtils.getMinecraftInventoryClass();
	private static final Class<?> NON_NULL_LIST_CLASS = ChestPanelUtils.getNonNullListClass();
	private static final MethodHandle IINVENTORY_GETTER = ChestPanelUtils.getIInventoryGetter();
	private static final MethodHandle INVENTORY_TITLE_SETTER = ChestPanelUtils.getInventoryTitleSetter();
	private static final MethodHandle INVENTORY_ITEMS_GETTER = ChestPanelUtils.getInventoryItemsGetter();
	private static final MethodHandle INVENTORY_ITEMS_SETTER = ChestPanelUtils.getInventoryItemsSetter();
	private static final MethodHandle NON_NULL_LIST_CREATOR = ChestPanelUtils.getNonNullListCreator();
	private static final MethodHandle NON_NULL_LIST_SIZE = ChestPanelUtils.getNonNullListSize();
	private static final MethodHandle NON_NULL_LIST_GET = ChestPanelUtils.getNonNullListGet();
	private static final MethodHandle NON_NULL_LIST_SET = ChestPanelUtils.getNonNullListSet();

	private static final ItemStack EMPTY_ITEM = new ItemStack(Material.AIR);
	private static MethodHandle nonNullListSet;

	private static Class<?> getCraftInventoryClass()
	{
		return Try.to(() -> ClassUtils.getClass(ChestPanelUtils.CRAFT_INVENTORY_CLASS_NAME, true));
	}

	private static Class<?> getMinecraftInventoryClass()
	{
		return Try.to(() -> ClassUtils.getClass(ChestPanelUtils.MINECRAFT_INVENTORY_CLASS_NAME, true));
	}

	private static Class<?> getNonNullListClass()
	{
		return Try.to(() -> ClassUtils.getClass(ChestPanelUtils.NON_NULL_LIST_CLASS_NAME, true));
	}

	private static MethodHandle getIInventoryGetter()
	{
		return HandleUtils.getMethod(MethodUtils.getMatchingAccessibleMethod(
				ChestPanelUtils.CRAFT_INVENTORY_CLASS,
				"getInventory"
		));
	}

	private static MethodHandle getInventoryTitleSetter()
	{
		Field field = FieldUtils.getDeclaredField(ChestPanelUtils.MINECRAFT_INVENTORY_CLASS, "title", true);

		return HandleUtils.createSetter(field);
	}

	private static MethodHandle getInventoryItemsGetter()
	{
		Field field = FieldUtils.getDeclaredField(ChestPanelUtils.MINECRAFT_INVENTORY_CLASS, "items", true);

		return HandleUtils.createGetter(field);
	}

	private static MethodHandle getInventoryItemsSetter()
	{
		Field field = FieldUtils.getDeclaredField(ChestPanelUtils.MINECRAFT_INVENTORY_CLASS, "items", true);

		return HandleUtils.createSetter(field);
	}

	private static MethodHandle getNonNullListCreator()
	{
		return HandleUtils.getMethod(
				MethodUtils.getMatchingAccessibleMethod(
						ChestPanelUtils.NON_NULL_LIST_CLASS,
						"a", int.class, Object.class
				)
		);
	}

	public static MethodHandle getNonNullListSize()
	{
		return HandleUtils.getMethod(
				MethodUtils.getMatchingAccessibleMethod(
						ChestPanelUtils.NON_NULL_LIST_CLASS,
						"size"
				)
		);
	}

	public static MethodHandle getNonNullListGet()
	{
		return HandleUtils.getMethod(
				MethodUtils.getMatchingAccessibleMethod(
						ChestPanelUtils.NON_NULL_LIST_CLASS,
						"get", int.class
				)
		);
	}

	public static MethodHandle getNonNullListSet()
	{
		return HandleUtils.getMethod(
				MethodUtils.getMatchingAccessibleMethod(
						ChestPanelUtils.NON_NULL_LIST_CLASS,
						"set", int.class, Object.class
				)
		);
	}

	static void setTitle(Inventory inventory, String title)
	{
		Try.to(() -> ChestPanelUtils.INVENTORY_TITLE_SETTER.invokeExact(inventory, title));
	}

	static void setSize(Inventory inventory, int size)
	{
		Try.to(() ->
		{
			Object iInventory = IINVENTORY_GETTER.invoke(inventory);

			Object items = ChestPanelUtils.INVENTORY_ITEMS_GETTER.invoke(iInventory);

			Object newItems = ChestPanelUtils.NON_NULL_LIST_CREATOR.invoke(size, ChestPanelUtils.EMPTY_ITEM);

			int itemsSize = (int) ChestPanelUtils.NON_NULL_LIST_SIZE.invoke(items);

			for (int i = 0; i < itemsSize; i++)
			{
				Object item = ChestPanelUtils.NON_NULL_LIST_GET.invoke(items, i);

				ChestPanelUtils.NON_NULL_LIST_SET.invoke(newItems, i, item);
			}

			ChestPanelUtils.INVENTORY_ITEMS_SETTER.invoke(iInventory, newItems);
		});
	}
}