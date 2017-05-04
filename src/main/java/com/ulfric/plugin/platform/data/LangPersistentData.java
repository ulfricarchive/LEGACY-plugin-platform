package com.ulfric.plugin.platform.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ulfric.commons.bean.Bean;
import com.ulfric.commons.exception.Try;
import com.ulfric.commons.spigot.data.PersistentData;

final class LangPersistentData extends Bean implements PersistentData {

	private final String name;
	private final Map<String, String> values;

	public LangPersistentData(Path file)
	{
		this.name = file.getFileName().toString().substring(0, ".lang".length());
		this.values = this.readLanguage(file);
	}

	private Map<String, String> readLanguage(Path file)
	{
		List<String> lines = Try.to(() -> Files.readAllLines(file));
		Map<String, String> values = new HashMap<>(lines.size());
		for (String line : lines)
		{
			int equals = line.indexOf('=');
			String key = line.substring(0, equals).trim();
			String value = line.substring(equals).trim();
			values.put(key, value);
		}
		return values;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public String getString(String path, String defaultValue)
	{
		String value = this.getString(path);
		return value == null ? defaultValue : value;
	}

	@Override
	public Object getObject(String path)
	{
		return this.values.get(path);
	}

	@Override
	public String getString(String path)
	{
		return this.values.get(path);
	}

	@Override
	public Set<String> getKeys()
	{
		return Collections.unmodifiableSet(this.values.keySet());
	}

	@Override
	public void save()
	{
		// TODO
	}

	@Override
	public void markForWrite()
	{
		// TODO
	}

	@Override
	public void unmarkForWrite()
	{
		// TODO
	}

	@Override
	public void set(String path, Object value)
	{
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public PersistentData getSection(String path)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getStringList(String path)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(String path)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(String path, int defaultValue)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String path)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String path, long defaultValue)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(String path)
	{
		throw new UnsupportedOperationException();
	}

}
