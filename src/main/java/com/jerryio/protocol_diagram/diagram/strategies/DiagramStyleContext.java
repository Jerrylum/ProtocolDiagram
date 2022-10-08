package com.jerryio.protocol_diagram.diagram.strategies;

import com.jerryio.protocol_diagram.diagram.Canvas;

public class DiagramStyleContext {

	private IDiagramStyleStrategy strategy;

	public void setStrategy(IDiagramStyleStrategy strategy) {
		this.strategy = strategy;
	}

	public void execute(Canvas canvas) {
		this.strategy.execute(canvas);
	}

}
