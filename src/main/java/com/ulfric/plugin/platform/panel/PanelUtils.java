package com.ulfric.plugin.platform.panel;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.reflect.HandleUtils;

enum PanelUtils {

	;

	private static final String MINECRAFT_INVENTORY_CLASS_NAME =
			"org.bukkit.craftbukkit.v1_11_R1.inventory.CraftInventoryCustom$MinecraftInventory";
	private static final String NON_NULL_LIST_CLASS_NAME = "net.minecraft.server.NonNullList";

	private static final Class<?> MINECRAFT_INVENTORY_CLASS = PanelUtils.getMinecraftInventoryClass();
	private static final Class<?> NON_NULL_LIST_CLASS = PanelUtils.getNonNullListClass();
	private static final MethodHandle INVENTORY_TITLE_SETTER = PanelUtils.getInventoryTitleSetter();
	private static final MethodHandle INVENTORY_ITEMS_GETTER = PanelUtils.getInventoryItemsGetter();
	private static final MethodHandle INVENTORY_ITEMS_SETTER = PanelUtils.getInventoryItemsSetter();
	private static final Constructor<?> NON_NULL_LIST_CONSTRUCTOR = PanelUtils.getNonNullListConstructor();

	private static final ItemStack EMPTY_ITEM = new ItemStack(Material.AIR);

	private static Class<?> getMinecraftInventoryClass()
	{
		return Try.to(() -> ClassUtils.getClass(PanelUtils.MINECRAFT_INVENTORY_CLASS_NAME, true));
	}

	public static Class<?> getNonNullListClass()
	{
		return Try.to(() -> ClassUtils.getClass(PanelUtils.NON_NULL_LIST_CLASS_NAME, true));
	}

	private static MethodHandle getInventoryTitleSetter()
	{
		Field field = FieldUtils.getDeclaredField(PanelUtils.MINECRAFT_INVENTORY_CLASS, "title", true);

		return HandleUtils.createSetter(field);
	}

	private static MethodHandle getInventoryItemsGetter()
	{
		Field field = FieldUtils.getDeclaredField(PanelUtils.MINECRAFT_INVENTORY_CLASS, "items", true);

		return HandleUtils.createGetter(field);
	}

	private static MethodHandle getInventoryItemsSetter()
	{
		Field field = FieldUtils.getDeclaredField(PanelUtils.MINECRAFT_INVENTORY_CLASS, "items", true);

		return HandleUtils.createSetter(field);
	}


	public static Constructor<?> getNonNullListConstructor()
	{
		return ConstructorUtils.getMatchingAccessibleConstructor(
				PanelUtils.NON_NULL_LIST_CLASS, int.class, Object.class
		);
	}

	static void setTitle(Inventory inventory, String title)
	{
		Try.to(() -> PanelUtils.INVENTORY_TITLE_SETTER.invokeExact(inventory, title));
	}

	static void setSize(Inventory inventory, int size)
	{

		Try.to(() ->
		{
			List<?> items = (List<?>) PanelUtils.INVENTORY_ITEMS_GETTER.invokeExact(inventory);

			List<Object> newItems =
					(List<Object>) PanelUtils.NON_NULL_LIST_CONSTRUCTOR.newInstance(size, PanelUtils.EMPTY_ITEM);

			newItems.addAll(items);

			PanelUtils.INVENTORY_ITEMS_SETTER.invokeExact(inventory, newItems);
		});
	}
}