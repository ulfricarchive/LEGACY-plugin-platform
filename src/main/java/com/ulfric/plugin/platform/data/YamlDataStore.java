package com.ulfric.plugin.platform.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.io.YamlFilter;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;

public class YamlDataStore implements DataStore {

	public static YamlDataStore newInstance(Path directory)
	{
		if (Files.exists(directory))
		{
			if (!Files.isDirectory(directory))
			{
				throw new IllegalArgumentException(directory + " not a directory");
			}
		}
		else
		{
			Try.to(() -> Files.createDirectories(directory));
		}

		return new YamlDataStore(directory);
	}

	private final Path directory;
	private final Map<String, YamlPersistentData> data = new HashMap<>();

	private YamlDataStore(Path directory)
	{
		this.directory = directory;
		// TODO watch the directory for external writes, reload files, throw illegal state exceptions
	}

	@Override
	public DataStore getDataStore(String name)
	{
		return YamlDataStore.newInstance(this.directory.resolve(name));
	}

	@Override
	public PersistentData getData(String pointer)
	{
		return this.data.computeIfAbsent(pointer, this::createPersistentData);
	}

	private YamlPersistentData createPersistentData(String pointer)
	{
		return new YamlPersistentData(this.getDataFile(pointer));
	}

	private Path getDataFile(String pointer)
	{
		Path file = this.directory.resolve(pointer + ".yml");

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
				.map(this::getData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String stripYaml(String name)
	{
		int lastDot = name.lastIndexOf('.');
		return name.substring(0, lastDot);
	}

}