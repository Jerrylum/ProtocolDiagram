package com.jerryio.protocol_diagram.diagram.middlewares;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Field;

public class CollisionEliminator implements IMiddleware {

	private final Configuration config;

	public CollisionEliminator(Map<Class, Object> dependencies) {
		this.config = (Configuration) dependencies.get(Configuration.class);
	}

	public void execute(Canvas canvas, List<Field> fields) {
		final int canvasWidth = (int) config.getValue("bit");
		final List<Integer> collisions = new ArrayList<>();

		for (Field field: fields) {
			collisions.add(Math.max(field.getLength() - canvasWidth, 0));
		}

		for (int i = 0, x = 0, y = 0; i < fields.size() && collisions.size() != 0; i++) {
			final Field f = fields.get(i);
			int collision = collisions.get(i);

			// eliminate border until no collision in the current context
			for (int nx = x, ny = y; collision > 0; nx = 0, ny++) {
				final int range = Math.min(collision, canvasWidth - nx);
				canvas.eliminateBorder(nx * 2, ny * 2, range * 2);
				collision -= range;
			}

			y += (x + f.getLength()) / canvasWidth;
			x = (x + f.getLength()) % canvasWidth;
		}
	}
	
}
