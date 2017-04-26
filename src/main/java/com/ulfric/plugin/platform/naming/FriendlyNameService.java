package com.ulfric.plugin.platform.naming;

import java.util.Map;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.bukkit.Material;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.item.MaterialType;
import com.ulfric.commons.spigot.naming.FriendlyName;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class FriendlyNameService implements FriendlyName {

	private final Map<String, MaterialType> nameToType = new CaseInsensitiveMap<>();

	@Inject
	private Container container;

	private DataStore folder;

	@Initialize
	private void initialize()
	{
		this.folder = Data.getDataStore(this.container);
		this.registerItems();
	}

	private void registerItems()
	{
		this.registerDefaultItems();

		PersistentData names = this.folder.getData("items");
		for (PersistentData name : names.getSections())
		{
			this.nameToType.put(name.getName(), this.getMaterialType(name));
		}
	}

	private void registerDefaultItems()
	{
		for (Material material : Material.values())
		{
			MaterialType materialType = MaterialType.getType(material);
			String name = material.name();
			this.nameToType.put(name, materialType);
			this.nameToType.put(name.replace("_", ""), materialType);
		}
	}

	private MaterialType getMaterialType(PersistentData name)
	{
		String materialString = name.getString("material");
		Material material = Material.getMaterial(materialString);
		byte data = (byte) name.getInt("data");
		return MaterialType.getType(material, data);
	}

	@Override
	public MaterialType materialTypeByName(String name)
	{	
		return this.nameToType.get(name);
	}

}