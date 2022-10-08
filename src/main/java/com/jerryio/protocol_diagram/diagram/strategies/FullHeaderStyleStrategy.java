package com.jerryio.protocol_diagram.diagram.strategies;

import java.util.Map;

import com.jerryio.protocol_diagram.config.Configuration;

public class FullHeaderStyleStrategy implements IHeaderStyleStrategy {

	private final Configuration config;

	public FullHeaderStyleStrategy(Map<Class, Object> dependencies) {
		this.config = (Configuration) dependencies.get(Configuration.class);
	}

	@Override
	public String execute(int width) {
		final int canvasWidth = (int) this.config.getValue("bit");
		final StringBuilder builder = new StringBuilder();

		for (int i = 0; i < canvasWidth; i++) {
				if (i % 10 == 0) {
						builder.append(" " + (i / 10));
				} else {
						builder.append("  ");
				}
		}

		builder.append("\n");

		for (int i = 0; i < canvasWidth; i++) {
				builder.append(" " + (i % 10));
		}

		builder.append("\n");

		return builder.toString();
	}
	
}
