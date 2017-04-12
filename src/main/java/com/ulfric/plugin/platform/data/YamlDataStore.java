package com.ulfric.plugin.platform.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;

public class YamlDataStore implements DataStore {

	private final Path directory;
	private final Map<UUID, YamlPersistentData> data = new HashMap<>();

	public YamlDataStore(Path directory)
	{
		this.directory = directory;
		// TODO watch the directory for external writes, reload files, throw illegal state exceptions
	}

	@Override
	public PersistentData getData(UUID pointer)
	{
		return this.data.computeIfAbsent(pointer, this::createPersistentData);
	}

	private YamlPersistentData createPersistentData(UUID pointer)
	{
		return new YamlPersistentData(this.getDataFile(pointer));
	}

	private Path getDataFile(UUID pointer)
	{
		Path file = this.directory.resolve(pointer.toString() + ".yml");

		if (Files.exists(file))
		{
			if (!Files.isRegularFile(file))
			{
				// TODO exception
				throw new IllegalStateException();
			}
		}
		else
		{
			try {
				// TODO Try.to
				Files.createFile(file);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return file;
	}

	@Override
	public Stream<PersistentData> loadAllData()
	{
		try {
			// Try.to
			return Files.list(this.directory)
				.filter(this::isYaml)
				.map(Path::getFileName)
				.map(Path::toString)
				.map(this::stripYaml)
				.map(UUID::fromString)
				.map(this::getData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isYaml(Path directory)
	{
		// TODO use YamlFilter
		return directory.toString().endsWith(".yml");
	}

	private String stripYaml(String name)
	{
		int lastDot = name.lastIndexOf('.');
		return name.substring(0, lastDot - 1);
	}

}