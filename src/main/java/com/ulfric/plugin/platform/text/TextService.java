package com.ulfric.plugin.platform.text;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ulfric.commons.exception.Try;
import com.ulfric.commons.io.YamlFilter;
import com.ulfric.commons.locale.Locale;
import com.ulfric.commons.locale.LocaleSpace;
import com.ulfric.commons.locale.Message;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;

class TextService implements Text {

	private static final String DEFAULT_LOCALE_CODE = "en_US";

	private final Map<String, CompiledMessage> messages = new HashMap<>();
	private LocaleSpace space;

	void loadMessages(Path directory)
	{
		this.space = new LocaleSpace();
		this.messages.clear();

		this.space.install(Locale.builder().setCode(TextService.DEFAULT_LOCALE_CODE).build());
		Try.to(() -> Files.list(directory)).filter(YamlFilter.INSTANCE).forEach(this::loadMessagesFromFile);
	}

	private void loadMessagesFromFile(Path file)
	{
		YamlConfiguration configuration =
				Try.toWithResources(() -> Files.newBufferedReader(file), YamlConfiguration::loadConfiguration);

		Locale.Builder locale = Locale.builder();
		String fileName = file.getFileName().toString();
		locale.setCode(fileName.substring(0, fileName.lastIndexOf('.') - 1));
		for (String code : configuration.getKeys(false))
		{
			Message message = Message.builder()
				.setCode(code)
				.setText(configuration.getString(code))
				.build();

			locale.addMessage(message);
		}

		this.space.install(locale.build());
	}

	@Override
	public void registerPlaceholder(Placeholder placeholder)
	{
		CompiledMessage.registerPlaceholder(placeholder);
	}

	@Override
	public void unregisterPlaceholder(Placeholder placeholder)
	{
		CompiledMessage.unregisterPlaceholder(placeholder);
	}

	@Override
	public String getMessage(CommandSender target, String code)
	{
		String message = this.getLocalizedMessage(target, code);

		if (message == null)
		{
			message = code;
		}

		return this.messages.computeIfAbsent(message, CompiledMessage::compile).apply(target);
	}

	private String getLocalizedMessage(CommandSender target, String code)
	{
		if (target instanceof Player)
		{
			String localeCode = ((Player) target).spigot().getLocale();

			if (!TextService.DEFAULT_LOCALE_CODE.equals(localeCode))
			{
				String message = this.space.getLocale(localeCode)
						.map(locale -> locale.getMessage(code))
						.map(Message::getText)
						.orElse(null);

				if (message != null)
				{
					return message;
				}
			}
		}

		return this.getDefaultMessage(code);
	}

	private String getDefaultMessage(String code)
	{
		Locale defaultLocale = this.getDefaultLocale();
		Message defaultMessage = defaultLocale.getMessage(code);
		if (defaultMessage == null)
		{
			return null;
		}
		return defaultMessage.getText();
	}

	private Locale getDefaultLocale()
	{
		return this.space.getLocale(TextService.DEFAULT_LOCALE_CODE).orElseThrow(NullPointerException::new);
	}

}