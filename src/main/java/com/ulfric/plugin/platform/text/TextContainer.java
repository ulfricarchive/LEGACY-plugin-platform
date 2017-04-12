package com.ulfric.plugin.platform.text;

import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.plugin.Plugin;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.spigot.service.ServiceUtils;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.dragoon.ObjectFactory;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

public class TextContainer extends Container {

	@Inject
	private Plugin plugin;

	private TextService service;

	@Initialize
	private void setup()
	{
		ServiceUtils.getService(ObjectFactory.class).bind(Text.class).to(TextService.class);
		this.install(TextService.class);
	}

	@Override
	public void onEnable()
	{
		this.service = ServiceUtils.getService(TextService.class);
		this.service.loadMessages(this.getLocaleFolder());
	}

	private Path getLocaleFolder()
	{
		Path localeFolder = this.plugin.getDataFolder().toPath().resolve("locale");
		Try.to(() -> Files.createDirectories(localeFolder));
		return localeFolder;
	}

	@Override
	public void onDisable()	
	{
		ServiceUtils.unregister(TextService.class, this.service);
	}

}