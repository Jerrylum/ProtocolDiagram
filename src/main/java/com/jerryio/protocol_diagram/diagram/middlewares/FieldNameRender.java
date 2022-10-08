package com.jerryio.protocol_diagram.diagram.middlewares;

import java.util.List;
import java.util.Map;

import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.services.SplitService;

public class FieldNameRender implements IMiddleware {

	private final SplitService splitService;
	private final Configuration config;

	public FieldNameRender(Map<Class, Object> dependencies) {
		this.splitService = (SplitService) dependencies.get(SplitService.class);
		this.config = (Configuration) dependencies.get(Configuration.class);
	}

	public void execute(Canvas canvas, List<Field> fields) {
		final int canvasWidth = (int) this.config.getValue("bit");
		final Map<Field, List<Field>> split = this.splitService.split(fields);

		for (int i = 0, x = 0, y = 0; i < split.size(); i++) {
			final List<Field> chunk = split.get(fields.get(i));
			final int size = chunk.size();

			int idx = 0;
			boolean offset = false;

			switch(size) {
				case 1: {
					idx = 0;
					offset = false;
					break;
				}
				case 2: {
					final Field f0 = chunk.get(0);
					final Field f1 = chunk.get(1);
					idx = f0.getLength() == f1.getLength()
						? 0
						: chunk.indexOf(chunk.stream().max((e1, e2) -> Integer.compare(e1.getLength(), e2.getLength())).get());
					offset = idx == 0 && f0.getLength() == canvasWidth;
					break;
				}
				default: {
					final int stridx = chunk.get(0).getLength() == canvasWidth ? 0 : 1;
					final int endidx = chunk.get(chunk.size() - 1).getLength() == canvasWidth ? chunk.size() - 1 : chunk.size() - 2;
					idx = (stridx + endidx) / 2;
					offset = (stridx + endidx) % 2 == 1;
					break;
				}
			}

			for (int j = 0; j < chunk.size(); j++) {
				final Field f = chunk.get(j);

				if (idx == j) {
					canvas.drawText(x * 2, y * 2 + 1 + (offset ? 1 : 0), f.getLength() * 2, f.getName());
				}
				y += (x + f.getLength()) / canvasWidth;
				x = (x + f.getLength()) % canvasWidth;
			}
		}
	}
}
