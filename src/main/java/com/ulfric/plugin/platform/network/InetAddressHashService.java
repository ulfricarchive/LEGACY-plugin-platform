package com.ulfric.plugin.platform.network;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.network.InetAddressHash;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class InetAddressHashService implements InetAddressHash {

	@Inject
	private Container container;

	private DataStore hashes;
	private int hashSize;
	private String hashPrefix;

	private final Object lock = new Object();
	private final Map<String, String> addressToHash = new HashMap<>();
	private final Map<String, String> hashToAddress = new HashMap<>();
	private final Map<String, String> newHashes = new HashMap<>();

	@Initialize
	private void initialize()
	{
		DataStore folder = Data.getDataStore(this.container);
		PersistentData config = folder.getData("config");
		this.hashSize = config.getInt("hash-size", 6);
		this.hashPrefix = config.getString("hash-prefix", "IP-");

		this.hashes = folder.getDataStore("hashes");
		this.hashes.loadAllData().forEach(this::loadHash);
	}

	private void loadHash(PersistentData data)
	{
		String hash = data.getString("hash");
		String address = data.getString("address");

		this.addressToHash.put(address, hash);
		this.hashToAddress.put(hash, address);
	}

	@Override
	public String getInetAddress(String hash)
	{
		synchronized(this.lock)
		{
			String qualifiedHash = hash.toUpperCase();
			if (!qualifiedHash.startsWith(this.hashPrefix))
			{
				qualifiedHash = this.hashPrefix + qualifiedHash;
			}
			return this.hashToAddress.get(qualifiedHash);
		}
	}

	@Override
	public String getHash(String address)
	{
		synchronized(this.lock)
		{
			String hash = this.addressToHash.get(address);
			if (hash == null)
			{
				hash = this.hashPrefix + this.generateRandomHash();
				this.addressToHash.put(address, hash);
				this.hashToAddress.put(hash, address);
				this.newHashes.put(hash, address);
			}
			return hash;
		}
	}

	private String generateRandomHash()
	{
		String hash = RandomStringUtils.randomAlphabetic(this.hashSize)
				.toUpperCase();
		if (this.hashToAddress.containsKey(hash))
		{
			return this.generateRandomHash();
		}
		return hash;
	}

	public final void save()
	{
		DataStore hashes = this.hashes;
		this.newHashes.forEach((hash, address) ->
		{
			PersistentData data = hashes.getData(hash);
			data.set("hash", hash);
			data.set("address", address);
			data.save();
		});
		this.newHashes.clear();
	}

}