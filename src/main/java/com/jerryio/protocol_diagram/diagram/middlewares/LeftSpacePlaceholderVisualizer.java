package com.jerryio.protocol_diagram.diagram.middlewares;

import java.util.List;
import java.util.Map;

import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Coordinate;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.GridType;

public class LeftSpacePlaceholderVisualizer implements IMiddleware {

	private final Configuration config;

	public LeftSpacePlaceholderVisualizer(Map<Class, Object> dependencies) {
		this.config = (Configuration) dependencies.get(Configuration.class);
	}

	@Override
	public void execute(Canvas canvas, List<Field> fields) {
		final int canvasWidth = (int) this.config.getValue("bit");
		final int length = fields.stream().map((e) -> e.getLength()).reduce(0, (a, b) -> a + b);
		final int height = length / canvasWidth;
		final int pivot = length % canvasWidth;
		final int remain = canvasWidth - pivot;
		final String placeholder = "Reserved";

		// draw underlying horizontal line
		for (int i = 0; i < remain * 2 - 1; i++) {
			canvas.setChar(new Coordinate(pivot * 2 + 1 + i, height * 2 + 1), GridType.HORIZONTAL.toChar());
		}
		// draw border connector
		canvas.setChar(new Coordinate(pivot * 2, height * 2 + 1), GridType.NON_LEFT.toChar());
		// draw rightmost border indicator
		canvas.setChar(new Coordinate(canvasWidth * 2, height * 2 + 1), GridType.NON_RIGHT.toChar());
		// draw "Reserved" text
		canvas.drawText(2 * pivot, height * 2 + 1, remain * 2, placeholder);
	}
	
}
