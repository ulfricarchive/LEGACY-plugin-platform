package com.ulfric.plugin.platform.text;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ulfric.commons.locale.Locale;
import com.ulfric.commons.locale.LocaleSpace;
import com.ulfric.commons.locale.Message;
import com.ulfric.commons.spigot.data.Data;
import com.ulfric.commons.spigot.data.PersistentData;
import com.ulfric.commons.spigot.text.Text;
import com.ulfric.commons.spigot.text.placeholder.Placeholder;
import com.ulfric.dragoon.container.Container;
import com.ulfric.dragoon.initialize.Initialize;
import com.ulfric.dragoon.inject.Inject;

class TextService implements Text {

	private static final String DEFAULT_LOCALE_CODE = "en_US";

	@Inject
	private Container container;

	private Map<String, CompiledMessage> legacyMessages = new HashMap<>();
	private Map<String, CompiledMessage> messages = new HashMap<>();
	private LocaleSpace space;

	@Initialize
	private void loadMessages()
	{
		this.legacyMessages = new HashMap<>();
		this.messages = new HashMap<>();
		this.space = new LocaleSpace();

		this.space.install(Locale.builder().setCode(TextService.DEFAULT_LOCALE_CODE).build());
		Data.getDataStore(this.container).loadAllData().forEach(this::loadMessages);
	}

	private void loadMessages(PersistentData data)
	{
		Locale.Builder locale = Locale.builder();
		locale.setCode(data.getName());
		for (String code : data.getKeys())
		{
			Message message = Message.builder()
				.setCode(code)
				.setText(data.getString(code))
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
	public String getLegacyMessage(CommandSender target, String code)
	{
		String message = this.getLocalizedMessage(target, code);
		return this.legacyMessages.computeIfAbsent(message, CompiledMessage::compileLegacy).apply(target);
	}

	@Override
	public String getRawMessage(CommandSender target, String code)
	{
		String message = this.getLocalizedMessage(target, code);
		return this.messages.computeIfAbsent(message, CompiledMessage::compileRaw).apply(target);
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
			return code;
		}
		return defaultMessage.getText();
	}

	private Locale getDefaultLocale()
	{
		return this.space.getLocale(TextService.DEFAULT_LOCALE_CODE).orElseThrow(NullPointerException::new);
	}

}