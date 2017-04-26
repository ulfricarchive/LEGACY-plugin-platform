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
import com.ulfric.commons.spigot.metadata.Metadata;
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
	public String getRawMessage(CommandSender target, String code, String... metadata)
	{
		this.addTemporaryMetadata(target, metadata);
		String message = this.getRawMessage(target, code);
		this.deleteTemporaryMetadata(target, metadata);

		return message;
	}

	@Override
	public String getLegacyMessage(CommandSender target, String code, String... metadata)
	{
		this.addTemporaryMetadata(target, metadata);
		String message = this.getLegacyMessage(target, code);
		this.deleteTemporaryMetadata(target, metadata);

		return message;
	}

	private void addTemporaryMetadata(CommandSender target, String... metadata)
	{
		for (int x = 0, l = metadata.length; x < l; x += 2)
		{
			Metadata.write(target, metadata[x], metadata[x+1]);
		}
	}

	private void deleteTemporaryMetadata(CommandSender target, String... metadata)
	{
		for (int x = 0, l = metadata.length; x < l; x += 2)
		{
			Metadata.delete(target, metadata[x]);
		}
	}

	@Override
	public String getLegacyMessage(CommandSender target, String code)
	{
		String message = this.getLocalizedMessage(target, code);
		return this.getCompiledLegacyMessage(message).apply(target);
	}

	@Override
	public String getRawMessage(CommandSender target, String code)
	{
		String message = this.getLocalizedMessage(target, code);
		return this.getCompiledRawMessage(message).apply(target);
	}

	@Override
	public String getRawMessage(String code, String... metadata)
	{
		CommandSender temp = new TemporaryCommandSender();
		this.addTemporaryMetadata(temp, metadata);
		String message = this.getDefaultRawMessage(code).apply(temp);
		Metadata.delete(temp);
		return message;
	}

	@Override
	public String getLegacyMessage(String code, String... metadata)
	{
		CommandSender temp = new TemporaryCommandSender();
		this.addTemporaryMetadata(temp, metadata);
		String message = this.getDefaultLegacyMessage(code).apply(temp);
		Metadata.delete(temp);
		return message;
	}

	@Override
	public String getRawMessage(String code)
	{
		return this.getDefaultRawMessage(code).apply(TemporaryCommandSender.SHARED);
	}

	@Override
	public String getLegacyMessage(String code)
	{
		return this.getDefaultLegacyMessage(code).apply(TemporaryCommandSender.SHARED);
	}

	private CompiledMessage getDefaultRawMessage(String code)
	{
		String message = this.getDefaultMessage(code);
		return this.getCompiledRawMessage(message);
	}

	private CompiledMessage getDefaultLegacyMessage(String code)
	{
		String message = this.getDefaultMessage(code);
		return this.getCompiledLegacyMessage(message);
	}

	private CompiledMessage getCompiledLegacyMessage(String message)
	{
		return this.legacyMessages.computeIfAbsent(message, CompiledMessage::compileLegacy);
	}

	private CompiledMessage getCompiledRawMessage(String message)
	{
		return this.messages.computeIfAbsent(message, CompiledMessage::compileRaw);
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