package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.io.LangFilter;
import com.ulfric.commons.io.YamlFilter;
import com.ulfric.commons.spigot.data.DataStore;
import com.ulfric.commons.spigot.data.PersistentData;

public class FileDataStore implements DataStore, Predicate<Path> {

	public static FileDataStore newInstance(Path directory)
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

		return new FileDataStore(directory);
	}

	private final Path directory;
	private final Map<String, PersistentData> data = new HashMap<>();
	private final Map<String, DataStore> substores = new HashMap<>();

	private FileDataStore(Path directory)
	{
		this.directory = directory;
		// TODO watch the directory for external writes, reload files, throw illegal state exceptions
	}

	@Override
	public DataStore getDataStore(String name)
	{
		return this.substores.computeIfAbsent(name, key -> FileDataStore.newInstance(this.directory.resolve(key)));
	}

	@Override
	public PersistentData getData(String pointer)
	{
		return this.data.computeIfAbsent(pointer, this::createPersistentData);
	}

	@Override
	public void deleteData(String pointer)
	{
		this.data.remove(pointer);
		Try.to(() -> Files.deleteIfExists(this.getDataFile(pointer)));
	}

	private PersistentData createPersistentData(String pointer)
	{
		Path file = this.getDataFile(pointer);
		if (LangFilter.INSTANCE.test(file))
		{
			return new LangPersistentData(file);
		}
		return new YamlPersistentData(file);
	}

	private Path getDataFile(String pointer)
	{
		Path existingFile = this.getExistingLangFile(pointer);
		if (existingFile != null)
		{
			return existingFile;
		}

		return this.getYamlFile(pointer);
	}

	private Path getExistingLangFile(String pointer)
	{
		Path file = this.directory.resolve(pointer + ".lang");
		return this.validateFile(file) ? file : null;
	}

	private Path getYamlFile(String pointer)
	{
		Path file = this.directory.resolve(pointer + ".yml");

		if (!this.validateFile(file))
		{
			Try.to(() -> Files.createFile(file));
		}

		return file;
	}

	private boolean validateFile(Path file)
	{
		if (Files.exists(file))
		{
			if (Files.isRegularFile(file))
			{
				return true;
			}

			throw new IllegalStateException(file + " must be a regular file");
		}

		return false;
	}

	@Override
	public Stream<PersistentData> loadAllData()
	{
		return Try.to(() -> Files.list(this.directory))
				.filter(this)
				.map(Path::getFileName)
				.map(Path::toString)
				.map(this::stripYaml)
				.map(this::getData);
	}

	private String stripYaml(String name)
	{
		int lastDot = name.lastIndexOf('.');
		return name.substring(0, lastDot);
	}

	@Override
	public boolean test(Path path)
	{
		return YamlFilter.INSTANCE.test(path) || LangFilter.INSTANCE.test(path);
	}

}