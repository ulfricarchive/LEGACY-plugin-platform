package com.ulfric.plugin.platform.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.spigot.text.placeholder.Placeholder;

import net.md_5.bungee.chat.ComponentSerializer;

final class CompiledMessage implements Function<CommandSender, String> {

	private static final Map<String, Placeholder> PLACEHOLDERS = new HashMap<>();
	private static final Pattern VARIABLE = Pattern.compile(CompiledMessage.escape("(?i)${[A-Z0-9_]+}"));

	private static String escape(String pattern)
	{
		return pattern
				.replace("$", Pattern.quote("$"))
				.replace("{", Pattern.quote("{"))
				.replace("}", Pattern.quote("}"));
	}

	static void registerPlaceholder(Placeholder placeholder)
	{
		Objects.requireNonNull(placeholder);

		CompiledMessage.PLACEHOLDERS.put(placeholder.getName(), placeholder);
	}

	static void unregisterPlaceholder(Placeholder placeholder)
	{
		Objects.requireNonNull(placeholder);

		CompiledMessage.PLACEHOLDERS.remove(placeholder.getName(), placeholder);
	}

	public static CompiledMessage compileLegacy(String message)
	{
		Objects.requireNonNull(message);

		String legacy = FancyMessage.parse(message).toLegacyText();
		return CompiledMessage.compile(legacy);
	}

	public static CompiledMessage compileRaw(String message)
	{
		Objects.requireNonNull(message);

		String raw = ComponentSerializer.toString(FancyMessage.parse(message));
		return CompiledMessage.compile(raw);
	}

	private static CompiledMessage compile(String message)
	{
		List<Function<CommandSender, String>> compiledMessageParts =
				CompiledMessage.getCompiledMessageParts(message);
		int expectedLength = CompiledMessage.getExpectedLength(message);
		return new CompiledMessage(compiledMessageParts, expectedLength);
	}

	private static List<Function<CommandSender, String>> getCompiledMessageParts(String message)
	{
		List<Function<CommandSender, String>> messages = new ArrayList<>();

		Matcher variables = CompiledMessage.VARIABLE.matcher(message);
		int textStart = 0;
		while (variables.find())
		{
			String variable = CompiledMessage.stripVariableBoilerplate(variables.group());
			Placeholder placeholder = CompiledMessage.PLACEHOLDERS.get(variable);
			Objects.requireNonNull(placeholder, () -> "Placeholder " + variable + " not found");
			messages.add(placeholder);

			String text = message.substring(textStart, variables.start());
			messages.add(ignore -> text);
			textStart = variables.end();
		}
		String text = message.substring(textStart, message.length());
		messages.add(ignore -> text);

		return messages;
	}

	private static String stripVariableBoilerplate(String variable)
	{
		return variable.substring("${".length(), variable.length() - "}".length());
	}

	private static int getExpectedLength(String precompile)
	{
		return (int) (precompile.length() + (0.2 * precompile.length()));
	}

	private final List<Function<CommandSender, String>> compiled;
	private final int expectedLength;

	private CompiledMessage(List<Function<CommandSender, String>> compiled, int expectedLength)
	{
		this.compiled = compiled;
		this.expectedLength = expectedLength;
	}

	@Override
	public String apply(CommandSender target)
	{
		StringBuilder message = new StringBuilder(this.expectedLength);
		for (Function<CommandSender, String> messagePart : this.compiled)
		{
			message.append(messagePart.apply(target));
		}
		return message.toString();
	}

}