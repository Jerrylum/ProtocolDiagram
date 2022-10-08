package com.jerryio.protocol_diagram.diagram.strategies;

import com.jerryio.protocol_diagram.diagram.Canvas;

public interface IDiagramStyleStrategy {

	public char getHorizontalChar();
	public char getVerticalChar();
	public char getTopRightChar();
	public char getBottomRightChar();
	public char getBottomLeftChar();
	public char getTopLeftChar();
	public char getNonTopChar();
	public char getNonRightChar();
	public char getNonBottomChar();
	public char getNonLeftChar();
	public char getAllChar();

	public void execute(Canvas canvas);
	
}
