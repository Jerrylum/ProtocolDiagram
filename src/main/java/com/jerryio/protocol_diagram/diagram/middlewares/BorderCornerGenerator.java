package com.jerryio.protocol_diagram.diagram.middlewares;

import java.util.List;
import java.util.Map;

import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Field;

public class BorderCornerGenerator implements IMiddleware {

	public BorderCornerGenerator(Map<Class, Object> dependencies) { }
	
	public void execute(Canvas canvas, List<Field> fields) {
		canvas.generateCorners();
	}

}
