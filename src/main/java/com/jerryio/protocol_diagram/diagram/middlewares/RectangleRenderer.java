package com.jerryio.protocol_diagram.diagram.middlewares;

import java.util.List;
import java.util.Map;

import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.services.SplitService;

public class RectangleRenderer implements IMiddleware {

	private final SplitService splitService;
	private final Configuration config;

	public RectangleRenderer(Map<Class, Object> dependencies) {
		this.splitService = (SplitService) dependencies.get(SplitService.class);
		this.config = (Configuration) dependencies.get(Configuration.class);
	}

	public void execute(Canvas canvas, List<Field> fields) {
		final int canvasWidth = (int) this.config.getValue("bit");
		final Map<Field, List<Field>> split = this.splitService.split(fields);

		for (int i = 0, x = 0, y = 0; i < split.size(); i++) {
			final List<Field> chunk = split.get(fields.get(i));

			for (int j = 0; j < chunk.size(); j++) {
				final Field f = chunk.get(j);
				canvas.drawRectangle(x * 2, y * 2, f.getLength() * 2);
				y += (x + f.getLength()) / canvasWidth;
				x = (x + f.getLength()) % canvasWidth;
			}
		}
	}
	
}
