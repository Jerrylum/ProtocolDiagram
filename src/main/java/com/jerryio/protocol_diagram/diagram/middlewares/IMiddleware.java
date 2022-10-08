package com.jerryio.protocol_diagram.diagram.middlewares;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Field;

public interface IMiddleware {

	public void execute(Canvas canvas, List<Field> fields);
	
}
