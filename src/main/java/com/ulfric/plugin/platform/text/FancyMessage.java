package com.ulfric.plugin.platform.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ulfric.commons.spigot.text.ChatUtils;
import com.ulfric.commons.spigot.xml.XmlUtils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

enum FancyMessage {

	;

	public static TextComponent parse(String message)
	{
		String text = ChatUtils.format(message);

		Document document = XmlUtils.parseIncompleteDocument(text);
		Node root = document.getDocumentElement();

		return FancyMessage.createComponent(root);
	}

	private static TextComponent createComponent(Node node)
	{
		TextComponent component = new TextComponent("");

		String value = node.getNodeValue();
		if (value == null)
		{
			NodeList nodes = node.getChildNodes();
			for (int x = 0, l = nodes.getLength(); x < l; x++)
			{
				Node child = nodes.item(x);
				component.addExtra(FancyMessage.createComponent(child));
			}
		}
		else // TODO run event stuff outside of else?
		{
			List<Node> parents = getAllParentEvents(node);
			for (Node event : parents)
			{
				NamedNodeMap attributes = event.getAttributes();

				Node type = attributes.getNamedItem("type");
				Objects.requireNonNull(type, "type");
				EventType eventType = EventType.parse(type.getNodeValue());

				Node text = attributes.getNamedItem("text");
				Objects.requireNonNull(type, "text");
				eventType.accept(text.getNodeValue(), component);
			}
			component.setText(value);
		}

		return component;
	}

	private static List<Node> getAllParentEvents(Node node)
	{
		List<Node> parents = new ArrayList<>();

		Node parent = node;
		while (parent != null)
		{
			if (isEvent(parent))
			{
				parents.add(parent);
			}

			parent = parent.getParentNode();
		}

		Collections.reverse(parents);
		return parents;
	}

	private static boolean isEvent(Node node)
	{
		return node != null && node.hasAttributes() && "event".equals(node.getNodeName());
	}

	private enum EventType implements BiConsumer<String, TextComponent>
	{
		HOVER
		{
			@Override
			public void accept(String text, TextComponent component)
			{
				HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(text).create());
				component.setHoverEvent(event);
			}
		},

		URL
		{
			@Override
			public void accept(String text, TextComponent component)
			{
				ClickEvent event = new ClickEvent(ClickEvent.Action.OPEN_URL, text);
				component.setClickEvent(event);
			}
		},

		RUN
		{
			@Override
			public void accept(String text, TextComponent component)
			{
				ClickEvent event = new ClickEvent(ClickEvent.Action.RUN_COMMAND, text);
				component.setClickEvent(event);
			}
		},

		SUGGEST
		{
			@Override
			public void accept(String text, TextComponent component)
			{
				ClickEvent event = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text);
				component.setClickEvent(event);
			}
		};

		public static EventType parse(String value)
		{
			return EventType.valueOf(value.toUpperCase());
		}
	}

}