package com.ulfric.plugin.platform.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.io.YamlFilter;
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
				throw new IllegalStateException(file + " must be a regular file");
			}
		}
		else
		{
			Try.to(() -> Files.createFile(file));
		}

		return file;
	}

	@Override
	public Stream<PersistentData> loadAllData()
	{
		try {
			// Try.to
			return Files.list(this.directory)
				.filter(YamlFilter.INSTANCE)
				.map(Path::getFileName)
				.map(Path::toString)
				.map(this::stripYaml)
				.map(UUID::fromString)
				.map(this::getData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String stripYaml(String name)
	{
		int lastDot = name.lastIndexOf('.');
		return name.substring(0, lastDot - 1);
	}

}