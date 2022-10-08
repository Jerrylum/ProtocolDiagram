package com.jerryio.protocol_diagram.diagram.strategies;

public class UTF8CornerDiagramStyleStrategy extends UTF8DiagramStyleStrategy {

	@Override
	public char getTopRightChar() { return '┼'; }
	@Override
	public char getBottomRightChar() { return '┼'; }
	@Override
	public char getBottomLeftChar() { return '┼'; }
	@Override
	public char getTopLeftChar() { return '┼'; }
	@Override
	public char getNonTopChar() { return '┼'; }
	@Override
	public char getNonRightChar() { return '┼'; }
	@Override
	public char getNonBottomChar() { return '┼'; }
	@Override
	public char getNonLeftChar() { return '┼'; }
	@Override
	public char getAllChar() { return '┼'; }

}
