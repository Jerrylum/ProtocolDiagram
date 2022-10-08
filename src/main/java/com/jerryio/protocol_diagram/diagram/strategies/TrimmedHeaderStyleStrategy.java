package com.jerryio.protocol_diagram.diagram.strategies;

import java.util.Map;

public class TrimmedHeaderStyleStrategy implements IHeaderStyleStrategy {

	public TrimmedHeaderStyleStrategy(Map<Class, Object> dependencies) {}

	@Override
	public String execute(int width) {
		final StringBuilder builder = new StringBuilder();

		for (int i = 0; i < width; i++) {
				if (i % 10 == 0) {
						builder.append(" " + (i / 10));
				} else {
						builder.append("  ");
				}
		}

		builder.append("\n");

		for (int i = 0; i < width; i++) {
				builder.append(" " + (i % 10));
		}

		builder.append("\n");

		return builder.toString();
	}
	
}
