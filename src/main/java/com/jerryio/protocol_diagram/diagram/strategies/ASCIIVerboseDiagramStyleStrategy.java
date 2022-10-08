package com.jerryio.protocol_diagram.diagram.strategies;

import com.jerryio.protocol_diagram.diagram.Canvas;
import com.jerryio.protocol_diagram.diagram.Coordinate;
import com.jerryio.protocol_diagram.diagram.GridType;

public class ASCIIVerboseDiagramStyleStrategy extends ASCIIDiagramStyleStrategy {

	public void execute(Canvas canvas) {
		for (int i = 0; i < canvas.getWidth(); i++) {
			for (int j = 0; j < canvas.getHeight(); j++) {
				final Coordinate pos = new Coordinate(i, j);
				final char ch = canvas.getChar(pos);

				if (i % 2 == 0) {
					if (ch == GridType.HORIZONTAL.toChar()) canvas.setChar(pos, this.getAllChar());
				} else {
					if (ch == GridType.HORIZONTAL.toChar()) canvas.setChar(pos, this.getHorizontalChar());
				}
				if (ch == GridType.VERTICAL.toChar()) canvas.setChar(pos, this.getVerticalChar());
				if (ch == GridType.TOP_RIGHT.toChar()) canvas.setChar(pos, this.getTopRightChar());
				if (ch == GridType.BOTTOM_RIGHT.toChar()) canvas.setChar(pos, this.getBottomRightChar());
				if (ch == GridType.BOTTOM_LEFT.toChar()) canvas.setChar(pos, this.getBottomLeftChar());
				if (ch == GridType.TOP_LEFT.toChar()) canvas.setChar(pos, this.getTopLeftChar());
				if (ch == GridType.NON_TOP.toChar()) canvas.setChar(pos, this.getNonTopChar());
				if (ch == GridType.NON_RIGHT.toChar()) canvas.setChar(pos, this.getNonRightChar());
				if (ch == GridType.NON_BOTTOM.toChar()) canvas.setChar(pos, this.getNonBottomChar());
				if (ch == GridType.NON_LEFT.toChar()) canvas.setChar(pos, this.getNonLeftChar());
				if (ch == GridType.ALL.toChar()) canvas.setChar(pos, this.getAllChar());
			}
		}
	}

}
