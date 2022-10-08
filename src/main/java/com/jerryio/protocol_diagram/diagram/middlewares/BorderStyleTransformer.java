package com.jerryio.protocol_diagram.diagram.middlewares;

import java.util.List;
import java.util.Map;

import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.strategies.ASCIIDiagramStyleStrategy;
import com.jerryio.protocol_diagram.diagram.strategies.ASCIIVerboseDiagramStyleStrategy;
import com.jerryio.protocol_diagram.diagram.strategies.DiagramStyleContext;
import com.jerryio.protocol_diagram.diagram.strategies.UTF8CornerDiagramStyleStrategy;
import com.jerryio.protocol_diagram.diagram.strategies.UTF8DiagramStyleStrategy;
import com.jerryio.protocol_diagram.diagram.strategies.UTF8HeaderDiagramStyleStrategy;

public class BorderStyleTransformer implements IMiddleware {

	private final Configuration config;

	public BorderStyleTransformer(Map<Class, Object> dependencies) {
		this.config = (Configuration) dependencies.get(Configuration.class);
	}

	public void execute(Canvas canvas, List<Field> fields) {
		String val = this.config.getValue("diagram-style").toString();
		DiagramStyleContext context = new DiagramStyleContext();

		switch (val) {
			case "utf8": context.setStrategy(new UTF8DiagramStyleStrategy()); break;
			case "utf8-header": context.setStrategy(new UTF8HeaderDiagramStyleStrategy()); break;
			case "utf8-corner": context.setStrategy(new UTF8CornerDiagramStyleStrategy()); break;
			case "ascii": context.setStrategy(new ASCIIDiagramStyleStrategy()); break;
			case "ascii-verbose": context.setStrategy(new ASCIIVerboseDiagramStyleStrategy()); break;
			default: context.setStrategy(new UTF8DiagramStyleStrategy()); break;
		}

		context.execute(canvas);
	}
	
}
